package com.trymad.litechess_monolith.livegame.internal.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;

public class GameTimer {

    private final Map<PlayerColor, Instant> deadLines = new ConcurrentHashMap<>();
    private final Map<PlayerColor, Long> remainingTimes = new ConcurrentHashMap<>();
    private final TimerHistory timerHistory;
    
    private final TimeControlDTO timeControl;

    public GameTimer(TimerHistory history, TimeControlDTO timeControl) {
        this.timerHistory = history;
        this.timeControl = timeControl;
        final Duration lastWhiteTime = timerHistory.getLastTimerValue(PlayerColor.WHITE);
        final Duration lastBlackTime = timerHistory.getLastTimerValue(PlayerColor.BLACK);
        remainingTimes.put(PlayerColor.WHITE, lastWhiteTime == null ? timeControl.initTime() : lastWhiteTime.toMillis());
        remainingTimes.put(PlayerColor.BLACK, lastBlackTime == null ? timeControl.initTime() : lastBlackTime.toMillis());
    }

    public void start() {
        final PlayerColor currentTurn = getCurrentTurn();
        final long remainingTime = remainingTimes.get(currentTurn);
        deadLines.put(currentTurn, Instant.now().plusMillis(remainingTime));
    }

    public void stop() {
        deadLines.remove(getCurrentTurn());
    }

    public boolean isRunning() {
        return deadLines.containsKey(getCurrentTurn());
    }

    public PlayerColor getCurrentTurn() {
        return timerHistory.getLastTimedPlayer() == null ? 
            PlayerColor.WHITE : timerHistory.getLastTimedPlayer().flip();
    }

    public void applyMove() {
        final PlayerColor currentTurn = getCurrentTurn();
        if(!isRunning()) {
            throw new IllegalStateException("Timer is not running");
        }

        final Instant deadLine = deadLines.get(currentTurn);
        final long timeUsed = Duration.between(deadLine, Instant.now()).abs().toMillis();
        long newRemainingTime = timeUsed + timeControl.increment();
        remainingTimes.put(currentTurn, Math.max(newRemainingTime, 0));
        deadLines.remove(currentTurn);
        timerHistory.addTime(Duration.ofMillis(remainingTimes.get(currentTurn)));

        final long remainingTimeOpponent = remainingTimes.get(currentTurn.flip());
        deadLines.put(currentTurn.flip(), Instant.now().plusMillis(remainingTimeOpponent));
    }

    public Instant getDeadline(PlayerColor color) {
        return deadLines.get(color);
    }

    public Instant getRemainingTime(PlayerColor color) {
        final Instant deadLine = deadLines.get(color);
        if(deadLine == null) {
            return Instant.now().plusMillis(remainingTimes.get(color));
        }

        final long remainingMillis = Duration.between(Instant.now(), deadLine).abs().toMillis();
        return Instant.now().plusMillis(remainingMillis);
    }

    public TimerHistory getTimerHistory() {
        return timerHistory;
    }

    public TimeControlDTO getTimeControl() {
        return timeControl;
    }
}
