package familiar.move;

import model.Board;
import model.Piece;
import model.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovementHelper {
    private static final List<List<Integer>> HORIZONTAL_VERTICAL = Arrays.asList(
            List.of(-1, 0), List.of(1, 0), List.of(0, -1), List.of(0, 1)
    );

    private static final List<List<Integer>> DIAGONAL = Arrays.asList(
            List.of(-1, -1), List.of(1, -1), List.of(1, 1), List.of(-1, 1)
    );



    public static List<Square> diagonalMoves(Board board, Piece piece) {
        List<Square> legalMoves = new ArrayList<>();

        Square[][] currentBoard = board.getSquareArray();

        Square position = piece.getPosition();
        int x = position.getXNum(), y = position.getYNum();

        for (int i=0;  i<DIAGONAL.size();  i++) {
            int currentY = y + DIAGONAL.get(i).get(0);
            int currentX = x + DIAGONAL.get(i).get(1);

            while (isValidPosition(currentY, currentX)) {
                Square t = currentBoard[currentY][currentX];
                if (t.isOccupied()) {
                    if (t.getOccupyingPiece().getColor() != piece.getColor())
                        legalMoves.add(t);
                    break;
                }
                legalMoves.add(t);
            }
            currentX += DIAGONAL.get(i).get(1);
            currentY += DIAGONAL.get(i).get(0);
        }

        return legalMoves;
    }

    private static boolean isValidPosition(int x, int y) {
        return x>=0 && y>=0 && x<8 && y<8;
    }

    public static List<Square> getHorizontalVerticalMoves(Board board, Piece piece) {
        List<Square> legalMoves = new ArrayList<>();

        Square[][] currentBoard = board.getSquareArray();
        Square position = piece.getPosition();
        int x = position.getXNum(), y = position.getYNum();
        for (int i=0;  i<HORIZONTAL_VERTICAL.size();  i++) {
            int currentY = y + HORIZONTAL_VERTICAL.get(i).get(0);
            int currentX = x + HORIZONTAL_VERTICAL.get(i).get(1);

            while (isValidPosition(currentY, currentX)) {
                Square t = currentBoard[currentY][currentX];
                if (t.isOccupied()) {
                    if (t.getOccupyingPiece().getColor() != piece.getColor())
                        legalMoves.add(t);
                    break;
                }

                legalMoves.add(t);
                currentX += HORIZONTAL_VERTICAL.get(i).get(1);
                currentY += HORIZONTAL_VERTICAL.get(i).get(0);
            }
        }
        return legalMoves;
    }
}
