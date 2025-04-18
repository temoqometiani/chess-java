package familiar.move;


import familiar.StrategyMove;
import model.Board;
import model.Piece;
import model.Square;

import java.util.LinkedList;
import java.util.List;

public class KnightMove implements StrategyMove {

    private final Piece knight;

    public KnightMove(Piece knight) {
        this.knight = knight;
    }

    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = chessBoard.getSquareArray();
        Square position = knight.getPosition();


        int x = position.getX();
        int y = position.getY();

        for (int i = 2; i > -3; i--) {
            for (int j = 2; j > -3; j--) {
                if((Math.abs(i) == 2) != (Math.abs(j) == 2)) {
                    if (j != 0 && i != 0) {
                        try {
                            legalMoves.add(board[y + j][x + i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }

        return legalMoves;

    }


}
