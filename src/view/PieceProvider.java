package view;

import model.Piece;

import java.awt.*;

public class PieceProvider {
    private final Image img;
    private final Piece piece;

    public PieceProvider(Piece piece) {
        this.piece = piece;
        this.img = piece.getImage();
    }

    public void draw(Graphics g, int width, int height) {
        g.drawImage(img, 0, 0, width, height, null);
    }
}
