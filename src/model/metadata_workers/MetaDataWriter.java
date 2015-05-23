package model.metadata_workers;

import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;
import com.sun.media.imageio.plugins.tiff.TIFFTag;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.IOException;

public class MetaDataWriter {
    public MetaDataWriter() {
        writer = ImageIO.getImageWritersByFormatName("tiff").next();
        reader = ImageIO.getImageReadersByFormatName("tiff").next();
    }

    public void initTiffDirectory(Object input) {
        reader.setInput(input);
        try {
            tiffDirectory = TIFFDirectory.createFromMetadata(reader.getImageMetadata(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initWriterOutput(String savePath, String outputFileName) {
        try {
            ImageOutputStream ios = ImageIO.createImageOutputStream(new File(savePath + "/" + outputFileName + ".tif"));
            writer.setOutput(ios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMetadata(String stringMetadata) {
        String[] tagValue = { stringMetadata };
        TIFFTag testTag = new TIFFTag("testTag", tiffTagNumber, TIFFTag.TIFF_ASCII);
        TIFFField field = new TIFFField(testTag, TIFFTag.TIFF_ASCII, 1, tagValue);
        tiffDirectory.addTIFFField(field);
        IIOMetadata metadata = tiffDirectory.getAsMetadata();
        try {
            IIOImage image = new IIOImage(reader.readAsRenderedImage(0, new ImageReadParam()), null, metadata);
            writer.write(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    final private int tiffTagNumber = 51135;
    private TIFFDirectory tiffDirectory;
    final private ImageWriter writer;
    final private ImageReader reader;
}
