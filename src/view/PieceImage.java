package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PieceImage {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(PieceImage.class.getResource(path));
        } catch (IOException e) {
            System.out.println("can not able to load piece image: " + e.getMessage());
            return null;
        }
    }
}
