package com.trymad.litechess_monolith.chessparty.api.model;

import java.time.Duration;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TimeControl {
	
	private final Duration initTime;
	
	private final Duration increment;
	
	private final TimeControlType type;

	public TimeControl(DefaultTimeControl control) {
		this.increment = control.getIncrement();
		this.initTime = control.getInitTime();
		this.type = control.getType();
	}

}
