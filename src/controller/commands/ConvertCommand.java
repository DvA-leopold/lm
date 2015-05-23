package controller.commands;

import model.ProjectModel;
import view.ProjectView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class ConvertCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        //TODO: stop the conversion if stop button is pressed
        while (!model.filePathIsDirectory()) {
            model.setInputPath(view.getFileChooserPath("open", JFileChooser.DIRECTORIES_ONLY));
        }
        String savePath = view.getFileChooserPath("save as", JFileChooser.DIRECTORIES_ONLY);
        String imageName = "converted";

        new Thread(() -> {
            view.getProgressMonitor().setMaximum(model.getNumberOfImages());
            int maxNumberOfImages = model.getNumberOfImages();
            model.getMultipleFilesConverter().setActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    view.getProgressMonitor().setNote(e.getModifiers() + " of " + maxNumberOfImages + " done");
                    view.getProgressMonitor().setProgress(e.getModifiers());
                }
            });
            boolean isConversionFinish = model.convertImage(model.getInputPath(), savePath, imageName);
            if (isConversionFinish) {
                view.getProgressMonitor().setNote("Conversion completed successfully");
                view.getProgressMonitor().close();
            } else {
                view.getProgressMonitor().setNote("Conversion failed");
            }
        }).start();
    }
}
