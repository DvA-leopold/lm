package gui.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainMenu {
    public MainMenu() {
        menuBar = new JMenuBar();
        initFileMenu();
        menuBar.add(fileMenu);
    }

    public JMenuBar getMenu() {
        return  menuBar;
    }

    private void initFileMenu() {
        JMenuItem openMenu = new JMenuItem("open");
        JMenuItem saveMenu = new JMenuItem("save");
        JMenuItem exitMenu = new JMenuItem(new AbstractAction("exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu = new JMenu("File");
        fileMenu.add(openMenu);
        fileMenu.addSeparator();
        fileMenu.add(saveMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);
    }


    private void initHelpMenu() {

    }


    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;
}
