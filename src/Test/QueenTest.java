package Test;

import model.*;
import model.PieceColor.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GameWindow;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    private Board chessBoard;

    @BeforeEach
    void initializeTestEnvironment() throws IOException {
        GameWindow window = null;
        chessBoard = new Board(window);

        // Clear all squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                chessBoard.getSquareArray()[row][col].setOccupyingPiece(null);
            }
        }

        chessBoard.getWhitePieces().clear();
        chessBoard.getBlackPieces().clear();
    }


    @Test
    void verifyCornerPositionMovement() throws IOException {
        Square corner = chessBoard.getSquare("h1");
        Queen queen = new Queen(PieceColor.BLACK, corner, "/bqueen.png");
        corner.setOccupyingPiece(queen);

        List<Square> moves = queen.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("h8"))), // Vertical
                () -> assertTrue(moves.contains(chessBoard.getSquare("a1"))), // Horizontal
                () -> assertTrue(moves.contains(chessBoard.getSquare("a8"))), // Diagonal
                () -> assertEquals(21, moves.size())
        );
    }

    @Test
    void testBlockedByAllies() throws IOException {
        Square position = chessBoard.getSquare("d4");
        Queen queen = new Queen(PieceColor.WHITE, position, "/wqueen.png");
        position.setOccupyingPiece(queen);

        // Block all directions
        String[] blockingPositions = {"d5", "e5", "e4", "e3", "d3", "c3", "c4", "c5"};
        for (String pos : blockingPositions) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Rook(PieceColor.WHITE, chessBoard.getSquare(pos), "/wrook.png"));
        }

        assertTrue(queen.getLegalMoves(chessBoard).isEmpty());
    }


    @Test
    void testEdgePositionMovement() throws IOException {
        Square edge = chessBoard.getSquare("a4");
        Queen queen = new Queen(PieceColor.WHITE, edge, "/wqueen.png");
        edge.setOccupyingPiece(queen);

        List<Square> moves = queen.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("a8"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("h4"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("e8"))),
                () -> assertEquals(21, moves.size())
        );
    }



    @Test
    void testMaximumRangeMovement() throws IOException {
        Square corner = chessBoard.getSquare("a1");
        Queen queen = new Queen(PieceColor.BLACK, corner, "/bqueen.png");
        corner.setOccupyingPiece(queen);

        List<Square> moves = queen.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("a8"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("h1"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("h8"))),
                () -> assertEquals(21, moves.size())
        );
    }



    @Test
    void testNoMovementWhenCompletelySurrounded() throws IOException {
        Square center = chessBoard.getSquare("d4");
        Queen queen = new Queen(PieceColor.WHITE, center, "/wqueen.png");
        center.setOccupyingPiece(queen);

        // Completely surround with pieces
        String[] surrounding = {"d5", "e5", "e4", "e3", "d3", "c3", "c4", "c5"};
        for (String pos : surrounding) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Rook(PieceColor.WHITE, chessBoard.getSquare(pos), "/wrook.png"));
        }

        assertTrue(queen.getLegalMoves(chessBoard).isEmpty());
    }

    @Test
    void testDiagonalCaptureOnly() throws IOException {
        Square position = chessBoard.getSquare("f2");
        Queen queen = new Queen(PieceColor.BLACK, position, "/bqueen.png");
        position.setOccupyingPiece(queen);

        // Block horizontal/vertical with friends
        chessBoard.getSquare("f3").setOccupyingPiece(
                new Knight(PieceColor.BLACK, chessBoard.getSquare("f3"), "/bknight.png"));
        chessBoard.getSquare("f1").setOccupyingPiece(
                new Knight(PieceColor.BLACK, chessBoard.getSquare("f1"), "/bknight.png"));
        chessBoard.getSquare("e2").setOccupyingPiece(
                new Knight(PieceColor.BLACK, chessBoard.getSquare("e2"), "/bknight.png"));
        chessBoard.getSquare("g2").setOccupyingPiece(
                new Knight(PieceColor.BLACK, chessBoard.getSquare("g2"), "/bknight.png"));

        // Place diagonal enemies
        String[] enemies = {"e1", "e3", "g1", "g3"};
        for (String pos : enemies) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Pawn(PieceColor.WHITE, chessBoard.getSquare(pos), "/wpawn.png"));
        }

        List<Square> moves = queen.getLegalMoves(chessBoard);
        assertEquals(4, moves.size());
        for (String pos : enemies) {
            assertTrue(moves.contains(chessBoard.getSquare(pos)));
        }
    }
}