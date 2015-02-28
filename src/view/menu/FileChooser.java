package view.menu;

import com.sun.corba.se.impl.io.TypeMismatchException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class FileChooser {
    public FileChooser(Component component) {
        this.component = component;
        FileNameExtensionFilter nameFilter = new FileNameExtensionFilter("tiff files", "tif", "tiff");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(nameFilter);

    }

    private int fileChooserDialog(Component parent) {
        return fileChooser.showDialog(parent, "open");
    }

    public String getChosenFilePath() {
        if (fileChooserDialog(component) == JFileChooser.APPROVE_OPTION) {
            System.out.println("file to open: " + fileChooser.getSelectedFile().getAbsolutePath());
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    private Component component;
    private JFileChooser fileChooser;
}
