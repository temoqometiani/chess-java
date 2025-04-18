package Test;

import model.*;
import familiar.MateThreat.*;
import model.PieceColor.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class CheckState {

    private Board chessBoard;
    private CheckmateDetector threatAnalyzer;

    @BeforeEach
    void initializeTestEnvironment() throws IOException {
        chessBoard = new Board(null);
        threatAnalyzer = new CheckmateDetector(chessBoard);
        clearChessboard();
    }

    private void clearChessboard() {
        chessBoard.getWhitePieces().clear();
        chessBoard.getBlackPieces().clear();
        for (Square[] row : chessBoard.getSquareArray()) {
            for (Square square : row) {
                square.setOccupyingPiece(null);
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
    void verifyRookThreatOnSameFile() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("e8", new Rook(PieceColor.BLACK, chessBoard.getSquare("e8"), "/brook.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyNoThreatFromDistantRook() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("a8", new Rook(PieceColor.BLACK, chessBoard.getSquare("a8"), "/brook.png"));

        assertFalse(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyBishopDiagonalThreat() throws IOException {
        placePiece("e4", new King(PieceColor.WHITE, chessBoard.getSquare("e4"), "/wking.png"));
        placePiece("h7", new Bishop(PieceColor.BLACK, chessBoard.getSquare("h7"), "/bbishop.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyQueenCombinedThreat() throws IOException {
        placePiece("d4", new King(PieceColor.WHITE, chessBoard.getSquare("d4"), "/wking.png"));
        placePiece("d7", new Queen(PieceColor.BLACK, chessBoard.getSquare("d7"), "/bqueen.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyKnightLShapedThreat() throws IOException {
        placePiece("e4", new King(PieceColor.WHITE, chessBoard.getSquare("e4"), "/wking.png"));
        placePiece("f6", new Knight(PieceColor.BLACK, chessBoard.getSquare("f6"), "/bknight.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyPawnDiagonalAttack() throws IOException {
        placePiece("e4", new King(PieceColor.WHITE, chessBoard.getSquare("e4"), "/wking.png"));
        placePiece("d5", new Pawn(PieceColor.BLACK, chessBoard.getSquare("d5"), "/bpawn.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyBlackKingUnderThreat() throws IOException {
        placePiece("e8", new King(PieceColor.BLACK, chessBoard.getSquare("e8"), "/bking.png"));
        placePiece("e4", new Queen(PieceColor.WHITE, chessBoard.getSquare("e4"), "/wqueen.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.BLACK));
    }

    @Test
    void verifyMultipleThreatsSimultaneously() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("e8", new Rook(PieceColor.BLACK, chessBoard.getSquare("e8"), "/brook.png"));
        placePiece("a5", new Bishop(PieceColor.BLACK, chessBoard.getSquare("a5"), "/bbishop.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifySurroundedKingSafety() throws IOException {
        placePiece("e4", new King(PieceColor.WHITE, chessBoard.getSquare("e4"), "/wking.png"));
        placePiece("d3", new Pawn(PieceColor.WHITE, chessBoard.getSquare("d3"), "/wpawn.png"));
        placePiece("e3", new Pawn(PieceColor.WHITE, chessBoard.getSquare("e3"), "/wpawn.png"));
        placePiece("f3", new Pawn(PieceColor.WHITE, chessBoard.getSquare("f3"), "/wpawn.png"));

        assertFalse(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyBlockedThreatScenario() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("e3", new Pawn(PieceColor.WHITE, chessBoard.getSquare("e3"), "/wpawn.png"));
        placePiece("e8", new Rook(PieceColor.BLACK, chessBoard.getSquare("e8"), "/brook.png"));

        assertFalse(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyKnightJumpOverBlockers() throws IOException {
        placePiece("e4", new King(PieceColor.WHITE, chessBoard.getSquare("e4"), "/wking.png"));
        placePiece("e5", new Pawn(PieceColor.WHITE, chessBoard.getSquare("e5"), "/wpawn.png"));
        placePiece("f6", new Knight(PieceColor.BLACK, chessBoard.getSquare("f6"), "/bknight.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }



    @Test
    void verifyEdgeOfBoardThreats() throws IOException {
        placePiece("a1", new King(PieceColor.WHITE, chessBoard.getSquare("a1"), "/wking.png"));
        placePiece("a8", new Rook(PieceColor.BLACK, chessBoard.getSquare("a8"), "/brook.png"));

        assertTrue(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }

    @Test
    void verifyKingCannotPutSelfInCheck() throws IOException {
        placePiece("e1", new King(PieceColor.WHITE, chessBoard.getSquare("e1"), "/wking.png"));
        placePiece("f2", new Rook(PieceColor.BLACK, chessBoard.getSquare("f2"), "/brook.png"));

        assertFalse(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }



    @Test
    void verifyPawnForwardMovementNotThreat() throws IOException {
        placePiece("e4", new King(PieceColor.WHITE, chessBoard.getSquare("e4"), "/wking.png"));
        placePiece("e5", new Pawn(PieceColor.BLACK, chessBoard.getSquare("e5"), "/bpawn.png"));

        assertFalse(threatAnalyzer.isInCheck(PieceColor.WHITE));
    }
}