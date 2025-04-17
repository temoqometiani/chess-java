package model;

import model.PieceColor.PieceColor;
import model.King;

import java.util.LinkedList;

public class Board {
    private final Square[][] board;
    final LinkedList<Piece> Bpieces = new LinkedList<>();
    final LinkedList<Piece> Wpieces = new LinkedList<>();
    private final CheckmateDetector cmd;


    private boolean whiteTurn = true;

    private Piece currPiece;


    private static final String RESOURCES_WBISHOP_PNG = "/wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = "/bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = "/wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = "/bknight.png";
    private static final String RESOURCES_WROOK_PNG = "/wrook.png";
    private static final String RESOURCES_BROOK_PNG = "/brook.png";
    private static final String RESOURCES_WKING_PNG = "/wking.png";
    private static final String RESOURCES_BKING_PNG = "/bking.png";
    private static final String RESOURCES_BQUEEN_PNG = "/bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = "/wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = "/wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = "/bpawn.png";

    public Board() {
        board = new Square[8][8];
        initializeBoardSquares();
        initializePieces();
        cmd = new CheckmateDetector(this, Wpieces, Bpieces, getWhiteKing(), getBlackKing());
    }

    private void initializeBoardSquares() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                PieceColor color = ((x + y) % 2 == 0) ? PieceColor.WHITE : PieceColor.BLACK;
                board[y][x] = new Square(board,color.getValue(), x, y);
            }
        }
    }

    private void initializePieces() {
        // Add pawns
        for (int x = 0; x < 8; x++) {
            board[1][x].put(new Pawn(PieceColor.BLACK, board[1][x], RESOURCES_BPAWN_PNG));
            board[6][x].put(new Pawn(PieceColor.WHITE, board[6][x], RESOURCES_WPAWN_PNG));
        }

        // Add kings and queens
        board[0][4].put(new King(PieceColor.BLACK, board[0][4], RESOURCES_BKING_PNG));
        board[7][4].put(new King(PieceColor.WHITE, board[7][4], RESOURCES_WKING_PNG));
        board[0][3].put(new Queen(PieceColor.BLACK, board[0][3], RESOURCES_BQUEEN_PNG));
        board[7][3].put(new Queen(PieceColor.WHITE, board[7][3], RESOURCES_WQUEEN_PNG));

        // Add other pieces (rooks, knights, bishops)
        board[0][0].put(new Rook(PieceColor.BLACK, board[0][0], RESOURCES_BROOK_PNG));
        board[0][7].put(new Rook(PieceColor.BLACK, board[0][7], RESOURCES_BROOK_PNG));
        board[7][0].put(new Rook(PieceColor.WHITE, board[7][0], RESOURCES_WROOK_PNG));
        board[7][7].put(new Rook(PieceColor.WHITE, board[7][7], RESOURCES_WROOK_PNG));

        board[0][1].put(new Knight(PieceColor.BLACK, board[0][1], RESOURCES_BKNIGHT_PNG));
        board[0][6].put(new Knight(PieceColor.BLACK, board[0][6], RESOURCES_BKNIGHT_PNG));
        board[7][1].put(new Knight(PieceColor.WHITE, board[7][1], RESOURCES_WKNIGHT_PNG));
        board[7][6].put(new Knight(PieceColor.WHITE, board[7][6], RESOURCES_WKNIGHT_PNG));

        board[0][2].put(new Bishop(PieceColor.BLACK, board[0][2], RESOURCES_BBISHOP_PNG));
        board[0][5].put(new Bishop(PieceColor.BLACK, board[0][5], RESOURCES_BBISHOP_PNG));
        board[7][2].put(new Bishop(PieceColor.WHITE, board[7][2], RESOURCES_WBISHOP_PNG));
        board[7][5].put(new Bishop(PieceColor.WHITE, board[7][5], RESOURCES_WBISHOP_PNG));

        // Add to Bpieces and Wpieces
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                Bpieces.add(board[y][x].getOccupyingPiece());
                Wpieces.add(board[7 - y][x].getOccupyingPiece());
            }
        }
    }

    public Square[][] getSquareArray() {
        return board;
    }

    public void toggleTurn() {
        whiteTurn = !whiteTurn;
    }

    public CheckmateDetector getCheckmateDetector() {
        return cmd;
    }
    public boolean getTurn() {
        return whiteTurn;
    }

    public void capturePiece(Square square, Piece capturingPiece) {
        Piece capturedPiece = square.getOccupyingPiece();
        if (capturedPiece != null) {
            if (capturedPiece.getColor() == PieceColor.BLACK) Bpieces.remove(capturedPiece);
            else Wpieces.remove(capturedPiece);
        }
        square.setOccupyingPiece(capturingPiece);
    }

    public boolean isPathClear(Square from, Square to) {
        int dx = Integer.compare(to.getPosition().getX(), from.getPosition().getX());
        int dy = Integer.compare(to.getPosition().getY(), from.getPosition().getY());

        int x = from.getPosition().getX() + dx;
        int y = from.getPosition().getY() + dy;

        while (x != to.getPosition().getX() || y != to.getPosition().getY()) {
            if (board[x][y] != null) {
                return false;
            }
            x += dx;
            y += dy;
        }
        return true;
    }

    private King getWhiteKing() {
        return (King) board[7][4].getOccupyingPiece();
    }

    private King getBlackKing() {
        return (King) board[0][4].getOccupyingPiece();
    }

}