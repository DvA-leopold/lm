package view;

import com.sun.istack.internal.Nullable;
import reader.TiffReader;
import versioning.VersionInfo;
import view.components.ImageComponent;
import view.menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Frame extends JFrame {
    public Frame() {
        initMainFrameParts();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(0, 0);
        setSize(dimension.getSize());
    }

    public Frame(int width, int height) {
        initBackend();
        initMainFrameParts();
        this.setLocation(100, 100);
        setSize(width, height);
        setScrollbarListener();
    }

    public void repaintScrollbar() {
        scrollbar.setVisible(true);
        scrollbar.setMaximum(tiffReader.getNumPages());
        scrollbar.repaint();
    }

    public void setScrollbarListener() {
        scrollbar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                repaintImageComponent(scrollbar.getValue());
            }
        });
    }

    public void setTiffReaderImage(String tiffImageName) {
        tiffReader.setTiffImage(tiffImageName);
    }

    public void repaintImageComponent(@Nullable Integer imageNumber) {
        if (imageNumber == null) {
            imageComponent.setImage(tiffReader.getImage(0));
        } else {
            imageComponent.setImage(tiffReader.getImage(imageNumber));
        }
        imageComponent.repaint();
    }

    public void init() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "\\resources\\lm.png").getImage());
        this.setTitle("Lm " + VersionInfo.getVersion());
        this.setJMenuBar(menu.getMenu());
        this.add(scrollbar).setSize(this.getWidth() - 15, 20);
        this.add(imageComponent);
        this.setVisible(true);
    }

    private void initMainFrameParts() {
        imageComponent = new ImageComponent();
        menu = new MainMenu(this);
        scrollbar = new JScrollBar(Adjustable.HORIZONTAL, 0, 1, 0, tiffReader.getNumPages());
        scrollbar.setVisible(false);
    }

    private void initBackend() {
        tiffReader = new TiffReader();
    }

    private MainMenu menu;
    private JScrollBar scrollbar;
    private ImageComponent imageComponent;

    public TiffReader tiffReader;
}
