package controller.commands;

import model.ProjectModel;
import view.ProjectView;

public final class ReadMetadataCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        if (!model.filePathIsDirectory()) {
            final String filePath = model.getInputPath();
            model.getMetaDataReader().initializeMetadata(filePath);
        }
    }
}
