package model;

import com.sun.istack.internal.Nullable;
import model.converters.MultipleFilesConverter;
import model.image_workers.MultiTiffImageReader;
import model.image_workers.SimpleImageReader;
import model.metadata_workers.MetaDataReader;

import java.awt.image.BufferedImage;
import java.io.File;

public class ProjectModel {
    public ProjectModel() {
        multiTiffImageReader = new MultiTiffImageReader();
        simpleImageReader = new SimpleImageReader();
        multipleFilesConverter = new MultipleFilesConverter();
        metaDataReader = new MetaDataReader();
    }

    public boolean convertImage(String pathToImages, String pathToSave, String outputFileName) {
        return multipleFilesConverter.convert(pathToImages, pathToSave, outputFileName);
    }

    public void readMetadata(final String fileName) {
        metaDataReader.readAndDisplayMetadata(fileName);
    }

    public void setInputPath(final String inputPath) {
        this.isFilepathDirectory = new File(inputPath).isDirectory();
        if (isFilepathDirectory) {
            simpleImageReader.setDirectoryPath(inputPath);
            this.inputPath = null;
        } else {
            multiTiffImageReader.setDirectoryPath(inputPath);
            this.inputPath = inputPath;
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

    /**
     * @return <code>true</code> if file path is a directory, <code>false</code> respectively
     */
    public boolean getFilepathFlag() {
        return isFilepathDirectory;
    }

    public String getInputPath() {
        return inputPath;
    }

    public MultipleFilesConverter getMultipleFilesConverter() {
        return multipleFilesConverter;
    }

    private boolean isFilepathDirectory;
    private String inputPath;

    private MultiTiffImageReader multiTiffImageReader;
    private MultipleFilesConverter multipleFilesConverter;
    private SimpleImageReader simpleImageReader;

    private MetaDataReader metaDataReader;
}
