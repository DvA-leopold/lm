package view;

import controller.Controller;
import controller.commands.ScrollCommand;
import model.versioning.VersionInfo;
import view.components.ImageComponent;
import view.menu.FileChooser;
import view.menu.TopBarMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProjectView extends JFrame {
/*
    public ProjectView() {
        initFrameParts();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(0, 0);
        setSize(dimension.getSize());
    }
*/

    public ProjectView(int width, int height, final Controller controller) {
        this.controller = controller;
        fileChooser = new FileChooser(this.getComponent(0));
        fileChooser.setVisible(false);
        initFrameParts();
        this.setLocation(100, 100);
        setSize(width, height);
    }
    /*
        public void getTiffMetadata(final String imageName) {
            metaDataReader.readAndDisplayMetadata(imageName);
        }
    */
    public void init() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "\\resources\\lm.png").getImage());
        this.setTitle("Lm " + VersionInfo.getVersion());
        this.setJMenuBar(topBarMenu.getMenu());
        this.add(scrollbar).setSize(this.getWidth() - 15, 20);
        this.add(imageComponent);
        this.setVisible(true);
    }

    private void initFrameParts() {
        imageComponent = new ImageComponent();
        topBarMenu = new TopBarMenu(controller);
        scrollbar = new JScrollBar(Adjustable.HORIZONTAL, 0, 1, 0, 1);
        scrollbar.setVisible(false);
    }

    public void repaintScrollbar(int maximum) {
        scrollbar.setVisible(true);
        scrollbar.setMaximum(maximum);
        scrollbar.repaint();
    }

    public void setScrollbarListener() {
        //TODO: garbage collector troubles. make command hash
        scrollbar.addAdjustmentListener(e -> controller.runCommand(new ScrollCommand()));
    }

    public void repaintImageComponent(final BufferedImage image) {
        imageComponent.setImage(image);
        imageComponent.repaint();
    }

    public int getScrollValue() {
        return scrollbar.getValue();
    }

    public String openWithFileChooser() {
        fileChooser.setVisible(true);
        return fileChooser.getChosenFilePath();
    }

    private TopBarMenu topBarMenu;
    private FileChooser fileChooser;
    private JScrollBar scrollbar;
    private ImageComponent imageComponent;

    private final Controller controller;
}
