package controller.commands;

import model.ProjectModel;
import view.ProjectView;

import javax.swing.*;

public final class OpenFileCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        String filePath = view.getFileChooserPath("open", JFileChooser.FILES_AND_DIRECTORIES);
        if (filePath == null) {
            return;
        }
        model.setInputPath(filePath);
        view.repaintScrollbar(model.getNumberOfImages());
        view.repaintImageComponent(model.getImage(null));
        model.getMetaDataReader().initializeMetadata(filePath);

        if (!model.filePathIsDirectory()) {
            String currentMetadata = model.getMetaDataReader().getMetadataInformation();
            if (currentMetadata != null) {
                view.getImageComponent().reInitMetadata(currentMetadata.split(";"));
            }
        }

        view.setScrollbarListener();
    }
}
