package view;

import view.menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.logging.FileHandler;

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
        System.out.println(System.getProperty("user.dir") + "light.jpg");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "\\resources\\light.jpg").getImage());
        this.setJMenuBar(new MainMenu().getMenu());
        //this.add(new ImageComponent());

        this.setVisible(true);
    }
}
