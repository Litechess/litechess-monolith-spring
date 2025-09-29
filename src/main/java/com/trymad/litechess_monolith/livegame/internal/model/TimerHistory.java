package com.trymad.litechess_monolith.livegame.internal.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TimerHistory {
	
	private final List<Long> history = new ArrayList<>();

	public TimerHistory(List<Long> history) {
		this.history.addAll(history);
	}

	public void addTime(Duration duration) {
		history.add(duration.toMillis());
	}

	public void addTime(Long duration) {
		history.add(duration);
	}

	public Duration getLastTimerValue(PlayerColor side) {
		int minSize = side == PlayerColor.WHITE ? 1 : 2;
		if(history.size() < minSize) {
			return null;
		}
		
		if(history.size() % 2 == 0) {
			return side == PlayerColor.WHITE ? 
				Duration.ofMillis(history.get(history.size() - 2)) : 
				Duration.ofMillis(history.get(history.size() - 1));
		} else {
			return side == PlayerColor.WHITE ? 
				Duration.ofMillis(history.get(history.size() - 1)) : 
				Duration.ofMillis(history.get(history.size() - 2));
		}
	}

	public PlayerColor getLastTimedPlayer() {
		if(history.isEmpty()) {
			return PlayerColor.WHITE;
		}
		return history.size() % 2 == 0 ? PlayerColor.BLACK : PlayerColor.WHITE;
	}

	@Override
	public String toString() {
		return history.toString();
	}

	public List<Long> getAsList() {
		return new ArrayList<>(history);
	}


}
