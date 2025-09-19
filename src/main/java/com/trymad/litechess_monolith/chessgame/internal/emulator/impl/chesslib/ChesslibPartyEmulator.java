package com.trymad.litechess_monolith.chessgame.internal.emulator.impl.chesslib;

import java.util.List;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import com.trymad.litechess_monolith.chessgame.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.api.model.ChessPiece;
import com.trymad.litechess_monolith.chessgame.api.model.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.chessgame.internal.model.PlayerColor;

public class ChesslibPartyEmulator implements ChessPartyEmulator {
	
	private final Board board;
	private final MoveList moveList;

	public ChesslibPartyEmulator() {
		this.board = new Board();
		this.moveList = new MoveList();
	}

	public ChesslibPartyEmulator(List<GameMove> moveList) {
		this.board = new Board();
		this.moveList = new MoveList();
		setPosition(moveList);
	}

	@Override
	public boolean isLegalMove(GameMove move) {
		final Move chessLibMove = convertToChesslibMove(move);
		return board.isMoveLegal(chessLibMove, false);
	}

	@Override
	public void move(GameMove move) {
		final Move chessLibMove = convertToChesslibMove(move);
		final boolean accepted = board.doMove(chessLibMove);
		if(accepted) moveList.add(chessLibMove);

	}

	@Override
	public void setPosition(List<GameMove> moveList) {
		moveList.forEach(move -> {
			final Move chessLibMove = convertToChesslibMove(move);
			boolean accepted = board.doMove(chessLibMove);
			if(!accepted) throw new IllegalArgumentException("Illegal move: " + move);
			this.moveList.add(chessLibMove);
		});
	}

	@Override
	public PlayerColor getCurrentTurnColor() {
		final Side side = board.getSideToMove();
		return side == Side.WHITE ? PlayerColor.WHITE : PlayerColor.BLACK;
	}

	@Override
	public String getSan() {
		return moveList.toSan();
	}

	public String getFen() {
		return board.getFen();
	}

	@Override
	public ChessGameStatus gameStatus() {
		if(board.isMated()) {
			Side winner = board.getSideToMove().flip();
			return winner == Side.WHITE ? ChessGameStatus.WIN_WHITE : ChessGameStatus.WIN_BLACK;
		}
		else if(board.isDraw()) {
			return ChessGameStatus.DRAW;
		}
		else return ChessGameStatus.NOT_FINISHED;
	}

	private Move convertToChesslibMove(GameMove gameMove) {
		final Square fromSquare = Square.fromValue(gameMove.from().toUpperCase());
		final Square toSquare = Square.fromValue(gameMove.to().toUpperCase());
		final Side side = board.getSideToMove();
		final ChessPiece chessPiece = gameMove.promotion();
		final String san = gameMove.san();
		final Piece piece = chessPiece == null ? 
			Piece.NONE : Piece.make(side, PieceType.fromSanSymbol(chessPiece.sanName()));

		final Move move = new Move(fromSquare, toSquare, piece);
		move.setSan(san);
		return move;
	}

	private GameMove convertToGameMove(Move chesslibMove) {
		final String from = chesslibMove.getFrom().value().toLowerCase();
		final String to = chesslibMove.getTo().value().toLowerCase();
		final Piece piece = chesslibMove.getPromotion();
		final String san = chesslibMove.getSan();
		final ChessPiece promotion = piece == Piece.NONE ? null : ChessPiece.fromLetter.get(piece.getSanSymbol().toLowerCase());

		return new GameMove(from, to, promotion, san);
	}

	@Override
	public List<GameMove> getMoveList() {
		return moveList.stream()
				.map(this::convertToGameMove)
				.toList();
	}
	
}
