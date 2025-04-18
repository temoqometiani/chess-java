package Test;

import familiar.MateThreat.*;
import model.*;
import model.PieceColor.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class MateTest {

    private Board chessBoard;
    private CheckmateDetector mateDetector;

    @BeforeEach
    void initializeTestEnvironment() throws IOException {
        chessBoard = new Board(null);
        mateDetector = new CheckmateDetector(chessBoard);
        clearChessboard();
    }

    private void clearChessboard() {
        chessBoard.getWhitePieces().clear();
        chessBoard.getBlackPieces().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                chessBoard.getSquareArray()[row][col].setOccupyingPiece(null);
            }
        }
    }

    private void placePiece(String position, Piece piece) {
        Square square = chessBoard.getSquare(position);
        square.setOccupyingPiece(piece);
        if (piece.getColor() == PieceColor.WHITE) {
            chessBoard.getWhitePieces().add(piece);
        } else {
            chessBoard.getBlackPieces().add(piece);
        }
    }


    @Test
    void verifyCheckCanBeBlockedScenario() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("d1", new Queen(PieceColor.WHITE, chessBoard.getSquare("d1"), "/wqueen.png"));

        placePiece("e3", new Rook(PieceColor.BLACK, chessBoard.getSquare("e3"), "/brook.png"));

        assertFalse(mateDetector.isCheckMate(PieceColor.WHITE));
    }

    @Test
    void verifyKingEscapePossibleScenario() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("a1", new Rook(PieceColor.BLACK, chessBoard.getSquare("a1"), "/brook.png"));

        assertFalse(mateDetector.isCheckMate(PieceColor.WHITE));
    }

    @Test
    void verifyAttackerCapturePossibleScenario() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("d2", new Queen(PieceColor.WHITE, chessBoard.getSquare("d2"), "/wqueen.png"));

        placePiece("e2", new Rook(PieceColor.BLACK, chessBoard.getSquare("e2"), "/brook.png"));

        assertFalse(mateDetector.isCheckMate(PieceColor.WHITE));
    }

    @Test
    void verifyDoublePieceCheckmate() throws IOException {
        placePiece("g1", new King(PieceColor.WHITE, chessBoard.getSquare("g1"), "/wking.png"));
        placePiece("h2", new Pawn(PieceColor.WHITE, chessBoard.getSquare("h2"), "/wpawn.png"));

        placePiece("f1", new Rook(PieceColor.BLACK, chessBoard.getSquare("f1"), "/brook.png"));
        placePiece("h3", new Bishop(PieceColor.BLACK, chessBoard.getSquare("h3"), "/bbishop.png"));

        assertTrue(mateDetector.isCheckMate(PieceColor.WHITE));
    }


    @Test
    void verifyNotCheckmateWhenKingCanCapture() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("e2", new Rook(PieceColor.BLACK, chessBoard.getSquare("e2"), "/brook.png"));

        assertFalse(mateDetector.isCheckMate(PieceColor.WHITE));
    }
}
