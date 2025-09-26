package com.trymad.litechess_monolith.livegame.internal.service.impl;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.livegame.internal.model.GameTimer;
import com.trymad.litechess_monolith.livegame.internal.model.TimerHistory;
import com.trymad.litechess_monolith.livegame.internal.service.GameTimeService;

@Component
public class ScheduleQueueGameTimeServiceImpl implements GameTimeService{

	private final static int THREAD_POOL_SIZE = 5;

	private final ScheduledExecutorService executorService;

	private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

	public ScheduleQueueGameTimeServiceImpl() {
		this.executorService = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
	}

	@Override
	public GameTimer createTimer(TimeControlDTO timeControl, TimerHistory timerHistory) {
		final Duration lastTimerWhite = timerHistory.getLastTimerValue(PlayerColor.WHITE);
		final Duration lastTimerBlack = timerHistory.getLastTimerValue(PlayerColor.WHITE);

		final Duration whiteTime = lastTimerWhite != null ? lastTimerWhite : Duration.ofMillis(timeControl.initTime());
		final Duration blackTime = lastTimerBlack != null ? lastTimerBlack : Duration.ofMillis(timeControl.initTime());
		final PlayerColor currentTurn = timerHistory.getLastTimedPlayer() != null ? 
			PlayerColor.WHITE : timerHistory.getLastTimedPlayer().flip();

		return new GameTimer(whiteTime, blackTime, currentTurn, timeControl);
	}

	@Override
	public void startTimer(long gameId, GameTimer gameTimer, Runnable onTimeout) {
		stopTimer(gameId);

		final Runnable runnable = () -> {
			scheduledTasks.remove(gameId);
			onTimeout.run();
		};

		final ScheduledFuture<?> scheduledFuture = 
			executorService.schedule(runnable, gameTimer.getRemainingTimeForCurrentPlayer().toMillis(), TimeUnit.MILLISECONDS);
		
		scheduledTasks.put(gameId, scheduledFuture);
	}

	@Override
	public void stopTimer(long gameId) {
		final ScheduledFuture<?> existedFuture = scheduledTasks.get(gameId);
		if(existedFuture != null && !existedFuture.isDone()) {
			existedFuture.cancel(true);
		}

		scheduledTasks.remove(gameId);
	}
	
}
