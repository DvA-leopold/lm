package view.menu;

import reader.TiffReader;
import view.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu {
    public MainMenu(final Frame mainFrame) {
        menuBar = new JMenuBar();
        menuBar.add(initFileMenu(mainFrame));
        menuBar.add(initHelpMenu());
    }

    public JMenuBar getMenu() {
        return  menuBar;
    }

    //public int getNumTiffPages() {
    //    return tiffReader.getNumPages();
    //}

    private JMenu initFileMenu(final Frame mainFrame) {
        JMenuItem openMenu = new JMenuItem(new AbstractAction("open") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new FileChooser(menuBar.getComponent());
                mainFrame.setTiffReaderImage(fileChooser.getChosenFilePath());
                mainFrame.repaintImageComponent(null);
                mainFrame.repaintScrollbar();
                //mainFrame.getGraphics().setColor(Color.WHITE);
                //System.out.println(mainFrame.getGraphics().getColor());
                //mainFrame.getGraphics().setColor(Color.red);
                //System.out.println(mainFrame.getGraphics().getColor());
                //mainFrame.getGraphics().drawString("fuck this cruel world", 100, 100);
            }
        });
        JMenuItem saveMenu = new JMenuItem("save");
        JMenuItem exitMenu = new JMenuItem(new AbstractAction("exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openMenu);
        fileMenu.addSeparator();
        fileMenu.add(saveMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);
        return fileMenu;
    }

    private JMenu initHelpMenu() {
        JMenuItem docMenu = new JMenuItem("docs");
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(docMenu);
        return helpMenu;
    }

    public FileChooser fileChooser;


    private JMenuBar menuBar;
}
