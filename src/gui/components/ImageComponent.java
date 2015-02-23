package gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageComponent extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("E:\\IdeaProjects\\Lm\\src\\resources\\light.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Image image = new ImageIcon("E:\\IdeaProjects\\Lumen\\src\\resources\\sagra.tif").getImage();
        g.drawImage(image, 0, 0, null);
    }
}
