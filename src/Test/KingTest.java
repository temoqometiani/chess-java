package Test;

import model.*;
import model.PieceColor.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GameWindow;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class KingTest {

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
    void verifyKingMovementFromCenter() throws IOException {
        Square center = chessBoard.getSquare("d4");
        King king = new King(PieceColor.WHITE, center, "/wking.png");
        center.setOccupyingPiece(king);

        List<Square> possibleMoves = king.getLegalMoves(chessBoard);

        String[] validPositions = {
                "c3", "c4", "c5",
                "d3",       "d5",
                "e3", "e4", "e5"
        };

        for (String position : validPositions) {
            assertTrue(possibleMoves.contains(chessBoard.getSquare(position)));
        }
        assertEquals(8, possibleMoves.size());
    }

    @Test
    void verifyCornerKingMovement() throws IOException {
        Square corner = chessBoard.getSquare("a1");
        King king = new King(PieceColor.WHITE, corner, "/wking.png");
        corner.setOccupyingPiece(king);

        List<Square> moves = king.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("a2"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("b1"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("b2"))),
                () -> assertEquals(3, moves.size())
        );
    }

    @Test
    void testBlockedKingMovement() throws IOException {
        Square center = chessBoard.getSquare("d4");
        King king = new King(PieceColor.WHITE, center, "/wking.png");
        center.setOccupyingPiece(king);

        // Surround king with friendly pieces
        String[] surrounding = {"c3", "c4", "c5", "d3", "d5", "e3", "e4", "e5"};
        for (String pos : surrounding) {
            Square square = chessBoard.getSquare(pos);
            square.setOccupyingPiece(new Rook(PieceColor.WHITE, square, "/wrook.png"));
        }

        assertTrue(king.getLegalMoves(chessBoard).isEmpty());
    }

    @Test
    void testKingCaptureMoves() throws IOException {
        Square center = chessBoard.getSquare("d4");
        King king = new King(PieceColor.WHITE, center, "/wking.png");
        center.setOccupyingPiece(king);

        // Surround king with enemy pieces
        String[] enemies = {"c3", "c4", "c5", "d3", "d5", "e3", "e4", "e5"};
        for (String pos : enemies) {
            Square square = chessBoard.getSquare(pos);
            square.setOccupyingPiece(new Pawn(PieceColor.BLACK, square, "/bpawn.png"));
        }

        assertEquals(8, king.getLegalMoves(chessBoard).size());
    }

    @Test
    void testEdgeKingMovement() throws IOException {
        Square edge = chessBoard.getSquare("h5");
        King king = new King(PieceColor.WHITE, edge, "/wking.png");
        edge.setOccupyingPiece(king);

        List<Square> moves = king.getLegalMoves(chessBoard);

        assertAll(
                () -> assertTrue(moves.contains(chessBoard.getSquare("g4"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("g5"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("g6"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("h4"))),
                () -> assertTrue(moves.contains(chessBoard.getSquare("h6"))),
                () -> assertEquals(5, moves.size())
        );
    }

    @Test
    void testMixedSurroundingKing() throws IOException {
        Square center = chessBoard.getSquare("d4");
        King king = new King(PieceColor.WHITE, center, "/wking.png");
        center.setOccupyingPiece(king);

        // Set up mixed environment
        String[] friends = {"c3", "d3", "e3"};
        String[] foes = {"c4", "e4", "c5", "d5", "e5"};

        for (String pos : friends) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Bishop(PieceColor.WHITE, chessBoard.getSquare(pos), "/wbishop.png"));
        }
        for (String pos : foes) {
            chessBoard.getSquare(pos).setOccupyingPiece(
                    new Knight(PieceColor.BLACK, chessBoard.getSquare(pos), "/bknight.png"));
        }

        List<Square> moves = king.getLegalMoves(chessBoard);
        assertEquals(5, moves.size());
        for (String pos : foes) {
            assertTrue(moves.contains(chessBoard.getSquare(pos)));
        }
    }





    @Test
    void testBlockedCastlingPath() throws IOException {
        Square kingPos = chessBoard.getSquare("e1");
        Square rookPos = chessBoard.getSquare("h1");
        Square blockPos = chessBoard.getSquare("f1");

        King king = new King(PieceColor.WHITE, kingPos, "/wking.png");
        Rook rook = new Rook(PieceColor.WHITE, rookPos, "/wrook.png");
        Bishop blocker = new Bishop(PieceColor.WHITE, blockPos, "/wbishop.png");

        kingPos.setOccupyingPiece(king);
        rookPos.setOccupyingPiece(rook);
        blockPos.setOccupyingPiece(blocker);

        chessBoard.getWhitePieces().addAll(List.of(king, rook, blocker));

        assertFalse(king.getLegalMoves(chessBoard).contains(chessBoard.getSquare("g1")));
    }

    @Test
    void testCastlingWhileInCheck() throws IOException {
        Square kingPos = chessBoard.getSquare("e1");
        Square rookPos = chessBoard.getSquare("h1");
        Square enemyPos = chessBoard.getSquare("e8");

        King king = new King(PieceColor.WHITE, kingPos, "/wking.png");
        Rook rook = new Rook(PieceColor.WHITE, rookPos, "/wrook.png");
        Queen enemy = new Queen(PieceColor.BLACK, enemyPos, "/bqueen.png");

        kingPos.setOccupyingPiece(king);
        rookPos.setOccupyingPiece(rook);
        enemyPos.setOccupyingPiece(enemy);

        chessBoard.getWhitePieces().addAll(List.of(king, rook));
        chessBoard.getBlackPieces().add(enemy);

        assertFalse(king.getLegalMoves(chessBoard).contains(chessBoard.getSquare("g1")));
    }
}