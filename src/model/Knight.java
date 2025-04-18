package model;

import familiar.MoveProvider.MoveProvider;
import familiar.MoveProvider.MoveProviderStrategy;
import familiar.StrategyMove;
import familiar.move.KnightMove;
import model.PieceColor.PieceColor;

import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    public Knight(PieceColor color, Square initSq, String img_file) {

        super(color, initSq, img_file);
    }

    @Override
    protected StrategyMove getMovementStrategy() {
        return new KnightMove(this);
    }

    @Override
    protected MoveProviderStrategy getMoveExecutorStrategy() {
        return new MoveProvider(this);
    }
}
