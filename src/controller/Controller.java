package controller;

import controller.commands.Command;
import controller.listeners.Listener;
import model.ProjectModel;
import view.ProjectView;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Controller {
    public void run() {
        commandPool = new HashMap<>(3);
        listOfListeners = new LinkedList<>();
        projectModel = new ProjectModel();
        EventQueue.invokeLater(() -> {
            projectView = new ProjectView(600, 600, this);
            projectView.init();
        });
    }

    public void runCommand(final Command command) {
        command.execute(projectModel, projectView);
    }

    public Command getCommand(Class commandClass)
            throws IllegalAccessException, InstantiationException {
        if (!commandPool.containsKey(commandClass)) {
            commandPool.put(commandClass, (Command) commandClass.newInstance());
        }
        return commandPool.get(commandClass);
    }

    private ProjectModel projectModel;
    private ProjectView projectView;

    private List<Listener> listOfListeners;
    private HashMap<Class, Command> commandPool;
}
