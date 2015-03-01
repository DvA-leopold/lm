package view.menu;

import reader.TiffReader;
import view.components.ImageComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainMenu {
    public MainMenu(final ImageComponent imageComponent) {
        menuBar = new JMenuBar();
        menuBar.add(initFileMenu(imageComponent));
        menuBar.add(initHelpMenu());
    }

    public JMenuBar getMenu() {
        return  menuBar;
    }

    private JMenu initFileMenu(ImageComponent imageComponent) {
        JMenuItem openMenu = new JMenuItem(new AbstractAction("open") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = new FileChooser(menuBar.getComponent()).getChosenFilePath();
                imageComponent.setImage(new TiffReader(filePath).getImage());
                imageComponent.repaint();
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

    private JMenuBar menuBar;
}
