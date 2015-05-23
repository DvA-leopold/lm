package controller.commands;

import model.ProjectModel;
import view.ProjectView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public final class WriteMetadataCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        final String filePath = view.getFileChooserPath("add metadata", JFileChooser.FILES_ONLY);
        if (filePath == null) {
            return;
        }
        try {
            model.getMetaDataWriter().initTiffDirectory(ImageIO.createImageInputStream(new File(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.getMetaDataWriter().initWriterOutput("E:/StarkDocs/", "output");
        model.getMetaDataReader().initializeMetadata(filePath);
        String oldMetadata = model.getMetaDataReader().getMetadataInformation();
        String newMetadata = (String) view.showMetadataDialog(oldMetadata);
        model.getMetaDataWriter().addMetadata(newMetadata);
    }
}
