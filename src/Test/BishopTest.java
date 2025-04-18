package Test;

import model.*;
import model.PieceColor.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GameWindow;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

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
    void verifyUnobstructedDiagonalMovement() throws IOException {
        Square center = chessBoard.getSquare("d4");
        Bishop bishop = new Bishop(PieceColor.WHITE, center, "/wbishop.png");
        center.setOccupyingPiece(bishop);

        List<Square> possibleMoves = bishop.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("a1"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("h8"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("g1"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("a7"))),
                () -> assertEquals(13, possibleMoves.size())
        );
    }

    @Test
    void verifyCornerPositionMovement() throws IOException {
        Square corner = chessBoard.getSquare("a1");
        Bishop bishop = new Bishop(PieceColor.WHITE, corner, "/wbishop.png");
        corner.setOccupyingPiece(bishop);

        List<Square> moves = bishop.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("b2"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("h8"))),
                () -> assertEquals(7, moves.size())
        );
    }

    @Test
    void testBlockedByAllies() throws IOException {
        Square center = chessBoard.getSquare("e5");
        Bishop bishop = new Bishop(PieceColor.BLACK, center, "/bbishop.png");
        center.setOccupyingPiece(bishop);

        // Block all four diagonals
        String[] blockingPositions = {"d6", "f6", "d4", "f4"};
        for (String pos : blockingPositions) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Pawn(PieceColor.BLACK, chessBoard.getSquare(pos), "/bpawn.png"));
        }

        assertTrue(bishop.getLegalMoves(chessBoard).isEmpty());
    }


    @Test
    void testMaximumRangeMovement() throws IOException {
        Square corner = chessBoard.getSquare("a8");
        Bishop bishop = new Bishop(PieceColor.BLACK, corner, "/bbishop.png");
        corner.setOccupyingPiece(bishop);

        List<Square> moves = bishop.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("b7"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("h1"))),
                () -> assertEquals(7, moves.size())
        );
    }

    @Test
    void testSingleAvailablePath() throws IOException {
        Square position = chessBoard.getSquare("f1");
        Bishop bishop = new Bishop(PieceColor.WHITE, position, "/wbishop.png");
        position.setOccupyingPiece(bishop);

        // Block all but one diagonal
        chessBoard.getSquare("e2").setOccupyingPiece(
                new Pawn(PieceColor.BLACK, chessBoard.getSquare("e2"), "/bpawn.png"));
        chessBoard.getSquare("g2").setOccupyingPiece(
                new Pawn(PieceColor.WHITE, chessBoard.getSquare("g2"), "/wpawn.png"));

        List<Square> moves = bishop.getLegalMoves(chessBoard);
        assertEquals(1, moves.size());
        assertTrue(moves.contains(chessBoard.getSquare("e2")));
    }

    @Test
    void testNoMovementWhenSurrounded() throws IOException {
        Square center = chessBoard.getSquare("d4");
        Bishop bishop = new Bishop(PieceColor.WHITE, center, "/wbishop.png");
        center.setOccupyingPiece(bishop);

        // Completely surround with pieces
        String[] surrounding = {"c3", "c5", "e3", "e5"};
        for (String pos : surrounding) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Knight(PieceColor.WHITE, chessBoard.getSquare(pos), "/wknight.png"));
        }

        assertTrue(bishop.getLegalMoves(chessBoard).isEmpty());
    }
}