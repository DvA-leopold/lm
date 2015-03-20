package view;

import com.sun.istack.internal.Nullable;
import readers.MetaDataReader;
import readers.TiffReader;
import versioning.VersionInfo;
import view.components.ImageComponent;
import view.menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends JFrame {
    public Frame() {
        //backend
        tiffReader = new TiffReader();
        metaDataReader = new MetaDataReader();
        //min frame parts
        imageComponent = new ImageComponent();
        menu = new MainMenu(this);
        scrollbar = new JScrollBar(Adjustable.HORIZONTAL, 0, 1, 0, tiffReader.getNumPages());
        scrollbar.setVisible(false);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(0, 0);
        setSize(dimension.getSize());
    }

    public Frame(int width, int height) {
        //backend
        tiffReader = new TiffReader();
        metaDataReader = new MetaDataReader();
        //min frame parts
        imageComponent = new ImageComponent();
        menu = new MainMenu(this);
        scrollbar = new JScrollBar(Adjustable.HORIZONTAL, 0, 1, 0, tiffReader.getNumPages());
        scrollbar.setVisible(false);

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
        scrollbar.addAdjustmentListener(e -> repaintImageComponent(scrollbar.getValue()));
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

    public void getTiffMetadata(final String imageName) {
        metaDataReader.readAndDisplayMetadata(imageName);
    }

    public BufferedImage getImage() {
        return tiffReader.getImage(0);
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

    private MainMenu menu;
    private JScrollBar scrollbar;
    private ImageComponent imageComponent;

    public TiffReader tiffReader;
    public MetaDataReader metaDataReader;
}
