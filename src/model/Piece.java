package model;


import familiar.MoveProvider.MoveProviderStrategy;
import model.PieceColor.PieceColor;
import familiar.StrategyMove;
import view.PieceImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


public abstract class Piece {
    public PieceColor getColor() {
        return color;
    }

    public Square getPosition() {
        return position;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setMoveExecutorStrategy(MoveProviderStrategy moveExecutorStrategy) {
        this.moveExecutorStrategy = moveExecutorStrategy;
    }


    public void setMovementStrategy(StrategyMove movementStrategy) {

        this.movementStrategy = movementStrategy;
    }

    public void setPosition(Square position) {

        this.position = position;
    }

    private final PieceColor color;

    private Square position;

    private BufferedImage img;
    private StrategyMove movementStrategy;

    private MoveProviderStrategy moveExecutorStrategy;

    public Piece(PieceColor color, Square initSq, String img_file) throws IOException {
        this.color = color;
        this.position = initSq;


        this.img = PieceImage.loadImage(img_file);
        this.movementStrategy = getMovementStrategy();
        this.moveExecutorStrategy = getMoveExecutorStrategy();
    }


    public boolean move(Square destination, Board board) {
        return moveExecutorStrategy.executeMove(board, destination );
    }

    protected abstract StrategyMove getMovementStrategy();

    protected abstract MoveProviderStrategy getMoveExecutorStrategy();


    public Image getImage() {

        return img;
    }

    public List<Square> getLegalMoves(Board b) {

        return movementStrategy.getLegalMoves(b);
    }


}