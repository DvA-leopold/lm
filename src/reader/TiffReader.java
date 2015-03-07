package reader;

import com.sun.media.jai.codec.*;

import java.awt.image.*;
import java.io.IOException;
import java.util.Hashtable;

public class TiffReader {
    public TiffReader() {
        decoder = null;
    }

    public void setTiffImage(String tiffImageName) {
        try {
            decoder = ImageCodec.createImageDecoder("tiff", new FileSeekableStream(tiffImageName), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(int imageIndex) {
        RenderedImage image = null;
        try {
            image = decoder.decodeAsRenderedImage(imageIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertRenderedImage(image);
    }

    public int getNumPages() {
        int decodePages = 100;
        if (decoder == null) {
            return decodePages;
        }
        try {
            decodePages = decoder.getNumPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decodePages;
    }

    private BufferedImage convertRenderedImage(RenderedImage img) {
        ColorModel cm = img.getColorModel();
        int width = img.getWidth();
        int height = img.getHeight();

        Hashtable<String, Object> properties = new Hashtable<>();
        String[] keys = img.getPropertyNames();
        if (keys != null) {
            for (String key : keys) {
                properties.put(key, img.getProperty(key));
            }
        }
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        BufferedImage result = new BufferedImage(
                cm,
                raster,
                cm.isAlphaPremultiplied(),
                properties);

        img.copyData(raster);
        return result;
    }

    private ImageDecoder decoder;
}
