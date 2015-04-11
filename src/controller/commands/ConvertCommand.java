package controller.commands;

import model.ProjectModel;
import view.ProjectView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class ConvertCommand implements Command {
    @Override
    public void execute(ProjectModel model, ProjectView view) {
        while (model.getInputPath() == null) { // TODO: mb we do not need this while loop
            model.setInputPath(view.getFileChooserPath("open", JFileChooser.DIRECTORIES_ONLY));
        }
        String savePath = view.getFileChooserPath("save as", JFileChooser.DIRECTORIES_ONLY);
        String imageName = "converted";

        //TODO: mb add thread join
        new Thread(() -> {
            view.getProgressMonitor().setMaximum(model.getNumberOfImages());
            model.getMultipleFilesConverter().setActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    view.getProgressMonitor().setNote(e.getModifiers() + " files was proceed");
                    view.getProgressMonitor().setProgress(e.getModifiers());
                }
            });
            boolean isConvertingOver = model.convertImage(model.getInputPath(), savePath, imageName);
            if (isConvertingOver) {
                view.getProgressMonitor().setNote("Conversion completed successfully");
                view.getProgressMonitor().close();
            } else {
                view.getProgressMonitor().setNote("Conversion failed");
            }
        }).start();
    }
}
