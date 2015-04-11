package controller.commands;

import model.ProjectModel;
import view.ProjectView;

/**
 * all commands which implement this interface must be <code>final</code>
 */
public interface Command {
    void execute(final ProjectModel model, final ProjectView view);
}
