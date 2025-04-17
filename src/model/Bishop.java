package model;

import model.PieceColor.PieceColor;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }
    
    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        return getDiagonalOccupations(board, x, y);
    }
}
