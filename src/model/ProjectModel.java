package model;

import com.sun.istack.internal.Nullable;
import model.readers.MultiTiffImageReader;
import model.readers.SimpleImageReader;

import java.awt.image.BufferedImage;
import java.io.File;

public class ProjectModel {
    public ProjectModel() {
        multiTiffImageReader = new MultiTiffImageReader();
        simpleImageReader = new SimpleImageReader();
        /*metaDataReader = new MetaDataReader();*/
    }

    public void setPath(final String path) {
        this.isFilepathDirectory = new File(path).isDirectory();
        if (isFilepathDirectory) {
            simpleImageReader.setDirectoryPath(path);
        } else {
            multiTiffImageReader.setDirectoryPath(path);
        }
    }

    public int getNumberOfImages() {
        if (isFilepathDirectory) {
            return simpleImageReader.getNumberOfFiles();
        } else {
            return multiTiffImageReader.getNumberOfPages();
        }
    }

    public BufferedImage getImage(@Nullable Integer imageNumber) {
        int number = imageNumber == null ? 0 : imageNumber;
        if (isFilepathDirectory) {
            return simpleImageReader.getImage(number);
        } else {
            return multiTiffImageReader.getImage(number);
        }
    }

    private boolean isFilepathDirectory;
    private MultiTiffImageReader multiTiffImageReader;
    private SimpleImageReader simpleImageReader;
    /*private MetaDataReader metaDataReader;*/
}
