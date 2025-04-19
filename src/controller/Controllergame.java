package controller;

import familiar.MateThreat.CheckmateDetector;
import model.*;
import model.PieceColor.PieceColor;
import view.GameWindow;
import java.util.List;

public class Controllergame {

    private final Board board;
    private final GameWindow gameWindow;

    public Controllergame(Board board, GameWindow gameWindow) {
        this.board = board;
        this.gameWindow = gameWindow;
    }

    public void handleMousePressed(Square square) {
        if (square.isOccupied()) {
            handleValidPieceSelection(square);
        }
    }

    private void handleValidPieceSelection(Square square) {
        Piece piece = square.getOccupyingPiece();
        if (isValidPlayerPiece(piece)) {
            board.setCurrPiece(piece);
            square.setDisplay(false);
        }
    }

    private boolean isValidPlayerPiece(Piece piece) {
        boolean isBlackOnWhiteTurn = piece.getColor() == PieceColor.BLACK && board.isWhiteTurn();
        boolean isWhiteOnBlackTurn = piece.getColor() == PieceColor.WHITE && !board.isWhiteTurn();
        return !(isBlackOnWhiteTurn || isWhiteOnBlackTurn);
    }

    public void handleMouseReleased(Square targetSquare) {
        Piece curr = board.getCurrPiece();
        if (curr != null) {
            processPieceMovement(curr, targetSquare);
        }
    }

    private void processPieceMovement(Piece curr, Square targetSquare) {
        List<Square> legalMoves = curr.getLegalMoves(board);
        if (legalMoves.contains(targetSquare)) {
            attemptMove(curr, targetSquare);
        } else {
            curr.getPosition().setDisplay(true);
        }
        board.setCurrPiece(null);
    }

    private void attemptMove(Piece curr, Square targetSquare) {
        Movement move = createMovement(curr, targetSquare);
        CheckmateDetector cd = new CheckmateDetector(board);
        PieceColor currentColor = getCurrentColor();
        PieceColor opponentColor = getOpponentColor();

        curr.move(targetSquare, board);

        if (cd.isInCheck(opponentColor)) {
            move.undo(board);
            System.out.println("check play another move");
            return;
        }

        if (cd.isCheckMate(currentColor)) {
            System.out.println("mate");
            return;
        }

        board.toggleTurn();
    }

    private Movement createMovement(Piece curr, Square targetSquare) {
        Movement move = new Movement(curr, curr.getPosition(), targetSquare);
        PieceColor color = board.getTurn() ? PieceColor.BLACK : PieceColor.WHITE;

        if (targetSquare.isOccupied() && targetSquare.getColor() == color) {
            move.setCapturePiece(targetSquare.getOccupyingPiece());
        }
        return move;
    }

    private PieceColor getCurrentColor() {
        return board.getTurn() ? PieceColor.BLACK : PieceColor.WHITE;
    }

    private PieceColor getOpponentColor() {
        return board.getTurn() ? PieceColor.WHITE : PieceColor.BLACK;
    }
}