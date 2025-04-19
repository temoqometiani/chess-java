package controller;

import familiar.MateThreat.CheckmateDetector;
import familiar.MateThreat.DetectorHelper;
import model.Board;
import model.Movement;
import model.Piece;
import model.PieceColor.PieceColor;
import model.Square;
import view.GameWindow;

import java.util.List;

public class Controllergame {

    // Fields remain exactly the same
    private final Board board;
    protected final GameWindow gameWindow;

    // Constructor remains exactly the same
    public Controllergame(Board board, GameWindow gameWindow) {
        this.board = board;
        this.gameWindow = gameWindow;
    }

    // handleMousePressed method with improved structure
    public void handleMousePressed(Square square) {
        // Early return if square is empty
        if (!square.isOccupied()) {
            return;
        }

        Piece piece = square.getOccupyingPiece();
        boolean isWrongTurn = (piece.getColor() == PieceColor.BLACK && board.isWhiteTurn()) ||
                (piece.getColor() == PieceColor.WHITE && !board.isWhiteTurn());

        if (isWrongTurn) {
            return;
        }

        board.setCurrPiece(piece);
        square.setDisplay(false);
    }

    // handleMouseReleased method with improved structure
    public void handleMouseReleased(Square targetSquare) {
        Piece currPiece = board.getCurrPiece();

        // Early return if no piece selected
        if (currPiece == null) {
            return;
        }

        List<Square> legalMoves = currPiece.getLegalMoves(board);
        var a=legalMoves.stream().map(x->x.getPosition().toAlgebraic()).toList();
        // Move validator setup (unchanged logic)
        DetectorHelper dh = new CheckmateDetector(board) {
            @Override public boolean isInCheck(PieceColor color) {
                return false; }
            @Override public boolean isCheckMate(PieceColor color) {
                return false; }
            @Override public boolean isStalemate(PieceColor color) {
                return false; }
        };

        // Determine colors (unchanged logic)
        PieceColor color = board.getTurn() ? PieceColor.BLACK : PieceColor.WHITE;
        PieceColor checkColor = board.getTurn() ? PieceColor.WHITE : PieceColor.BLACK;

        // Process valid move
        if (legalMoves.contains(targetSquare)) {
            Movement move = createMovement(currPiece, targetSquare, color);

            // Execute the move
            currPiece.move(targetSquare, board);

            // Check for invalid moves (king in check)
            if (dh.isInCheck(checkColor)) {
                move.undo(board);
                System.out.println("check play another move");
                return;
            }

            // Check for checkmate
            if (dh.isCheckMate(color)) {
                System.out.println("mate");
                return;
            }

            board.toggleTurn();
        }
        // Handle invalid move
        else {
            currPiece.getPosition().setDisplay(true);
        }

        board.setCurrPiece(null);
    }

    // Helper method for creating Movement (extracted from original logic)
    private Movement createMovement(Piece piece, Square target, PieceColor color) {
        Movement move = new Movement(piece, piece.getPosition(), target);

        if (target.isOccupied() && target.getColor() == color) {
            move.setCapturePiece(target.getOccupyingPiece());
        }

        return move;
    }
}