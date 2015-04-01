package model;

import com.sun.istack.internal.Nullable;
import model.converters.MultipleFilesConverter;
import model.readers.MultiTiffImageReader;
import model.readers.SimpleImageReader;

import java.awt.image.BufferedImage;
import java.io.File;

public class ProjectModel {
    public ProjectModel() {
        multiTiffImageReader = new MultiTiffImageReader();
        simpleImageReader = new SimpleImageReader();
        multipleFilesConverter = new MultipleFilesConverter();
        /*metaDataReader = new MetaDataReader();*/
    }

    public void convertImage(final String pathToImages,
                             final String pathToSave,
                             final String outputFileName) {
        multipleFilesConverter.convert(pathToImages, pathToSave, outputFileName);
    }

    public void setInputPath(final String inputPath) {
        this.isFilepathDirectory = new File(inputPath).isDirectory();
        if (isFilepathDirectory) {
            simpleImageReader.setDirectoryPath(inputPath);
            this.inputPath = inputPath;
        } else {
            multiTiffImageReader.setDirectoryPath(inputPath);
            this.inputPath = null;
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

    public boolean getFilepathFlag() {
        return isFilepathDirectory;
    }

    public String getInputPath() {
        return inputPath;
    }

    public int getProgress() {
        return multipleFilesConverter.getProgress();
    }

    private boolean isFilepathDirectory;
    private String inputPath = null;

    private MultiTiffImageReader multiTiffImageReader;
    private MultipleFilesConverter multipleFilesConverter;
    private SimpleImageReader simpleImageReader;
    /*private MetaDataReader metaDataReader;*/
}
