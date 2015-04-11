package view.menu;

import controller.Controller;
import controller.commands.ReadMetadataCommand;
import controller.commands.ConvertCommand;
import controller.commands.OpenFileCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TopBarMenu {
    public TopBarMenu(final Controller controller) {
        this.controller = controller;
        menuBar = new JMenuBar();
        menuBar.add(initFileMenu());
        menuBar.add(initHelpMenu());
    }

    public JMenuBar getMenu() {
        return  menuBar;
    }

    private JMenu initFileMenu() {
        JMenuItem openMenu = new JMenuItem(new AbstractAction("open") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.runCommand(controller.getCommand(OpenFileCommand.class));
                    controller.runCommand(controller.getCommand(ReadMetadataCommand.class));
                } catch (IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JMenuItem saveMenu = new JMenuItem(new AbstractAction("save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.runCommand(controller.getCommand(ConvertCommand.class));
                } catch (IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JMenuItem exitMenu = new JMenuItem(new AbstractAction("exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openMenu);
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

    private final Controller controller;
    private JMenuBar menuBar;
}
