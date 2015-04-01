package controller.commands;

import model.ProjectModel;
import view.ProjectView;

public final class OpenFileCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        String filePath = view.openWithFileChooser();
        model.setPath(filePath);
        view.repaintScrollbar(model.getNumberOfImages());
        view.repaintImageComponent(model.getImage(null));
        view.setScrollbarListener();
    }
}
