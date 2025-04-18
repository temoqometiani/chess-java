package view;

import model.PieceColor.PieceColor;
import model.Square;

import javax.swing.*;
import java.awt.*;

public class    Squareprovider extends JComponent {
    private Square square;

    public Square getSquare() {
        return square;
    }

    public Squareprovider(Square square) {
        this.square = square;
        this.setBorder(BorderFactory.createEmptyBorder());
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (square.getColor() == PieceColor.WHITE) {
            g.setColor(new Color(221, 192, 127));  // White square color
        } else {
            g.setColor(new Color(101, 67, 33));  // Black square color
        }

        g.fillRect(0, 0, getWidth(), getHeight());  // Use the actual width and height of the component

        if (square.getOccupyingPiece() != null && square.getDisplay()) {
            PieceProvider pieceRendering = new PieceProvider(square.getOccupyingPiece());
            pieceRendering.draw(g, getWidth(), getHeight());
        }
    }
}
