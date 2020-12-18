package player;

import board.Board;
import board.Move;
import board.Tile;
import pieces.Alliance;
import pieces.Piece;

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
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
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
                        kingCastles.add(null);
                        //TODO: add a castle move
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
                        kingCastles.add(null);
                        //TODO: add castle move
                    }

                }
            }
        }
        return kingCastles;
    }
}
