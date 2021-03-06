package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.Alliance;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishedKing();
        this.legalMoves = legalMoves;
        this.legalMoves.addAll(calculateKingCastles(legalMoves, opponentMoves));
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePositionRow(),
                this.playerKing.getPiecePositionColumn(),
                opponentMoves).isEmpty();
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePositionRow, int piecePositionColumn, Collection<Move> opponentMoves) {
        ArrayList<Move> attackMoves = new ArrayList<>();

        for (Move move : opponentMoves) {
            if (piecePositionRow == move.getDestinationRow() && piecePositionColumn == move.getDestinationColumn()) {
                attackMoves.add(move);
            }
        }
        return attackMoves;
    }

    private King establishedKing() {
        for (Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("should not reach here");
    }


    public boolean isMoveLegal(Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return false;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean hasEscapeMoves() {
        for (Move move : legalMoves) {
            MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        Board transitionBoard = move.execute();
        Player currentPlayer = transitionBoard.getCurrentPlayer();
        Collection<Move> kingAttacks = Player.calculateAttacksOnTile(
                currentPlayer.getOpponent().getPlayerKing().getPiecePositionRow(),
                currentPlayer.getOpponent().getPlayerKing().getPiecePositionColumn(),
                transitionBoard.getCurrentPlayer().getLegalMoves());

        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move,MoveStatus.DONE);
    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }

    public King getPlayerKing() {
        return playerKing;
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    protected abstract Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals);
}
