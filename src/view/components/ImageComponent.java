package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ImageComponent extends JComponent {
    public ImageComponent() {
        metadataCopy = new ArrayList<>(5);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, null);
            g.setFont(new Font("Serif", Font.PLAIN, 15));
            g.setColor(Color.white);
            int padding = 30;
            for (String data : metadataCopy) {
                g.drawString(data, 40, padding += 20);
            }
            //g.drawString("name: frog", 40, 50);
            //g.drawString("nanometers: 54", 40, 70);
            //g.drawString("type: multi", 40, 90);
            //g.drawString("data: 17.09.93", 40, 110);
            //g.drawString("time: 20.00", 40, 130);
            //g.drawString("color: gray", 40, 150);
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void reInitMetadata(String ... names) {
        metadataCopy.clear();
        Collections.addAll(metadataCopy, names);
    }


    private ArrayList<String> metadataCopy;
    private BufferedImage image = null;
}
