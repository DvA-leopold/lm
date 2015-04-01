package controller.commands;

import model.ProjectModel;
import view.ProjectView;

public interface Command {
    void execute(final ProjectModel model, final ProjectView projectView);
}
