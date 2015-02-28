package view.components;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class InfoComponent extends JComponent {
    @Override
    public void paintComponent(Graphics g) {
        g.setFont(new Font("Serif", Font.PLAIN, fontSize));
        initStrings();
        for(String string : infoStrings){
            fontSize+=fontSize;
            g.drawString(string, 40, 20 + fontSize);
        }
    }

    public void initStrings() {
        infoStrings = new Vector<>(10);
        infoStrings.add("first One");
        infoStrings.add("second One");
    }

    private Vector<String> infoStrings;
    private int fontSize = 20;
}
