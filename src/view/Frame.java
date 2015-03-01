package view;

import sun.applet.Main;
import versioning.VersionInfo;
import view.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(0, 0);
        setSize(dimension.getSize());
    }

    public Frame(int width, int height) {
        this.setLocation(100, 100);
        setSize(width, height);
    }

    public void init() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "\\resources\\icon.jpg").getImage());
        this.setTitle("Lm " + VersionInfo.getVersion());
        this.setJMenuBar(new MainMenu().getMenu());
        //this.add(new ImageComponent());

        this.setVisible(true);
    }
}
