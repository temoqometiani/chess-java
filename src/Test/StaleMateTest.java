package Test;

import model.*;
import familiar.MateThreat.*;
import model.PieceColor.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class StaleMateTest {

    private Board chessBoard;
    private CheckmateDetector positionAnalyzer;

    @BeforeEach
    void initializeTestEnvironment() throws IOException {
        chessBoard = new Board(null);
        positionAnalyzer = new CheckmateDetector(chessBoard);
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
    void verifyStandardStalematePosition() throws IOException {
        // King with no legal moves but not in check
        placePiece("h1", new King(PieceColor.WHITE, chessBoard.getSquare("h1"), "/wking.png"));
        placePiece("f2", new King(PieceColor.BLACK, chessBoard.getSquare("f2"), "/bking.png"));
        placePiece("g3", new Queen(PieceColor.BLACK, chessBoard.getSquare("g3"), "/bqueen.png"));

        assertAll(
                () -> assertTrue(positionAnalyzer.isStalemate(PieceColor.WHITE)),
                () -> assertFalse(positionAnalyzer.isStalemate(PieceColor.BLACK))
        );
    }


    @Test
    void verifyCheckPreventsStalemate() throws IOException {
        // King in check cannot be stalemate
        placePiece("h1", new King(PieceColor.WHITE, chessBoard.getSquare("h1"), "/wking.png"));
        placePiece("h2", new Queen(PieceColor.BLACK, chessBoard.getSquare("h2"), "/bqueen.png"));

        assertFalse(positionAnalyzer.isStalemate(PieceColor.WHITE));
    }

    @Test
    void verifyAvailableMovesPreventStalemate() throws IOException {
        // King has legal moves available
        placePiece("e4", new King(PieceColor.WHITE, chessBoard.getSquare("e4"), "/wking.png"));
        placePiece("e6", new King(PieceColor.BLACK, chessBoard.getSquare("e6"), "/bking.png"));
        placePiece("f5", new Rook(PieceColor.BLACK, chessBoard.getSquare("f5"), "/brook.png"));

        assertFalse(positionAnalyzer.isStalemate(PieceColor.WHITE));
    }

    @Test
    void verifyBlockedPawnCreatesStalemate() throws IOException {
        // Pawn cannot move and blocks stalemate
        placePiece("a1", new King(PieceColor.WHITE, chessBoard.getSquare("a1"), "/wking.png"));
        placePiece("b2", new Pawn(PieceColor.WHITE, chessBoard.getSquare("b2"), "/wpawn.png"));
        placePiece("b3", new Bishop(PieceColor.BLACK, chessBoard.getSquare("b3"), "/bbishop.png"));
        placePiece("c2", new Queen(PieceColor.BLACK, chessBoard.getSquare("c2"), "/bqueen.png"));

        assertTrue(positionAnalyzer.isStalemate(PieceColor.WHITE));
    }



    @Test
    void verifyInsufficientMaterialCase() throws IOException {
        // King vs King scenario
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("e8", new King(PieceColor.BLACK, chessBoard.getSquare("e8"), "/bking.png"));

        assertFalse(positionAnalyzer.isStalemate(PieceColor.WHITE));
    }

    @Test
    void verifyBishopAndKingCase() throws IOException {
        // King and bishop vs king
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("c1", new Bishop(PieceColor.WHITE, chessBoard.getSquare("c1"), "/wbishop.png"));
        placePiece("e8", new King(PieceColor.BLACK, chessBoard.getSquare("e8"), "/bking.png"));

        assertFalse(positionAnalyzer.isStalemate(PieceColor.BLACK));
    }

    @Test
    void verifyKnightAndKingCase() throws IOException {
        // King and knight vs king
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("g1", new Knight(PieceColor.WHITE, chessBoard.getSquare("g1"), "/wknight.png"));
        placePiece("e8", new King(PieceColor.BLACK, chessBoard.getSquare("e8"), "/bking.png"));

        assertFalse(positionAnalyzer.isStalemate(PieceColor.BLACK));
    }

    @Test
    void verifyBishopPairStalemate() throws IOException {
        // Two bishops create stalemate
        placePiece("a1", new King(PieceColor.WHITE, chessBoard.getSquare("a1"), "/wking.png"));
        placePiece("b1", new Bishop(PieceColor.BLACK, chessBoard.getSquare("b1"), "/bbishop.png"));
        placePiece("a2", new Bishop(PieceColor.BLACK, chessBoard.getSquare("a2"), "/bbishop.png"));
        placePiece("c3", new King(PieceColor.BLACK, chessBoard.getSquare("c3"), "/bking.png"));

        assertTrue(positionAnalyzer.isStalemate(PieceColor.WHITE));
    }
}