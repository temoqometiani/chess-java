package familiar.MateThreat;

import model.Board;
import model.Piece;
import model.Square;
import model.PieceColor.PieceColor;

import java.util.List;

public class ThreatDetection implements Threatfinder{

    private Board board;
    @Override
    public List<Piece> getThreatsTo(Square square, PieceColor color) {
        return null;
    }
    @Override
    public boolean isSquareUnderThreat(Square square, PieceColor color) {
        return false;
    }
}