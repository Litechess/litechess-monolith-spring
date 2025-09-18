package com.trymad.litechess_monolith.websocket.internal;

import com.trymad.litechess_monolith.matchmaking.GameMakerService;
import com.trymad.litechess_monolith.shared.event.EventPublisher;
import com.trymad.litechess_monolith.websocket.CreateGameRequest;
import com.trymad.litechess_monolith.websocket.MoveRequest;
import com.trymad.litechess_monolith.websocket.api.event.MoveEvent;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final EventPublisher eventPublisher;
    private final Logger logger = Logger.getLogger("websocket");

    @Qualifier("eventGameMakerService")
    private final GameMakerService gameMakerService;

    @MessageMapping("game/{gameId}")
    // @SendTo("/topic/{gameId}/move")
    public void acceptMove(@Payload MoveRequest moveRequest, @DestinationVariable("gameId") Long gameId, Principal principal) {
		final MoveEvent moveEvent = new MoveEvent(moveRequest, gameId, UUID.fromString(principal.getName()));
		logger.info(moveRequest.toString());
		eventPublisher.publish(moveEvent);
    }

    @MessageMapping("matchmaking/queue")
    public void findGame(@Payload CreateGameRequest gameRequest, Principal principal) {
        System.out.println("request to sub");
        gameMakerService.addInQueue(gameRequest, UUID.fromString(principal.getName()));
    }
}