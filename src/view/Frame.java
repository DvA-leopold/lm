package view;

import versioning.VersionInfo;
import view.components.ImageComponent;
import view.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        initMainFrameParts();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(0, 0);
        setSize(dimension.getSize());
    }

    public Frame(int width, int height) {
        initMainFrameParts();
        this.setLocation(100, 100);
        setSize(width, height);
    }

    private void initMainFrameParts() {
        imageComponent = new ImageComponent();
        menu = new MainMenu(imageComponent);
    }

    public void init() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "\\resources\\icon.jpg").getImage());
        this.setTitle("Lm " + VersionInfo.getVersion());
        this.setJMenuBar(menu.getMenu());
        this.add(imageComponent);

        this.setVisible(true);
    }

    private MainMenu menu;
    private ImageComponent imageComponent;
}
