package model;

import model.PieceColor.PieceColor;

import javax.swing.text.Position;


public class Square {
    public int getX() {
        return position.x;
    }
    public int getY() {
        return position.y;
    }

    static class Position{
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public Position getPosition() {
        return position;
    }

    private final Position position;

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public PieceColor getColor() {
        return color;
    }

    private final PieceColor color;

    public void setOccupyingPiece(Piece occupyingPiece) {
        this.occupyingPiece = occupyingPiece;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    private Piece occupyingPiece;

    private boolean display = true; // should be refactored moved out to

    public Square(PieceColor color, int x, int y) {
        this.color = color;
        this.position = new Position(x, y);
    }

    public Square(Square other){
        this.color = other.color;
        this.position = other.position;
        this.display = other.display;
    }

    public boolean isOccupied() {
        return occupyingPiece != null;
    }

    public void put(Piece piece) {
        this.occupyingPiece = piece;
        if (piece != null) {
            piece.setPosition(this);
        }
    }

  /*
  getXNUM() {
   */
    /*
       return x;
    }
  */

    public Piece removePiece() {
        Piece piece = this.occupyingPiece;
        this.occupyingPiece = null;
        return piece;
    }

    public boolean getDisplay(){
        return display;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Square other = (Square) obj;
        return position.equals(other.position);
    }
}
