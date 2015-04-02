package controller.commands;

import model.ProjectModel;
import view.ProjectView;

import javax.swing.*;

public final class ConvertCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        while (model.getInputPath() == null) { // TODO: mb we do not need this while loop
            model.setInputPath(view.getFileChooserPath("open", JFileChooser.DIRECTORIES_ONLY));
        }
        String savePath = view.getFileChooserPath("save as", JFileChooser.DIRECTORIES_ONLY);
        String imageName = "converted";
        //view.progressMonitor.setMaximum(model.getNumberOfImages());
        //view.progressMonitor.setProgress(model.getProgress());

        boolean isConvertingOver = model.convertImage(model.getInputPath(), savePath, imageName);
/*
        if (isConvertingOver) {
            view.progressMonitor.close();
        }
*/
    }
}
