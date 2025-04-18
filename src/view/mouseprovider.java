package view;

import controller.Controllergame;
import model.Square;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mouseprovider extends MouseAdapter {

    private final Controllergame controller;
    private final BoardProvider rendering;

    public mouseprovider(Controllergame controller, BoardProvider rendering) {
        this.controller = controller;
        this.rendering = rendering;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        rendering.setCurrX(e.getX());
        rendering.setCurrY(e.getY());

        Squareprovider squareRendering = (Squareprovider) rendering.getComponentAt(new Point(e.getX(), e.getY()));
        Square square = squareRendering.getSquare();

        controller.handleMousePressed(square);

        rendering.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Squareprovider squareRendering = (Squareprovider) rendering.getComponentAt(new Point(e.getX(), e.getY()));
        Square targetSquare = squareRendering.getSquare();

        controller.handleMouseReleased(targetSquare);

        rendering.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        rendering.setCurrX(e.getX() - 24);
        rendering.setCurrY(e.getY() - 24);
        rendering.repaint();
    }


}
