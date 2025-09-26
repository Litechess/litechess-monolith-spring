package com.trymad.litechess_monolith.chessparty.internal.mapper;

import com.trymad.litechess_monolith.chessparty.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;
import com.trymad.litechess_monolith.shared.mapper.ToEntityMapper;

public interface CreateChessPartyMapper extends ToEntityMapper<ChessParty, CreatePartyDTO> {

}
