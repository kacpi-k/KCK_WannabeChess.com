package chess.engine.player;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Move;
import chess.engine.board.Tile;
import chess.engine.pieces.Piece;
import chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static chess.engine.board.Move.*;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
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
        return this.board.blackPlayer();
    }

    @Override
    public String toString() {
        return Alliance.WHITE.toString();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {

        if(hasCastleOpportunities()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()) {
            // Roszada białych po stronie króla
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
                       rookTile.getPiece().getPieceType().isRook()) {

                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62,
                                        (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }
            // Roszada czarnych po stronie króla
            if(!this.board.getTile(59).isTileOccupied() &&
               !this.board.getTile(58).isTileOccupied() &&
               !this.board.getTile(57).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                   Player.calculateAttacksOnTile(58, opponentsLegals).isEmpty() &&
                   Player.calculateAttacksOnTile(59, opponentsLegals).isEmpty() &&
                   rookTile.getPiece().getPieceType().isRook()) {
                    if(Player.calculateAttacksOnTile(59, opponentsLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(58, opponentsLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(57, opponentsLegals).isEmpty() &&
                       rookTile.getPiece().getPieceType().isRook()) {

                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
                                        (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
