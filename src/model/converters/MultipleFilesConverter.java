package model.converters;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

public class MultipleFilesConverter {
    public MultipleFilesConverter() {
        try {
            initImageWriter();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean convert(String pathToImages, String savePathFolder, String outputFileName) {
        try {
            File[] files = verifyDirectoryAndImages(pathToImages);
            initOutputStream(savePathFolder, outputFileName);
            setImageCompression(ImageWriteParam.MODE_EXPLICIT, "LZW", 0.5f);
            createVoluminousTiff(files, outputFileName);
            flushAndFree();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * устанавливает тип сжатия, мод сжатия и качество
     * @param compressionMode <code>ImageWriteParam.MODE_DEFAULT,
     *                        ImageWriteParam.MODE_DISABLED,
     *                        ImageWriteParam.MODE_EXPLICIT</code>
     * @param imageCompression тип компрессии <code>LZW, JPEG, PackBits, Deflate</code>
     * @param compressionQuality значение лежащее в пределах от 0.f до 1.f
     */
    public void setImageCompression(int compressionMode, String imageCompression, float compressionQuality) {
        imageParams = writer.getDefaultWriteParam();
        imageParams.setCompressionMode(compressionMode);
        imageParams.setCompressionType(imageCompression);
        imageParams.setCompressionQuality(compressionQuality);
    }

    /**
     * @param pathToImages принимает <code>File</code> который указывает на текущую директорию
     * @return возвращает отсортированный массив всех файлов в данной дирректории
     * @throws FileNotFoundException в случае если файлов в папке нет,
     *                               или дирректория не существует
     */
    private File[] verifyDirectoryAndImages(String pathToImages) throws FileNotFoundException {
        File pathImageDescriptor = new File(pathToImages);
        if (!pathImageDescriptor.isDirectory()) {
            throw new FileNotFoundException(
                    "No pathToImages exists with the given name " + pathImageDescriptor.getName()
            );
        }
        File[] files = pathImageDescriptor.listFiles();
        if (files == null || files.length == 0) {
            throw new FileNotFoundException("No image files to process ");
        } else {
            Arrays.sort(files, (o1, o2) -> {
                Integer firstDigit = Integer.parseInt(o1.getName().replaceAll("[^0-9]", ""));
                Integer secondDigit = Integer.parseInt(o2.getName().replaceAll("[^0-9]", ""));
                return firstDigit.compareTo(secondDigit);
            });
            return files;
        }
    }

    private void initImageWriter() throws ClassNotFoundException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
        if (writers == null || !writers.hasNext()) {
            throw new ClassNotFoundException("Appropriate Tiff writer not found");
        }
        writer = ImageIO.getImageWritersByFormatName("tiff").next();
    }

    private void initOutputStream(final String savePath, final String outputFileName) {
        File outputFile = new File(savePath + "/" + outputFileName + ".tif"); //TODO: test this slash
        try {
            ios = ImageIO.createImageOutputStream(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.setOutput(ios);
    }

    /**
     * Итерируется по всем изображениям в файле дописывает каждое в <code>outputFileName</code>
     * многотомный тиф
     * @param files массив файлов, которые нужно записать в многотомный тиф
     * @param outputFileName имя многотомного тифа
     */
    private void createVoluminousTiff(final File[] files, String outputFileName) {
        for (int i = 0; i < files.length; i++) {
            InputStream fis;
            BufferedImage image;
            int dotIndex = files[i].getName().lastIndexOf('.');
            dotIndex = dotIndex > 0 ? dotIndex : files[i].getName().length();
            String fileName = files[i].getName().substring(0, dotIndex);
            if (!fileName.equalsIgnoreCase(outputFileName)) {
                try {
                    fis = new BufferedInputStream(new FileInputStream(files[i]));
                    image = ImageIO.read(fis);
                    IIOImage img = new IIOImage(image, null, null); //write custom netadata here
                    if (i == 0) {
                        writer.write(null, img, imageParams);
                    } else {
                        writer.writeInsert(-1, img, imageParams);
                    }
                    fis.close();
                    image.flush();
                } catch (IOException e) {
                    e.printStackTrace();
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
    private ImageWriteParam imageParams;
}
