package model;

import model.PieceColor.PieceColor;

public class Movement {

    private Piece movedPiece;
    private Square from;
    private Square to;
    private Piece capturePiece;

    public Movement(Piece movedPiece, Square from, Square to) {
        this.movedPiece = movedPiece;
        this.from = from;
        this.to = to;
    }

    public Piece getCapturePiece() {
        return capturePiece;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Square getTo() {
        return to;
    }

    public Square getFrom() {
        return from;
    }

    public void setTo(Square to) {
        this.to = to;
    }

    public void setCapturePiece(Piece capturePiece) {
        this.capturePiece = capturePiece;
    }

    public void setFrom(Square from) {
        this.from = from;
    }

    public void setMovedPiece(Piece movedPiece) {
        this.movedPiece = movedPiece;
    }

    public void undo(Board board) {
        from.setOccupyingPiece(movedPiece);
        to.setOccupyingPiece(capturePiece);
        movedPiece.setPosition(from);
        if (capturePiece != null) {
            capturePiece.setPosition(to);
            if (capturePiece.getColor() == PieceColor.WHITE) {
                board.getWhitePieces().add(capturePiece);
            } else {
                board.getBlackPieces().add(capturePiece);
            }
        }
    }

    @Override
    public String toString() {
        return "Move{" +
                "movedPiece=" + movedPiece.getClass().toString() +
                ", from=" + from.getPosition() +
                ", to=" + to.getPosition() +
                '}';
    }
}
