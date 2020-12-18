package player;

import board.Board;
import board.Move;
import board.Tile;
import pieces.Alliance;
import pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
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
                        kingCastles.add(null);
                        //TODO: add a castle move
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
                        kingCastles.add(null);
                        //TODO: add castle move
                    }

                }
            }
        }

        return kingCastles;
    }


}
