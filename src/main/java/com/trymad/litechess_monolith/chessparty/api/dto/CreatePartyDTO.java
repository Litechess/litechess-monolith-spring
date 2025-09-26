package com.trymad.litechess_monolith.chessparty.api.dto;

import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;

public record CreatePartyDTO(PlayerInfo white, PlayerInfo black, TimeControlDTO timeControl) {
	
}
