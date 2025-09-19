package com.trymad.litechess_monolith.chessgame.internal.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import com.trymad.litechess_monolith.chessgame.api.model.GameMove;
import com.trymad.litechess_monolith.websocket.api.dto.MoveRequest;

@Component
public class ChessLibUtilService implements ChessUtilService {

	@Override
	public String getSan(String initFen, List<GameMove> moves) {
		final MoveList moveList = convertToMoveList(moves, initFen);
		return moveList.toSan();
	}

	@Override
	public String getFen(String initFen, List<GameMove> moves) {
		final MoveList moveList = convertToMoveList(moves, initFen);
		final String fen = moveList.getFen();
		return fen == null ? initFen : fen;
	}

	private MoveList convertToMoveList(List<GameMove> moves, String initFen) {
		final MoveList moveList = new MoveList(initFen);
		Side side = Side.WHITE;
		moves.forEach(move -> {
			final Square fromSquare = Square.fromValue(move.from().toUpperCase());
			final Square toSquare = Square.fromValue(move.to().toUpperCase());
			final Piece piece = move.promotion() == null ? 
				Piece.NONE : Piece.make(side, PieceType.fromSanSymbol(move.promotion().sanName()));
			final Move convertedMove = new Move(fromSquare, toSquare, piece);
			moveList.add(convertedMove);
		});

		return moveList;
	}

	@Override
	public GameMove toGameMove(MoveRequest request) {
		return new GameMove(request.from(), request.to(), request.promotion(), request.san());
	}
}
