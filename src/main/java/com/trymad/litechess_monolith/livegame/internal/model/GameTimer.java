package com.trymad.litechess_monolith.livegame.internal.model;

import java.time.Duration;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;

public class GameTimer {

    private long whiteTime; 
    private long blackTime; 
    private long increment;  
    private PlayerColor currentTurn;
    private long lastMoveTimestamp;
    private final TimeControlDTO timeControl;

    public GameTimer(Duration whiteTime, Duration blackTime, PlayerColor currentTurn, TimeControlDTO timeControl) {
        this.whiteTime = whiteTime.toMillis();
        this.blackTime = blackTime.toMillis();
        this.increment = timeControl.increment();
        this.lastMoveTimestamp = System.currentTimeMillis();
        this.timeControl = timeControl;
        this.currentTurn = currentTurn;
    }


    public void applyMove() {
        long now = System.currentTimeMillis();
        long spent = now - lastMoveTimestamp;

        if (currentTurn == PlayerColor.WHITE) {
            whiteTime -= spent;
            whiteTime += increment;
            currentTurn = PlayerColor.BLACK;
        } else {
            blackTime -= spent;
            blackTime += increment;
            currentTurn = PlayerColor.WHITE;
        }

        lastMoveTimestamp = now;
    }

    public Duration getRemainingTimeForCurrentPlayer() {
        long now = System.currentTimeMillis();
        long spent = now - lastMoveTimestamp;

        if (currentTurn == PlayerColor.WHITE) {
            return Duration.ofMillis(whiteTime - spent);
        } else {
            return Duration.ofMillis(blackTime - spent);
        }
    }

    public PlayerColor getCurrentTurn() {
        return currentTurn;
    }

    public Duration getWhiteTime() {
        if (currentTurn == PlayerColor.WHITE) {
            return getRemainingTimeForCurrentPlayer();
        }
        return Duration.ofMillis(whiteTime);
    }

    public Duration getBlackTime() {
        if (currentTurn == PlayerColor.BLACK) {
            return getRemainingTimeForCurrentPlayer();
        }
        return Duration.ofMillis(blackTime);
    }

    public TimeControlDTO getTimeControl() {
        return timeControl;
    }
}
