package model;

import familiar.MoveProvider.MoveProvider;
import familiar.MoveProvider.MoveProviderStrategy;
import familiar.StrategyMove;
import familiar.move.KingMove;
import model.PieceColor.PieceColor;

import java.util.LinkedList;
import java.util.List;

public class King extends Piece {

    public King(PieceColor color, Square initSq, String img_file) {

        super(color, initSq, img_file);
    }

    @Override
    protected StrategyMove getMovementStrategy() {
        return new KingMove(this);
    }

    @Override
    protected MoveProviderStrategy getMoveExecutorStrategy() {
        return new MoveProvider(this);
    }


}