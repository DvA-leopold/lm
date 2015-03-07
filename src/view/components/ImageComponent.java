package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageComponent extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    private BufferedImage image = null;
}
