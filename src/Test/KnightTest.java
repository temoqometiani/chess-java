package Test;

import model.*;
import model.PieceColor.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GameWindow;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

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
    void verifyAllPossibleLMovesFromCenter() throws IOException {
        Square center = chessBoard.getSquare("d4");
        Knight knight = new Knight(PieceColor.WHITE, center, "/wknight.png");
        center.setOccupyingPiece(knight);

        List<Square> possibleMoves = knight.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("c6"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("e6"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("f5"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("f3"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("e2"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("c2"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("b3"))),
                () -> assertTrue(possibleMoves.contains(chessBoard.getSquare("b5"))),
                () -> assertEquals(8, possibleMoves.size())
        );
    }

    @Test
    void verifyLimitedMovesFromCorner() throws IOException {
        Square corner = chessBoard.getSquare("a1");
        Knight knight = new Knight(PieceColor.BLACK, corner, "/bknight.png");
        corner.setOccupyingPiece(knight);

        List<Square> moves = knight.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("b3"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("c2"))),
                () -> assertEquals(2, moves.size())
        );
    }

    @Test
    void testBlockedByAllies() throws IOException {
        Square position = chessBoard.getSquare("e5");
        Knight knight = new Knight(PieceColor.WHITE, position, "/wknight.png");
        position.setOccupyingPiece(knight);

        // Block all possible L-moves
        String[] blockingPositions = {"d7", "f7", "g6", "g4", "f3", "d3", "c4", "c6"};
        for (String pos : blockingPositions) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Pawn(PieceColor.WHITE, chessBoard.getSquare(pos), "/wpawn.png"));
        }

        assertTrue(knight.getLegalMoves(chessBoard).isEmpty());
    }

    @Test
    void testCanCaptureEnemies() throws IOException {
        Square position = chessBoard.getSquare("c3");
        Knight knight = new Knight(PieceColor.BLACK, position, "/bknight.png");
        position.setOccupyingPiece(knight);

        // Place enemy pieces
        String[] enemyPositions = {"a2", "a4", "b1", "b5", "d1", "d5", "e2", "e4"};
        for (String pos : enemyPositions) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Bishop(PieceColor.WHITE, chessBoard.getSquare(pos), "/wbishop.png"));
        }

        List<Square> moves = knight.getLegalMoves(chessBoard);
        assertEquals(8, moves.size());
        for (String pos : enemyPositions) {
            assertTrue(moves.contains(chessBoard.getSquare(pos)));
        }
    }

    @Test
    void testEdgePositionMovement() throws IOException {
        Square edge = chessBoard.getSquare("h3");
        Knight knight = new Knight(PieceColor.WHITE, edge, "/wknight.png");
        edge.setOccupyingPiece(knight);

        List<Square> moves = knight.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("g1"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("g5"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("f2"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("f4"))),
                () -> assertEquals(4, moves.size())
        );
    }

    @Test
    void testMixedObstacles() throws IOException {
        Square center = chessBoard.getSquare("d4");
        Knight knight = new Knight(PieceColor.WHITE, center, "/wknight.png");
        center.setOccupyingPiece(knight);

        // Mix of friends and enemies
        String[] friends = {"c6", "e6", "f5"};
        String[] enemies = {"f3", "e2", "c2"};

        for (String pos : friends) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Rook(PieceColor.WHITE, chessBoard.getSquare(pos), "/wrook.png"));
        }
        for (String pos : enemies) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Queen(PieceColor.BLACK, chessBoard.getSquare(pos), "/bqueen.png"));
        }

        List<Square> moves = knight.getLegalMoves(chessBoard);
        assertEquals(3, moves.size());
        for (String pos : enemies) {
            assertTrue(moves.contains(chessBoard.getSquare(pos)));
        }
        for (String pos : friends) {
            assertFalse(moves.contains(chessBoard.getSquare(pos)));
        }
    }

    @Test
    void testMaximumRangeMovement() throws IOException {
        Square corner = chessBoard.getSquare("a8");
        Knight knight = new Knight(PieceColor.BLACK, corner, "/bknight.png");
        corner.setOccupyingPiece(knight);

        List<Square> moves = knight.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("b6"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("c7"))),
                () -> assertEquals(2, moves.size())
        );
    }

    @Test
    void testSingleAvailablePath() throws IOException {
        Square position = chessBoard.getSquare("f1");
        Knight knight = new Knight(PieceColor.WHITE, position, "/wknight.png");
        position.setOccupyingPiece(knight);

        // Block all but one L-move
        chessBoard.getSquare("g3").setOccupyingPiece(
                new Pawn(PieceColor.BLACK, chessBoard.getSquare("g3"), "/bpawn.png"));
        chessBoard.getSquare("h2").setOccupyingPiece(
                new Pawn(PieceColor.WHITE, chessBoard.getSquare("h2"), "/wpawn.png"));
        chessBoard.getSquare("d2").setOccupyingPiece(
                new Pawn(PieceColor.WHITE, chessBoard.getSquare("d2"), "/wpawn.png"));

        List<Square> moves = knight.getLegalMoves(chessBoard);
        assertEquals(1, moves.size());
        assertTrue(moves.contains(chessBoard.getSquare("g3")));
    }

    @Test
    void testNoMovementWhenSurrounded() throws IOException {
        Square center = chessBoard.getSquare("d4");
        Knight knight = new Knight(PieceColor.WHITE, center, "/wknight.png");
        center.setOccupyingPiece(knight);

        // Completely surround with pieces
        String[] surrounding = {"c6", "e6", "f5", "f3", "e2", "c2", "b3", "b5"};
        for (String pos : surrounding) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Knight(PieceColor.WHITE, chessBoard.getSquare(pos), "/wknight.png"));
        }

        assertTrue(knight.getLegalMoves(chessBoard).isEmpty());
    }

    @Test
    void testPartialBlockingScenario() throws IOException {
        Square position = chessBoard.getSquare("e4");
        Knight knight = new Knight(PieceColor.BLACK, position, "/bknight.png");
        position.setOccupyingPiece(knight);

        // Block some paths
        String[] blocked = {"d6", "f6", "g5", "g3"};
        String[] open = {"f2", "d2", "c3", "c5"};

        for (String pos : blocked) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Bishop(PieceColor.BLACK, chessBoard.getSquare(pos), "/bbishop.png"));
        }

        List<Square> moves = knight.getLegalMoves(chessBoard);
        assertEquals(4, moves.size());
        for (String pos : open) {
            assertTrue(moves.contains(chessBoard.getSquare(pos)));
        }
    }
}