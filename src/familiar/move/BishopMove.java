package familiar.move;

import familiar.StrategyMove;
import model.Board;
import model.Piece;
import model.Square;
import familiar.move.MovementHelper;

import java.util.List;

public class BishopMove implements StrategyMove {

    private final Piece bishop;

    public BishopMove(Piece piece) {
        this.bishop = piece;
    }

    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        return MovementHelper.diagonalMoves(chessBoard, bishop);
    }


}
