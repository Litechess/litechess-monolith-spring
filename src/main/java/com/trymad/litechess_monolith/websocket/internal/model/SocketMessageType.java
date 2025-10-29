package com.trymad.litechess_monolith.websocket.internal.model;

public enum SocketMessageType {

    MOVE("move"),
    SURRENDER("surrender"),
    GAME_FINISH("gameFinish");

    private final String name;

    SocketMessageType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

	public static SocketMessageType fromString(String value) {
		for (SocketMessageType type : SocketMessageType.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
		return null;
	}
}