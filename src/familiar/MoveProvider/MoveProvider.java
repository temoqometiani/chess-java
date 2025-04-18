package familiar.MoveProvider;

import model.Board;
import model.Piece;
import model.Square;

public class MoveProvider implements MoveProviderStrategy{

    private final Piece piece;

    public MoveProvider(Piece piece) {
        this.piece = piece;
    }

    @Override
    public boolean executeMove(Board board, Square destination) {
        Piece occup = destination.getOccupyingPiece();

        if (occup != null) {
            if (occup.getColor() == piece.getColor()) return false;
            else board.capturePiece(destination,piece);
        }

        piece.getPosition().removePiece();
        piece.setPosition(destination);
        piece.getPosition().put(piece);
        return true;
    }
}