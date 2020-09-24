package player;

import board.Board;
import board.Move;
import pieces.King;
import pieces.Piece;

import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishedKing();
        this.legalMoves = legalMoves;
    }

    private King establishedKing() {
        for (Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("should not reach here");
    }

    public abstract Collection<Piece> getActivePieces();
}
