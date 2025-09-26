package com.trymad.litechess_monolith.chessparty.internal.model;

import java.time.Duration;

import com.trymad.litechess_monolith.chessparty.api.model.TimeControlType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class TimeControl {
	
	private final Duration initTime;
	
	private final Duration increment;
	
	private final TimeControlType type;

	public TimeControl(Duration initTime, Duration increment) {
		if(initTime.isPositive() == false) {
			this.initTime = Duration.ZERO;
			this.increment = Duration.ZERO;
			this.type = TimeControlType.NO_CONTROL;
		} else {
			this.initTime = initTime;
			this.increment = increment;
			this.type = increment.isPositive() ? TimeControlType.WITHOUT_INCREMENT : TimeControlType.WITH_INCREMENT;
		}
	}

	public static TimeControl noControl() {
		return new TimeControl(Duration.ZERO, Duration.ZERO);
	}
}
