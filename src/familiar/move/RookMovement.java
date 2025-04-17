package familiar.move;

import familiar.StrategyMove;
import model.Board;
import model.Piece;
import model.Square;
import familiar.move.MovementHelper;

import java.util.List;

public class RookMovement implements StrategyMove {

    private final Piece rook;

    public RookMovement(Piece rook) {
        this.rook = rook;
    }


    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        return MovementHelper.getHorizontalVerticalMoves(chessBoard,rook);
    }

}
