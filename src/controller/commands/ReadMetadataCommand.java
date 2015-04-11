package controller.commands;

import model.ProjectModel;
import view.ProjectView;

import javax.swing.*;

public final class ReadMetadataCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        if (!model.getFilepathFlag()) {
            final String filePath = model.getInputPath();
            model.readMetadata(filePath);
        }
    }
}
