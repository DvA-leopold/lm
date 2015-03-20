package converters;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class TestConverter {
    public boolean generateMultiPageTiff(String dirName, String outputFileName) throws Exception {
        boolean generated = false;
        ImageOutputStream ios = null;
        ImageWriter writer = null;
        try {
            // verify the directory for the image files
            File dir = new File(dirName);
            if (!dir.isDirectory()) {
                throw new FileNotFoundException("No directory exists with the given name " + dirName);
            }
            // verify the images files exist

            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                throw new FileNotFoundException("No image files to process");

            } else {

                // Create the output file on the disk
                File file = new File(dirName + outputFileName + ".tif");
                ios = ImageIO.createImageOutputStream(file);

                // Get the appropriate Tiff Image Writer
                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
                if (null == writers || !writers.hasNext()) {
                    throw new Exception("Appropriate Tiff writer not found");
                }
                writer = ImageIO.getImageWritersByFormatName("tiff").next();
                writer.setOutput(ios);
                // Set the compression parameters for Tiff image
                ImageWriteParam param = writer.getDefaultWriteParam();
                //param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                //param.setCompressionType("LZW");
                //param.setCompressionQuality(0.9F);

                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        Integer firstDigit = Integer.parseInt(o1.getName().replaceAll("[^0-9]", ""));
                        Integer secondDigit = Integer.parseInt(o2.getName().replaceAll("[^0-9]", ""));
                        return firstDigit.compareTo(secondDigit);
                    }
                });
                // Loop through all image files and write them to output tiff image
                for (int i = 0; i < files.length; i++) {
                    InputStream fis = null;
                    int dotIndex = files[i].getName().lastIndexOf('.');
                    System.out.println(files[i].getName());
                    dotIndex = dotIndex > 0 ? dotIndex : files[i].getName().length();
                    String fileName = files[i].getName().substring(0, dotIndex);
                    if (!fileName.equalsIgnoreCase(outputFileName)) {
                        try {
                            fis = new BufferedInputStream(new FileInputStream(files[i]));
                            BufferedImage image = ImageIO.read(fis);
                            IIOImage img = new IIOImage(image, null, null);
                            if (i == 0) {
                                writer.write(null, img, param);
                            } else {
                                writer.writeInsert(-1, img, param);
                            }
                            image.flush();
                        } finally {
                            if (null != fis) {
                                fis.close();
                            }
                        }
                    }
                }
                ios.flush();
                generated = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            generated = false;
        } finally {
            if (ios != null)
                ios.close();
            if (writer != null)
                writer.dispose();
        }
        return generated;
    }
}