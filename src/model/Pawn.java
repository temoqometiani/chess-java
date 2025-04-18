package model;

import familiar.MoveProvider.MoveProvider;
import familiar.MoveProvider.MoveProviderStrategy;
import familiar.StrategyMove;
import familiar.move.PawnMove;
import model.PieceColor.PieceColor;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
public class Pawn extends Piece {


    public Pawn(PieceColor color, Square initSq, String img_file) throws IOException {
        super(color, initSq, img_file);
    }

    @Override
    public boolean move(Square fin,Board board) {
        setMovementStrategy(new PawnMove(this,true));
        return super.move(fin,board);
    }

    @Override
    protected StrategyMove getMovementStrategy() {
        return new PawnMove(this,false);
    }

    @Override
    protected MoveProviderStrategy getMoveExecutorStrategy() {
        return new MoveProvider(this);
    }

}