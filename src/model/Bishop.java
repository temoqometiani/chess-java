package model;

import familiar.MoveProvider.MoveProvider;
import familiar.MoveProvider.MoveProviderStrategy;
import familiar.StrategyMove;
import familiar.move.BishopMove;
import model.PieceColor.PieceColor;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }



    @Override
    protected StrategyMove getMovementStrategy() {
        return new BishopMove(this);
    }

    @Override
    protected MoveProviderStrategy getMoveExecutorStrategy() {
        return new MoveProvider(this);
    }


}
