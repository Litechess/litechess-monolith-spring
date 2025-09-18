package com.trymad.litechess_monolith.chessgame.api.dto;

import com.trymad.litechess_monolith.chessgame.PlayerInfo;

public record CreatePartyDTO(PlayerInfo white, PlayerInfo black) {
	
}
