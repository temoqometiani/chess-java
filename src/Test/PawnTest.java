package Test;

import model.*;
import model.PieceColor.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GameWindow;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PawnTest  {

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
    void verifyWhitePawnInitialMovement() throws IOException {
        Square start = chessBoard.getSquare("e2");
        Pawn whitePawn = new Pawn(PieceColor.WHITE, start, "/wpawn.png");
        start.setOccupyingPiece(whitePawn);

        List<Square> possibleMoves = whitePawn.getLegalMoves(chessBoard);

        assertTrue(possibleMoves.contains(chessBoard.getSquare("e3")));
        assertTrue(possibleMoves.contains(chessBoard.getSquare("e4")));
    }

    @Test
    void checkBlockedWhitePawn() throws IOException {
        Square start = chessBoard.getSquare("e2");
        Pawn pawn = new Pawn(PieceColor.WHITE, start, "/wpawn.png");
        start.setOccupyingPiece(pawn);

        Square obstacle = chessBoard.getSquare("e3");
        new Pawn(PieceColor.WHITE, obstacle, "/wpawn.png").move(obstacle, chessBoard);

        List<Square> moves = pawn.getLegalMoves(chessBoard);
        assertFalse(moves.contains(obstacle));
    }

    @Test
    void testDiagonalCapture() throws IOException {
        Square pawnPosition = chessBoard.getSquare("d4");
        Pawn pawn = new Pawn(PieceColor.WHITE, pawnPosition, "/wpawn.png");
        pawnPosition.setOccupyingPiece(pawn);

        Square rightEnemy = chessBoard.getSquare("e5");
        Square leftEnemy = chessBoard.getSquare("c5");
        new Pawn(PieceColor.BLACK, rightEnemy, "/bpawn.png").move(rightEnemy, chessBoard);
        new Pawn(PieceColor.BLACK, leftEnemy, "/bpawn.png").move(leftEnemy, chessBoard);

        List<Square> moves = pawn.getLegalMoves(chessBoard);
        assertTrue(moves.contains(rightEnemy));
        assertTrue(moves.contains(leftEnemy));
    }

    @Test
    void verifyNoBackwardMovement() throws IOException {
        Square position = chessBoard.getSquare("d4");
        Pawn pawn = new Pawn(PieceColor.WHITE, position, "/wpawn.png");
        position.setOccupyingPiece(pawn);

        assertFalse(pawn.getLegalMoves(chessBoard).contains(chessBoard.getSquare("d3")));
    }

    @Test
    void testBlackPawnInitialOptions() throws IOException {
        Square start = chessBoard.getSquare("d7");
        Pawn blackPawn = new Pawn(PieceColor.BLACK, start, "/bpawn.png");
        start.setOccupyingPiece(blackPawn);

        List<Square> options = blackPawn.getLegalMoves(chessBoard);
        assertTrue(options.contains(chessBoard.getSquare("d6")));
        assertTrue(options.contains(chessBoard.getSquare("d5")));
    }

    @Test
    void verifyBlockedBlackPawn() throws IOException {
        Square start = chessBoard.getSquare("d7");
        Pawn pawn = new Pawn(PieceColor.BLACK, start, "/bpawn.png");
        start.setOccupyingPiece(pawn);

        Square block = chessBoard.getSquare("d6");
        new Pawn(PieceColor.WHITE, block, "/wpawn.png").move(block, chessBoard);

        assertFalse(pawn.getLegalMoves(chessBoard).contains(block));
    }

    @Test
    void testNoDiagonalMovementWithoutTarget() throws IOException {
        Square position = chessBoard.getSquare("d4");
        Pawn pawn = new Pawn(PieceColor.WHITE, position, "/wpawn.png");
        position.setOccupyingPiece(pawn);

        List<Square> moves = pawn.getLegalMoves(chessBoard);
        assertFalse(moves.contains(chessBoard.getSquare("e5")));
        assertFalse(moves.contains(chessBoard.getSquare("c5")));
    }

    @Test
    void verifyBlackPawnSingleMoveAfterInitial() throws IOException {
        Square start = chessBoard.getSquare("e7");
        Pawn pawn = new Pawn(PieceColor.BLACK, start, "/bpawn.png");
        start.setOccupyingPiece(pawn);
        pawn.move(chessBoard.getSquare("e6"), chessBoard);

        List<Square> moves = pawn.getLegalMoves(chessBoard);
        assertTrue(moves.contains(chessBoard.getSquare("e5")));
        assertFalse(moves.contains(chessBoard.getSquare("e4")));
    }

    @Test
    void testNoFriendlyFire() throws IOException {
        Square position = chessBoard.getSquare("d4");
        Pawn pawn = new Pawn(PieceColor.WHITE, position, "/wpawn.png");
        position.setOccupyingPiece(pawn);

        Square rightFriend = chessBoard.getSquare("e5");
        Square leftFriend = chessBoard.getSquare("c5");
        new Pawn(PieceColor.WHITE, rightFriend, "/wpawn.png").move(rightFriend, chessBoard);
        new Pawn(PieceColor.WHITE, leftFriend, "/wpawn.png").move(leftFriend, chessBoard);

        List<Square> moves = pawn.getLegalMoves(chessBoard);
        assertFalse(moves.contains(rightFriend));
        assertFalse(moves.contains(leftFriend));
    }

    @Test
    void verifyEdgeOfBoardMovement() throws IOException {
        Square edge = chessBoard.getSquare("a8");
        Pawn pawn = new Pawn(PieceColor.WHITE, edge, "/wpawn.png");
        edge.setOccupyingPiece(pawn);

        assertTrue(pawn.getLegalMoves(chessBoard).isEmpty());
    }
}