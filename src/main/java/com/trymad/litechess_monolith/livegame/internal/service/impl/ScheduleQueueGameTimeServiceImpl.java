package com.trymad.litechess_monolith.livegame.internal.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.livegame.internal.model.GameTimer;
import com.trymad.litechess_monolith.livegame.internal.model.TimerHistory;
import com.trymad.litechess_monolith.livegame.internal.service.GameTimeService;

@Component
public class ScheduleQueueGameTimeServiceImpl implements GameTimeService {

	private final static int THREAD_POOL_SIZE = 5;

	private final ScheduledExecutorService executorService;

	private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

	private final Logger logger = Logger.getLogger("gameTimerService");

	public ScheduleQueueGameTimeServiceImpl() {
		this.executorService = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
	}

	@Override
	public GameTimer createTimer(TimeControlDTO timeControl, TimerHistory timerHistory) {
		return new GameTimer(timerHistory, timeControl);
	}

	@Override
	public void startTimer(long gameId, GameTimer gameTimer, Runnable onTimeout) {
		stopTimer(gameId);

		final Runnable runnable = () -> {
			scheduledTasks.remove(gameId);
			onTimeout.run();
		};

		Instant now = Instant.now();
		long delayMillis = Duration.between(now, gameTimer.getDeadline(gameTimer.getCurrentTurn())).toMillis();


		final ScheduledFuture<?> scheduledFuture = 
			executorService.schedule(runnable, delayMillis, TimeUnit.MILLISECONDS);
		
		scheduledTasks.put(gameId, scheduledFuture);
	}

	@Override
	public void stopTimer(long gameId) {
		final ScheduledFuture<?> existedFuture = scheduledTasks.get(gameId);
		if(existedFuture != null && !existedFuture.isDone()) {
			existedFuture.cancel(true);
		}

		logger.info("timer stop: " + gameId);
		scheduledTasks.remove(gameId);
	}
	
}
