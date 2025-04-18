package view;

import controller.Controllergame;
import model.Board;
import model.Piece;
import model.PieceColor.PieceColor;
import model.Square;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class BoardProvider extends JPanel {
    private final Board chessBoard;
    private final Controllergame gameController;
    private int currX;
    private int currY;

    public BoardProvider(Board board, GameWindow gameWindow) {
        chessBoard = board;
        gameController = new Controllergame(board, gameWindow);
        initializeBoard();
        this.setVisible(true);
    }

    private void initializeBoard() {
        setLayout(new GridLayout(8, 8));
        setPreferredSize(new Dimension(400, 400));
        setupMouseHandling();
        createBoardSquares();
    }

    private void setupMouseHandling() {
        MouseAdapter mouseAdapter = new mouseprovider(gameController, this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private void createBoardSquares() {
        Square[][] boardSquares = chessBoard.getSquareArray();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                add(new Squareprovider(boardSquares[i][j]));
            }
        }
    }

    public void setCurrX(int x) {
        currX = x; }

    public void setCurrY(int y) {
        currY = y; }

    public int getCurrX() {
        return currX; }

    public int getCurrY() {
        return currY; }

    public Board getChessBoard() {
        return chessBoard; }

    public Controllergame getGameController() {
        return gameController; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateSquareVisibility();
        provideDraggedPiece(g);
    }

    private void updateSquareVisibility() {
        Square[][] boardSquares = chessBoard.getSquareArray();
        for (Square[] row : boardSquares) {
            for (Square square : row) {
                square.setDisplay(true);
            }
        }
    }

    private void provideDraggedPiece(Graphics g) {
        Piece activePiece = chessBoard.getCurrPiece();
        if (shouldProvidePiece(activePiece)) {
            Image pieceImage = activePiece.getImage();
            if (pieceImage != null) {
                g.drawImage(pieceImage, currX, currY, null);
            }
        }
    }

    private boolean shouldProvidePiece(Piece piece) {
        return piece != null &&
                ((piece.getColor() == PieceColor.WHITE && chessBoard.isWhiteTurn()) ||
                        (piece.getColor() == PieceColor.BLACK && !chessBoard.isWhiteTurn()));
    }
}