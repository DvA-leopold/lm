package controller;

import controller.commands.Command;
import model.ProjectModel;
import view.ProjectView;

import java.awt.*;
import java.util.HashMap;

public class Controller {
    public void run() {
        projectModel = new ProjectModel();
        EventQueue.invokeLater(() -> {
            projectView = new ProjectView(500, 500, this);
            projectView.init();
        });
    }

    public void runCommand(final Command command) {
        command.execute(projectModel, projectView);
    }

    private ProjectModel projectModel;
    private ProjectView projectView;

    //private HashMap<> commandHash;
}
