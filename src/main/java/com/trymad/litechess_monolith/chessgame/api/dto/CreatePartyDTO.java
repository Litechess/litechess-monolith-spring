package com.trymad.litechess_monolith.chessgame.api.dto;

import com.trymad.litechess_monolith.chessgame.api.model.PlayerInfo;

public record CreatePartyDTO(PlayerInfo white, PlayerInfo black) {
	
}
