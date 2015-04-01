package controller.commands;

import model.ProjectModel;
import view.ProjectView;

public final class ScrollCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView projectView) {
        int scrollValue = projectView.getScrollValue();
        projectView.repaintImageComponent(model.getImage(scrollValue));
    }
}
