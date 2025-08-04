package com.trymad.litechess_monolith.websocket.internal;

import com.trymad.litechess_monolith.websocket.MoveRequest;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final MoveService moveService;

    @MessageMapping("game/{gameId}")
    // @SendTo("/topic/{gameId}/move")
    public void acceptMove(@Payload MoveRequest moveRequest, @DestinationVariable("gameId") Long gameId, Principal principal) {
        moveService.publishMoveRequest(moveRequest, gameId, principal);
    }
}