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

    private final Board board;
    private final GameWindow gameWindow;

    public Controllergame(Board board, GameWindow gameWindow) {
        this.board = board;
        this.gameWindow = gameWindow;
    }

    public void handleMousePressed(Square square) {
        if (!square.isOccupied()) return;

        Piece piece = square.getOccupyingPiece();
        if ((piece.getColor() == PieceColor.BLACK && board.isWhiteTurn()) ||
                (piece.getColor() == PieceColor.WHITE && !board.isWhiteTurn())) {
            return;
        }

        board.setCurrPiece(piece);
        square.setDisplay(false);
    }

    public void handleMouseReleased(Square targetSquare) {
        Piece currPiece = board.getCurrPiece();
        if (currPiece == null) return;

        List<Square> legalMoves = currPiece.getLegalMoves(board);

        DetectorHelper dh = new CheckmateDetector(board);


        PieceColor color;
        PieceColor checkColor;

        if (board.getTurn()) {
            color = PieceColor.BLACK;
            checkColor = PieceColor.WHITE;
        } else {
            color = PieceColor.WHITE;
            checkColor = PieceColor.BLACK;
        }
        if(legalMoves.contains(targetSquare)){

            Movement move = new Movement(currPiece,currPiece.getPosition(),targetSquare);

            if(targetSquare.isOccupied() && targetSquare.getColor() == color){
                move.setCapturePiece(targetSquare.getOccupyingPiece());
            }

            currPiece.move(targetSquare,board);
            if(dh.isInCheck(checkColor)){
                move.undo(board);
                System.out.println("check play another move");
                return;
            }

            if (dh.isCheckMate(color)){
                System.out.println("mate");
                return;
            }
            board.toggleTurn();
        }else{
            currPiece.getPosition().setDisplay(true);
        }

        board.setCurrPiece(null);
    }
}