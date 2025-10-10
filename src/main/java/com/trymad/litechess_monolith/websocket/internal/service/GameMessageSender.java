package com.trymad.litechess_monolith.websocket.internal.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessparty.api.event.GameCreatedEvent;
import com.trymad.litechess_monolith.chessparty.api.event.MoveAcceptedEvent;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.livegame.api.event.LiveGameStartEvent;
import com.trymad.litechess_monolith.websocket.api.dto.GameCreatedDTO;
import com.trymad.litechess_monolith.websocket.api.dto.MoveResponse;
import com.trymad.litechess_monolith.websocket.internal.model.GameResultMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

// TODO delete from queue when unsub /matchmaking/queue
public class GameMessageSender {
	
	private final SimpMessagingTemplate messagingTemplate;

	public void gameCreate(GameCreatedEvent event) {
		final ChessPartyDTO chessParty = event.chessParty();
		final List<UUID> users = new ArrayList<>();
		users.add(chessParty.black().id());
		users.add(chessParty.white().id());

		gameCreate(chessParty.id(), users);

	}

	public void gameCreate(LiveGameStartEvent event) {
		final LiveGameDTO liveGame = event.dto();
		final List<UUID> users = new ArrayList<>();
		users.add(liveGame.playerSides().get(PlayerColor.WHITE));
		users.add(liveGame.playerSides().get(PlayerColor.BLACK));

		gameCreate(liveGame.id(), users);
	}
	
	private void gameCreate(Long id, List<UUID> users) {
		final GameCreatedDTO gameCreatedDTO = new GameCreatedDTO(id);
		final Message<GameCreatedDTO> createdGame = MessageBuilder
			.withPayload(gameCreatedDTO)
			.setHeader("type", "gameCreated")
			.build();
		
		users.forEach(user -> messagingTemplate.convertAndSendToUser(
			user.toString(), "/topic/matchmaking/queue", createdGame));
	}

	public void gameFinish(GameFinishEvent event) {
		final Message<GameResultMessage> gameResult = MessageBuilder
			.withPayload(new GameResultMessage(event.status()))
			.setHeader("type", "gameFinish")
			.build();

		messagingTemplate.convertAndSend("/topic/game/" + event.finishedGame().id(), gameResult);
	}

	public void move(MoveAcceptedEvent event) {
		final Message<MoveResponse> message = MessageBuilder
			.withPayload(new MoveResponse(event.move(), event.timers(), Instant.now().toEpochMilli()))
			.setHeader("type", "move")
			.build();
		messagingTemplate.convertAndSend("/topic/game/" + event.gameId(), message);
	}

}
