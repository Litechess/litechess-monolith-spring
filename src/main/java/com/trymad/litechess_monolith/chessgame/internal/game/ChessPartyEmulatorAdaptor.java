package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.model.PlayerColor;

import io.github.wolfraam.chessgame.ChessGame;
import io.github.wolfraam.chessgame.board.PieceType;
import io.github.wolfraam.chessgame.board.Side;
import io.github.wolfraam.chessgame.board.Square;
import io.github.wolfraam.chessgame.move.Move;

public class ChessPartyEmulatorAdaptor implements ChessPartyEmulator {

	private final ChessGame chessGame;

	public ChessPartyEmulatorAdaptor() {
		this.chessGame = new ChessGame();
	}

	public ChessPartyEmulatorAdaptor(List<GameMove> moveList) {
		this.chessGame = new ChessGame();
		this.setPosition(moveList);
	}

	@Override
	public boolean isLegalMove(GameMove move) {
		return chessGame.isLegalMove(createAdaptiveMove(move));
	}

	@Override
	public void move(GameMove move) {
		final Move adaptedMove = createAdaptiveMove(move);
		chessGame.playMove(adaptedMove);
	}

	@Override
	public void setPosition(List<GameMove> moveList) {
		moveList.forEach(move -> {
			final Move adaptedMove = createAdaptiveMove(move);
			chessGame.playMove(adaptedMove); 
		});
	}

	@Override
	public PlayerColor getCurrentTurnColor() {
		return chessGame.getSideToMove() == Side.WHITE ? PlayerColor.WHITE : PlayerColor.BLACK;
	}

	@Override
	public List<GameMove> getMoves() {
		return chessGame.getMoves().stream()
			.map(this::createMove)
			.toList();
	}

	private Move createAdaptiveMove(GameMove move) {
		final Square from = Square.fromName(move.from());
		final Square to = Square.fromName(move.to());
		final PieceType promotion = 
			move.promotion() == null ? null : PieceType.valueOf(move.promotion().toUpperCase());

		return new Move(from, to, promotion);
	}

	private GameMove createMove(Move move) {
		final String from = move.from.name().toLowerCase();
		final String to = move.to.name().toLowerCase();
		final String promotion = move.promotion == null ? null : move.promotion.name().toLowerCase();

		return new GameMove(from, to, promotion);
	}
	
}
