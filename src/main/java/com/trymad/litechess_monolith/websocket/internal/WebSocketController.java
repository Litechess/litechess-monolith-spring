package com.trymad.litechess_monolith.websocket.internal;

import com.trymad.litechess_monolith.matchmaking.GameMakerService;
import com.trymad.litechess_monolith.websocket.CreateGameRequest;
import com.trymad.litechess_monolith.websocket.MoveRequest;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final MoveService moveService;

    @Qualifier("eventGameMakerService")
    private final GameMakerService gameMakerService;

    @MessageMapping("game/{gameId}")
    // @SendTo("/topic/{gameId}/move")
    public void acceptMove(@Payload MoveRequest moveRequest, @DestinationVariable("gameId") Long gameId, Principal principal) {
        moveService.publishMoveRequest(moveRequest, gameId, principal);
    }

    @MessageMapping("matchmaking/queue")
    public void findGame(@Payload CreateGameRequest gameRequest, Principal principal) {
        System.out.println("request to sub");
        gameMakerService.addInQueue(gameRequest, UUID.fromString(principal.getName()));
    }
}