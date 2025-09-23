package com.trymad.litechess_monolith.chessparty.api.model;

import java.time.Duration;

public enum DefaultTimeControl {
	
	RAPID_15_5(Duration.ofMinutes(15), Duration.ofSeconds(5), TimeControlType.WITH_INCREMENT),
	RAPID_10_5(Duration.ofMinutes(10), Duration.ofSeconds(5), TimeControlType.WITH_INCREMENT),
	RAPID_10_0(Duration.ofMinutes(10), Duration.ofSeconds(0), TimeControlType.WITHOUT_INCREMENT);
	
	private final Duration initTime;
	
	private final Duration increment;

	private final TimeControlType type;
	
	DefaultTimeControl(Duration initTime, Duration increment, TimeControlType type) {
		this.increment = increment;
		this.initTime = initTime;
		this.type = type;
	}

	public Duration getIncrement() {
		return increment;
	}

	public Duration getInitTime() {
		return initTime;
	}

	public TimeControlType getType() {
		return type;
	}
}
