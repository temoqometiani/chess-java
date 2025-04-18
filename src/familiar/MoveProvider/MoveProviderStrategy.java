package familiar.MoveProvider;

import model.Board;
import model.Square;

public interface MoveProviderStrategy {
    boolean executeMove(Board board, Square destination);
}