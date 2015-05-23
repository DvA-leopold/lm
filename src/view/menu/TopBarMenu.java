package view.menu;

import controller.Controller;
import controller.commands.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TopBarMenu {
    public TopBarMenu(final Controller controller) {
        this.controller = controller;
        menuBar = new JMenuBar();
        menuBar.add(initFileMenu());
        menuBar.add(initEditMenu());
        menuBar.add(initHelpMenu());
    }

    public JMenuBar getMenu() {
        return  menuBar;
    }

    private JMenu initFileMenu() {
        JMenuItem openMenu = new JMenuItem(new AbstractAction("Open") {
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
        JMenuItem saveMenu = new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.runCommand(controller.getCommand(ConvertCommand.class));
                } catch (IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JMenuItem optionMenu = new JMenuItem(new AbstractAction("Options") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.runCommand(controller.getCommand(OptionCommand.class));
                } catch (IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JMenuItem exitMenu = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openMenu);
        fileMenu.add(saveMenu);
        fileMenu.addSeparator();
        fileMenu.add(optionMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);
        return fileMenu;
    }

    private JMenu initEditMenu() {
        JMenuItem insertMetadataMenu = new JMenuItem(new AbstractAction("Add metadata") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.runCommand(controller.getCommand(WriteMetadataCommand.class));
                } catch (IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JMenuItem deleteCustomMetadata = new JMenuItem(new AbstractAction("Delete metadata") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete
            }
        });

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(insertMetadataMenu);
        editMenu.add(deleteCustomMetadata);
        return editMenu;
    }

    private JMenu initHelpMenu() {
        JMenuItem docMenu = new JMenuItem("About");
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(docMenu);
        return helpMenu;
    }


    final private Controller controller;
    private JMenuBar menuBar;
}
