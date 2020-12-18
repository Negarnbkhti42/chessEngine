package player;

import board.Board;
import board.Move;
import board.Tile;
import pieces.Alliance;
import pieces.Piece;
import pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static board.Move.*;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        //white's kingSide castle
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getTile(7, 5).isTileOccupied() &&
                    !this.board.getTile(7, 6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7, 7);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(7, 5, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(7, 6, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(board, this.playerKing, 7, 6,
                                (Rook) rookTile.getPiece(), rookTile.getTILE_COORDINATE_ROW(), rookTile.getTILE_COORDINATE_COLUMN()
                                , 7, 5));
                    }
                }
            }

            //white's queenSide castle
            if (!this.board.getTile(7, 1).isTileOccupied() &&
                    !this.board.getTile(7, 2).isTileOccupied() &&
                    !this.board.getTile(7, 3).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7, 0);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(7, 1, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(7, 2, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(7, 3, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(board, this.playerKing, 7, 2,
                                (Rook) rookTile.getPiece(), rookTile.getTILE_COORDINATE_ROW(), rookTile.getTILE_COORDINATE_COLUMN(),
                                7, 3));
                    }

                }
            }
        }

        return kingCastles;
    }


}
