package com.trymad.litechess_monolith.websocket.internal;

import com.trymad.litechess_monolith.websocket.MoveEventDTO;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    
    @MessageMapping("{gameId}/move")
    @SendTo("/topic/{gameId}/move")
    public MoveEventDTO movSend(@Payload MoveEventDTO dto, @DestinationVariable("gameId") String gameId, JwtAuthenticationToken principal) {
        System.out.println(dto);
        System.out.println("SENDER: " + principal.getName());
        return dto;
    }
}