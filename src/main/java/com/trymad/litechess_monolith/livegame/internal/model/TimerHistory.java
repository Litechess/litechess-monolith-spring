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
		if(side == PlayerColor.WHITE && history.size() >= 1) {
			long millis = history.size() % 2 == 0 ? history.get(history.size() - 2) : history.get(history.size() - 1);
			return Duration.ofMillis(millis);
		}
		else if(side == PlayerColor.BLACK && history.size() >= 2) {
			long millis = history.size() % 2 == 0 ? history.get(history.size() - 1) : history.get(history.size() - 2);
			return Duration.ofMillis(millis);
		}
		
		return null;
	}

	public PlayerColor getLastTimedPlayer() {
		if(history.isEmpty()) {
			return null;
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
