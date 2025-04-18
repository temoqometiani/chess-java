package familiar.MateThreat;

import model.PieceColor.PieceColor;

public interface DetectorHelper {
    boolean isInCheck(PieceColor color);
    boolean isCheckMate(PieceColor color);
    boolean isStalemate(PieceColor color);

}
