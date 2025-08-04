package com.trymad.litechess_monolith.websocket;

public record MoveRequest(
        String color,
        String from,
        String to,
        String piece,
        String captured,
        String promotion,
        String flags,
        String san,
        String lan,
        String before,
        String after
) {}