package familiar.MateThreat;
import model.Piece;
import model.Square;
import model.PieceColor.PieceColor;

import java.util.List;


public interface Threatfinder {

    boolean isSquareUnderThreat(Square square, PieceColor color);
    List<Piece> getThreatsTo(Square square,PieceColor color);
}