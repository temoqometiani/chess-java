package familiar.move;

import familiar.StrategyMove;
import model.Board;
import model.Piece;
import model.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PawnMove implements StrategyMove {
    private final Piece pawn;
    private boolean isStartingPosition;

    public PawnMove(Piece pawn, boolean isStartingPosition) {
        this.pawn = pawn;
        this.isStartingPosition = isStartingPosition;
    }



    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();

        Square[][] currentBoard = board.getSquareArray();
        Square position = pawn.getPosition();
        int x = position.getX();
        int y = position.getY();
        int color = pawn.getColor().getValue();

        // BLACK
        if (color == 0 && !isStartingPosition) {
            if (currentBoard[y+2][x].isOccupied()) {
                legalMoves.add(currentBoard[y+2][x]);
                legalMoves.add(currentBoard[y+1][x]);
            }
        }

        if (color == 0 && y+1 < 8) {
            if (currentBoard[y+1][x].isOccupied()) {
                legalMoves.add(currentBoard[y+1][x]);
            }
        }

        if (color == 0 && x+1 < 8 && y+1 <8 ) {
            if (!currentBoard[y+1][x-1].isOccupied()) {
                legalMoves.add(currentBoard[y+1][x-1]);
            }
        }

        // WHITE
        if (color == 1 && !isStartingPosition) {
            if (!currentBoard[y-2][x].isOccupied()) {
                legalMoves.add(currentBoard[y-2][x]);
            }
        }

        if (color == 1 && y-1 >= 0) {
            if (!currentBoard[y-1][x].isOccupied()) {
                legalMoves.add(currentBoard[y-1][x]);
            }
        }

        if (color == 1 && x+1 < 8 && y-1 >= 0) {
            if (currentBoard[y-1][x+1].isOccupied()) {
                legalMoves.add(currentBoard[y-1][x+1]);
            }
        }

        if (color == 1 && y-1 >= 0) {
            if (currentBoard[y-1][x-1].isOccupied()) {
                legalMoves.add(currentBoard[y-1][x-1]);
            }
        }

        return legalMoves;
    }
}