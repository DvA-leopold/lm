package controller.commands;

import model.ProjectModel;
import view.ProjectView;

import javax.swing.*;

public final class OpenFileCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        String filePath = view.getFileChooserPath("open", JFileChooser.FILES_AND_DIRECTORIES);
        model.setInputPath(filePath);
        view.repaintScrollbar(model.getNumberOfImages());
        view.repaintImageComponent(model.getImage(null));
        view.setScrollbarListener();
    }
}
