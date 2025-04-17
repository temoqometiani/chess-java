package familiar;


import model.Board;
import model.Piece;
import model.Square;

import java.util.List;

public interface StrategyMove {
    List<Square> getLegalMoves(Board board);
}