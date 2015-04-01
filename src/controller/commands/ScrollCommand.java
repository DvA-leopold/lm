package controller.commands;

import model.ProjectModel;
import view.ProjectView;

public final class ScrollCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        int scrollValue = view.getScrollValue();
        view.repaintImageComponent(model.getImage(scrollValue));
    }
}
