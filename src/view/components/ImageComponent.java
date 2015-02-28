package view.components;

import reader.TiffReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageComponent extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(TiffReader.getImage().getAsBufferedImage(), 0, 0, null);
    }
}
