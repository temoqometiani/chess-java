package familiar.move;


import familiar.StrategyMove;
import model.Board;
import model.Piece;
import model.Square;
import familiar.move.MovementHelper;

import java.util.ArrayList;
import java.util.List;

public class QueenMove implements StrategyMove {

    private final Piece queen;

    public QueenMove(Piece queen) {
        this.queen = queen;
    }



    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> diagonalLegalMoves = MovementHelper.diagonalMoves(board, queen);
        List<Square> HorizontalVerticalLegalMoves = MovementHelper.getHorizontalVerticalMoves(board, queen);

        List<Square> result = new ArrayList<>();
        result.addAll(HorizontalVerticalLegalMoves);
        result.addAll(diagonalLegalMoves);

        return result;
    }


}