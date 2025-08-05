package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.trymad.litechess_monolith.chessgame.ChessPiece;
import com.trymad.litechess_monolith.chessgame.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.model.PlayerColor;

import io.github.wolfraam.chessgame.ChessGame;
import io.github.wolfraam.chessgame.board.PieceType;
import io.github.wolfraam.chessgame.board.Side;
import io.github.wolfraam.chessgame.board.Square;
import io.github.wolfraam.chessgame.move.Move;

public class ChessPartyEmulatorAdaptor implements ChessPartyEmulator {

	private final ChessGame chessGame;
	
	private final static Map<ChessPiece, PieceType> PIECE_TYPE_ADAPTER_TO = Map.of(
		ChessPiece.PAWN, PieceType.PAWN,
		ChessPiece.BISHOP, PieceType.BISHOP,
		ChessPiece.KING, PieceType.KING,
		ChessPiece.KNIGHT, PieceType.KNIGHT,
		ChessPiece.ROOK, PieceType.ROOK,
		ChessPiece.QUEEN, PieceType.QUEEN
	);

	private static final Map<PieceType, ChessPiece> PIECE_TYPE_ADAPTER_FROM = 
    PIECE_TYPE_ADAPTER_TO.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

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
		System.out.println(chessGame.getGameResultType());
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
			move.promotion() == null ? null : getAdaptedPiece(move.promotion());

		return new Move(from, to, promotion);
	}

	private GameMove createMove(Move move) {
		final String from = move.from.name();
		final String to = move.to.name();
		final ChessPiece promotion = move.promotion == null ? null : getGamePiece(move.promotion);

		return new GameMove(from, to, promotion);
	}

	private PieceType getAdaptedPiece(ChessPiece piece) {
		return PIECE_TYPE_ADAPTER_TO.get(piece);
	}

	private ChessPiece getGamePiece(PieceType piece) {
		return PIECE_TYPE_ADAPTER_FROM.get(piece);
	}
}
