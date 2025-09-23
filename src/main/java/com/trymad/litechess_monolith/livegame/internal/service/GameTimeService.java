package com.trymad.litechess_monolith.livegame.internal.service;

import com.trymad.litechess_monolith.chessparty.api.model.TimeControl;
import com.trymad.litechess_monolith.livegame.internal.model.GameTimer;
import com.trymad.litechess_monolith.livegame.internal.model.TimerHistory;

public interface GameTimeService {
	

	GameTimer createTimer(TimeControl timeControl, TimerHistory timerHistory);

	void startTimer(long gameId, GameTimer gameTimer, Runnable onTimeout);

	void stopTimer(long gameId);


}
