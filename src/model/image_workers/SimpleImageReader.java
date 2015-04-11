package model.image_workers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class SimpleImageReader {
    public boolean setDirectoryPath(final String pathToImage) {
        boolean isDirectoryCorrect = false;
        try {
            files = verifyDirectoryAndImages(pathToImage);
            isDirectoryCorrect = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return isDirectoryCorrect;
    }

    private File[] verifyDirectoryAndImages(final String pathToImages) throws FileNotFoundException {
        File pathDescriptor = new File(pathToImages);
        if (!pathDescriptor.isDirectory()) {
            throw new FileNotFoundException(
                    "No pathToImages exists with the given name " + pathDescriptor.getName()
            );
        }
        File[] files = pathDescriptor.listFiles();
        if (files == null || files.length == 0) {
            throw new FileNotFoundException("No image files to process ");
        } else {
            // TODO: маски для сохарниения
            Arrays.sort(files, (o1, o2) -> {
                Integer firstFileNumber = Integer.parseInt(o1.getName().replaceAll("[^0-9]", ""));
                Integer secondFileNumber = Integer.parseInt(o2.getName().replaceAll("[^0-9]", ""));
                return firstFileNumber.compareTo(secondFileNumber);
            });

            return files;
        }
    }

    public BufferedImage getImage(int imageId) {
        BufferedImage image = null;
        try {
            InputStream fis = new BufferedInputStream(new FileInputStream(files[imageId]));
            image = ImageIO.read(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public int getNumberOfFiles() {
        return files.length;
    }

    private File[] files;
}
