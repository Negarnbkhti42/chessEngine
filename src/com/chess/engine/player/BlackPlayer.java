package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.Alliance;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {

    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        //black's kingSide castle
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getTile(0, 5).isTileOccupied() &&
                    !this.board.getTile(0, 6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(0, 7);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(0, 5, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(0, 6, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(board, this.playerKing, 0, 6,
                                (Rook) rookTile.getPiece(), rookTile.getTILE_COORDINATE_ROW(), rookTile.getTILE_COORDINATE_COLUMN(),
                                0, 5));
                    }
                }
            }

            //black's queenSide castle
            if (!this.board.getTile(0, 1).isTileOccupied() &&
                    !this.board.getTile(0, 2).isTileOccupied() &&
                    !this.board.getTile(0, 3).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(0, 0);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(0, 1, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(0, 2, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(0, 3, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(board, this.getPlayerKing(), 0, 2,
                                (Rook) rookTile.getPiece(), rookTile.getTILE_COORDINATE_ROW(), rookTile.getTILE_COORDINATE_COLUMN(),
                                0, 3));
                    }

                }
            }
        }
        return kingCastles;
    }
}
