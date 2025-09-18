package com.trymad.litechess_monolith.websocket.internal;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.api.event.ChessPartyCreatedEvent;
import com.trymad.litechess_monolith.chessgame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.chessgame.api.event.MoveAcceptedEvent;
import com.trymad.litechess_monolith.matchmaking.api.event.QueueLeaveEvent;
import com.trymad.litechess_monolith.shared.event.EventPublisher;
import com.trymad.litechess_monolith.websocket.GameCreatedDTO;
import com.trymad.litechess_monolith.websocket.MoveRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

// TODO delete from queue when unsub /matchmaking/queue
public class GameMessageSender {
	
	private final SimpMessagingTemplate messagingTemplate;
	private final EventPublisher eventPublisher;

	public void gameCreate(ChessPartyCreatedEvent event) {
		final GameCreatedDTO gameCreatedDTO = new GameCreatedDTO(event.chessParty().getId());
		final Message<GameCreatedDTO> createdGame = MessageBuilder
			.withPayload(gameCreatedDTO)
			.setHeader("type", "gameCreated")
			.build();
		
		messagingTemplate.convertAndSendToUser(
			event.chessParty().getWhite().id().toString(), "/topic/matchmaking/queue", createdGame);
		messagingTemplate.convertAndSendToUser(
			event.chessParty().getBlack().id().toString(), "/topic/matchmaking/queue", createdGame);
	}

	public void gameFinish(GameFinishEvent event) {
		final ChessParty chessParty = event.chessParty();
		final Message<GameResultMessage> gameResult = MessageBuilder
			.withPayload(new GameResultMessage(chessParty.getStatus()))
			.setHeader("type", "gameFinish")
			.build();

		messagingTemplate.convertAndSend("/topic/game/" + chessParty.getId(), gameResult);
	}

	public void move(MoveAcceptedEvent event) {
		final Message<MoveRequest> message = MessageBuilder
			.withPayload(event.moveRequest())
			.setHeader("type", "move")
			.build();
		messagingTemplate.convertAndSend("/topic/game/" + event.gameId(), message);
	}

	// TODO convert to app event
	@EventListener
	void on(SessionUnsubscribeEvent event) {
		eventPublisher.publish(new QueueLeaveEvent(UUID.fromString(event.getUser().getName())));
	} 
	
}
