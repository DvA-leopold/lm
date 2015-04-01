package controller;

import controller.commands.Command;
import model.ProjectModel;
import view.ProjectView;

import java.awt.*;
import java.util.HashMap;

public class Controller {
    public void run() {
        commandHash = new HashMap<>(3);
        projectModel = new ProjectModel();
        EventQueue.invokeLater(() -> {
            projectView = new ProjectView(500, 500, this);
            projectView.init();
        });
    }

    public void runCommand(final Command command) {
        command.execute(projectModel, projectView);
    }

    public Command getCommand(Class commandClass)
            throws IllegalAccessException, InstantiationException {
        if (commandHash.containsKey(commandClass)) {
            System.out.println("GET");
            return commandHash.get(commandClass);
        } else {
            System.out.println("MISS");
            commandHash.put(commandClass, (Command) commandClass.newInstance());
            return commandHash.get(commandClass);
        }
    }

    private ProjectModel projectModel;
    private ProjectView projectView;

    private HashMap<Class, Command> commandHash;
}
