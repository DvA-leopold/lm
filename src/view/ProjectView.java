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
    public ProjectView(int width, int height, final Controller controller) {
        this.controller = controller;
        imageComponent = new ImageComponent();
        topBarMenu = new TopBarMenu(controller);
        scrollbar = new JScrollBar(Adjustable.HORIZONTAL, 0, 1, 0, 1);
        scrollbar.setVisible(false);
        fileChooser = new FileChooser(this.getComponent(0));
        fileChooser.setVisible(false);
        progressMonitor = new ProgressMonitor(
                this.getComponent(0),
                "Progress...",
                "this string updated every time", 0, 1);
        setLocation(100, 100);
        setSize(width, height);
    }

    public void init() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/resources/lm.png").getImage());
        this.setTitle("Lm " + VersionInfo.VERSION);
        this.setJMenuBar(topBarMenu.getMenu());
        this.add(scrollbar).setSize(this.getWidth() - 15, 20);
        this.add(imageComponent);
        this.setVisible(true);
    }

    public void repaintScrollbar(int maximum) {
        scrollbar.setVisible(true);
        scrollbar.setMaximum(maximum);
        scrollbar.repaint();
    }

    public void setScrollbarListener() {
        scrollbar.addAdjustmentListener(e -> {
            try {
                controller.runCommand(controller.getCommand(ScrollCommand.class));
            } catch (IllegalAccessException | InstantiationException e1) {
                e1.printStackTrace();
            }
        });
    }

    public ProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }

    public void repaintImageComponent(final BufferedImage image) {
        imageComponent.setImage(image);
        imageComponent.repaint();
    }

    public int getScrollValue() {
        return scrollbar.getValue();
    }

    public String getFileChooserPath(String chooserDialog, int selectionMode) {
        fileChooser.setVisible(true);
        return fileChooser.getChosenFilePath(chooserDialog, selectionMode);
    }

    private TopBarMenu topBarMenu;
    private JScrollBar scrollbar;
    private ImageComponent imageComponent;

    private FileChooser fileChooser;
    private ProgressMonitor progressMonitor;

    private final Controller controller;
}
