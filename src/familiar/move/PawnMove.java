package familiar.move;

import familiar.StrategyMove;
import model.Board;

import model.Piece;
import model.Square;
import familiar.MoveProvider.MoveProviderStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PawnMove implements StrategyMove {
    public final Piece pawn;
    public boolean wasMoved;
    public PawnMove(Piece piece, boolean wasMoved){
        this.pawn=piece;
        this.wasMoved=wasMoved;
    }
    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();
        Square[][] squares = board.getSquareArray();
        int x = this.pawn.getPosition().getX();
        int y = this.pawn.getPosition().getY();
        int color = this.pawn.getColor().getValue();

        // Determine movement direction based on color
        int forwardDir = (color == 0) ? 1 : -1;

        // Forward moves
        addForwardMoves(legalMoves, squares, x, y, forwardDir);

        // Capture moves
        addCaptureMoves(legalMoves, squares, x, y, forwardDir, color);

        // En passant (would need board to track last move)
        // addEnPassantMoves(legalMoves, board, x, y, forwardDir, color);

        return legalMoves;
    }

    private void addForwardMoves(List<Square> legalMoves, Square[][] squares,
                                 int x, int y, int forwardDir) {
        // Single move forward
        int newY = y + forwardDir;
        if (isValidSquare(x, newY) && !squares[newY][x].isOccupied()) {
            legalMoves.add(squares[newY][x]);

            // Double move from starting position
            if (!wasMoved) {
                newY = y + 2 * forwardDir;
                if (isValidSquare(x, newY) && !squares[newY][x].isOccupied()) {
                    legalMoves.add(squares[newY][x]);
                }
            }
        }
    }

    private void addCaptureMoves(List<Square> legalMoves, Square[][] squares,
                                 int x, int y, int forwardDir, int color) {
        // Diagonal captures
        int[] captureXs = {x - 1, x + 1};
        int newY = y + forwardDir;

        for (int captureX : captureXs) {
            if (isValidSquare(captureX, newY)) {
                Square target = squares[newY][captureX];
                if (target.isOccupied() && target.getOccupyingPiece().getColor().getValue() != color) {
                    legalMoves.add(target);
                }
                // Here you could also check en passant
            }
        }
    }

    private boolean isValidSquare(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}