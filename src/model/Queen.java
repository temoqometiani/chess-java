package model;

import familiar.MoveProvider.MoveProvider;
import familiar.MoveProvider.MoveProviderStrategy;
import familiar.StrategyMove;
import familiar.move.QueenMove;
import model.PieceColor.PieceColor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {

    public Queen(PieceColor color, Square initSq, String img_file) throws IOException {

        super(color, initSq, img_file);
    }


    @Override
    protected StrategyMove getMovementStrategy() {
        return new QueenMove(this);
    }

    @Override
    protected MoveProviderStrategy getMoveExecutorStrategy() {
        return new MoveProvider(this);
    }



}