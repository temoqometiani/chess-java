package familiar.MateThreat;

import familiar.MateThreat.DetectorHelper;
import model.*;
import model.PieceColor.PieceColor;

import java.util.*;

/**
 * Component of the Chess game that detects check mates in the game.
 *
 * @author Jussi Lundstedt
 *
 */
public class CheckmateDetector implements DetectorHelper {

    private final Board board;

    public CheckmateDetector(Board board) {
        this.board = board;
    }

    @Override
    public boolean isInCheck(PieceColor color) {
        King king = color == PieceColor.BLACK ? board.getBlackKing() : board.getWhiteKing();
        return board.isSquareUnderThreat(king.getPosition(), color);
    }

    @Override
    public boolean isCheckMate(PieceColor color) {
        // Not checkmate if not in check
        if (!isInCheck(color)) {
            return false;
        }

        // Get all possible moves for this color
        List<Movement> possibleMoves = getAllPossibleMoves(color);

        // Try each move to see if any can get out of check
        return possibleMoves.stream().allMatch(move -> {
            // Execute move
            move.getMovedPiece().move(move.getTo(), board);

            // Check if still in check
            boolean stillInCheck = isInCheck(color);

            // Debug output
            System.out.println(move);
            System.out.println(stillInCheck);

            // Undo move
            move.undo(board);

            // Return whether still in check after this move
            return stillInCheck;
        });
    }

    @Override
    public boolean isStalemate(PieceColor color) {
        // Already in check means not stalemate
        if (isInCheck(color)) {
            return false;
        }

        // Get all possible moves
        List<Movement> possibleMoves = getAllPossibleMoves(color);

        // Check if every move would put in check - that's stalemate
        return possibleMoves.stream().allMatch(move -> {
            // Try the move
            move.getMovedPiece().move(move.getTo(), board);

            // See if this would cause check
            boolean causesCheck = isInCheck(color);

            // Debug output
            System.out.println(move);
            System.out.println(causesCheck);

            // Revert the move
            move.undo(board);

            return causesCheck;
        });
    }

    /**
     * Collects all possible legal moves for pieces of the given color
     */
    private List<Movement> getAllPossibleMoves(PieceColor color) {
        // Get pieces of the requested color
        List<Piece> pieces = color == PieceColor.BLACK ?
                board.getBlackPieces() : board.getWhitePieces();

        ArrayList<Movement> moves = new ArrayList<>();

        // For each piece
        for (Piece piece : pieces) {
            // Get legal moves
            List<Square> legalSquares = piece.getLegalMoves(board);

            // Current position
            Square currentPos = piece.getPosition();

            // For each possible destination square
            for (Square destination : legalSquares) {
                // Skip if occupied by own piece
                if (destination.isOccupied() &&
                        destination.getOccupyingPiece().getColor() == color) {
                    continue;
                }

                // Create movement
                Movement movement = new Movement(piece, currentPos, destination);

                // If capturing opponent piece
                if (destination.isOccupied() &&
                        destination.getOccupyingPiece().getColor() != color) {
                    movement.setCapturePiece(destination.getOccupyingPiece());
                }

                moves.add(movement);
            }
        }

        return moves;
    }
}