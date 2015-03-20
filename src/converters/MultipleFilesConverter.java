package converters;

import com.sun.org.apache.xpath.internal.operations.Mult;

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

public class MultipleFilesConverter implements Converter {
    public MultipleFilesConverter() {
        writer = ImageIO.getImageWritersByFormatName("tiff").next();
    }

    @Override
    public boolean convert(final String pathToImages, final String outputFileName) {
        try {
            File[] files = verifyDirectoryAndImages(new File(pathToImages));
            ImageWriteParam param = initializeImageWriter(pathToImages, outputFileName);
            writeAllImageFiles(files, outputFileName, param);
            flushAndFree();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * @param pathToImages принимает <code>File</code> который указывает на текущую директорию
     * @return возвращает отсортированный массив всех файлов в данной дирректории
     * @throws FileNotFoundException в случае если файлов в папке нет,
     * или или дирректория не существует выброситься это исключение
     */
    private File[] verifyDirectoryAndImages(final File pathToImages) throws FileNotFoundException {
        if (!pathToImages.isDirectory()) {
            try {
                throw new FileNotFoundException("No pathToImages exists with the given name" + pathToImages.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        File[] files = pathToImages.listFiles();
        if (files == null || files.length == 0) {
            throw new FileNotFoundException("No image files to process");
        } else {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    Integer firstDigit = Integer.parseInt(o1.getName().replaceAll("[^0-9]", ""));
                    Integer secondDigit = Integer.parseInt(o2.getName().replaceAll("[^0-9]", ""));
                    return firstDigit.compareTo(secondDigit);
                }
            });

            return files;
        }
    }

    /**
     * @param pathToImages имя директории в которой лежат изображения для конвертирования
     * @param outputFileName имя файла
     * @return возвращает <code>ImageWriteParam</code> в котором хранится <code>compressionType<code/>
     * <code>compressionQuality<code/> и т.п
     * @throws ClassNotFoundException выбрасывается если <code>ImageIO.getImageWritersByFormatName</code>
     * не смог найти писателя tiff
     */
    //TODO: сделать возможным сжимать изображение, устанавливать качество сжатия и т.п
    private ImageWriteParam initializeImageWriter(final String pathToImages,
                                                  final String outputFileName) throws ClassNotFoundException {
        File file = new File(pathToImages + outputFileName + ".tif");
        try {
            ios = ImageIO.createImageOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the appropriate Tiff Image Writer
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
        if (null == writers || !writers.hasNext()) {
            throw new ClassNotFoundException("Appropriate Tiff writer not found");
        }

        writer.setOutput(ios);

        // Set the compression parameters for Tiff image
        ImageWriteParam param = writer.getDefaultWriteParam();
        //param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        //param.setCompressionType("LZW");
        //param.setCompressionQuality(0.9F);
        return param;
    }

    /**
     * Итерируется по всем изображениям в файле дописывает каждое в <code>outputFileName</code>
     * многотомный тиф
     * @param files файлы, которые нужно записать в многотомный тиф
     * @param outputFileName имя многотомного тифа
     * @param param параметры с которыми мы будем записывать наш многотомный тиф,
     *              устанавливаются в <code>initializeImageWriter</code>
     */
    private void writeAllImageFiles(final File[] files,
                                    String outputFileName,
                                    ImageWriteParam param) {
        for (int i = 0; i < files.length; i++) {
            InputStream fis = null;
            BufferedImage image = null;
            int dotIndex = files[i].getName().lastIndexOf('.');
            dotIndex = dotIndex > 0 ? dotIndex : files[i].getName().length();
            String fileName = files[i].getName().substring(0, dotIndex);
            if (!fileName.equalsIgnoreCase(outputFileName)) {
                try {
                    fis = new BufferedInputStream(new FileInputStream(files[i]));
                    image = ImageIO.read(fis);
                    IIOImage img = new IIOImage(image, null, null);

                    if (i == 0) {
                        writer.write(null, img, param);
                    } else {
                        writer.writeInsert(-1, img, param);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (image != null) {
                        image.flush();
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    private void flushAndFree() {
        try {
            ios.flush();
            ios.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageOutputStream ios = null;
    private ImageWriter writer;
}
