package view.menu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class FileChooser {
    public FileChooser(Component component) {
        this.component = component;
        FileNameExtensionFilter nameFilter = new FileNameExtensionFilter("tiff files", "tif", "tiff");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(nameFilter);
        //fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    private int fileChooserDialog(Component parent) {
        return fileChooser.showDialog(parent, "open");
    }

    public String getChosenFilePath(String dialogString, int selectionMode) {
        fileChooser.setFileSelectionMode(selectionMode);
        fileChooser.setDialogTitle(dialogString);
        if (fileChooserDialog(component) == JFileChooser.APPROVE_OPTION) {
            setVisible(false);
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    public void setVisible(boolean isVisible) {
        fileChooser.setVisible(isVisible);
    }

    private Component component;
    private JFileChooser fileChooser;
}
