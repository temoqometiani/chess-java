package familiar.move;

import familiar.StrategyMove;
import model.Board;
import model.Piece;
import model.Square;

import java.util.LinkedList;
import java.util.List;

public class KingMove implements StrategyMove {

    private Piece king;

    public KingMove(Piece king) {
        this.king = king;
    }


    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();

        Square[][] board = chessBoard.getSquareArray();

        Square position = king.getPosition();
        int x = position.getX();
        int y = position.getY();


        for (int i = 1; i > -2; i--) {
            for (int j = 1; j > -2; j--) {
                if (!(i == 0 && j == 0)) {
                    try {
                        if (!board[y + j][x + i].isOccupied() ||
                                board[y + j][x + i].getOccupyingPiece().getColor()
                                        != king.getColor()) {
                            legalMoves.add(board[y + j][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }

        return legalMoves;
    }


}
