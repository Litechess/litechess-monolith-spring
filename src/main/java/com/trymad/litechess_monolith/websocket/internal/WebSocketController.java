package com.trymad.litechess_monolith.websocket.internal;

import com.trymad.litechess_monolith.websocket.MoveEventDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("{gameId}/move")
    @SendTo("/topic/{gameId}/move")
    public MoveEventDTO movSend(@Payload MoveEventDTO dto, @DestinationVariable("gameId") String gameId) {
        System.out.println(dto);
        return dto;
    }
}