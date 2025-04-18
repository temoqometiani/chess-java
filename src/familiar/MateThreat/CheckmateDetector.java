package familiar.MateThreat;

import familiar.MateThreat.DetectorHelper;
import model.*;
import model.PieceColor.PieceColor;
import java.util.*;

public class CheckmateDetector implements DetectorHelper {

    private final Board board;

    public CheckmateDetector(Board board) {
        this.board = board;
    }
    private List<Movement> getAllPossibleMoves(PieceColor color) {
        List<Piece> pieces = color == PieceColor.BLACK ?
                board.getBlackPieces() : board.getWhitePieces();
        List<Movement> moves = new ArrayList<>();

        for (Piece piece : pieces) {
            Square from = piece.getPosition();
            for (Square to : piece.getLegalMoves(board)) {
                if (!to.isOccupied() || to.getOccupyingPiece().getColor() != color) {
                    Movement move = new Movement(piece, from, to);
                    if (to.isOccupied()) {
                        move.setCapturePiece(to.getOccupyingPiece());
                    }
                    moves.add(move);
                }
            }
        }
        return moves;
    }
    @Override
    public boolean isInCheck(PieceColor color) {
        King king = color == PieceColor.BLACK ? board.getBlackKing() : board.getWhiteKing();
        return board.isSquareUnderThreat(king.getPosition(), color);
    }
    @Override
    public boolean isStalemate(PieceColor color) {
        if (isInCheck(color)) {
            return false;
        }

        for (Movement move : getAllPossibleMoves(color)) {
            move.getMovedPiece().move(move.getTo(), board);
            boolean causesCheck = isInCheck(color);
            move.undo(board);

            if (!causesCheck) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean isCheckMate(PieceColor color) {
        if (!isInCheck(color)) {
            return false;
        }

        for (Movement move : getAllPossibleMoves(color)) {
            move.getMovedPiece().move(move.getTo(), board);
            boolean stillInCheck = isInCheck(color);
            move.undo(board);

            if (!stillInCheck) {
                return false;
            }
        }
        return true;
    }




}