package reader;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.TIFFDirectory;

import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.RenderedOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;

public class TiffReader {
    public TiffReader(String fileName) {
        try {
            stream = new FileSeekableStream(fileName); //E:\StarkDocs1\frog.tif"
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParameterBlock block = new ParameterBlock();
        block.add(stream);
        TIFFDecodeParam decodeParam = new TIFFDecodeParam();
        decodeParam.setDecodePaletteAsShorts(true);
        image1 = JAI.create("tiff", block);

        checkDataType(image1.getSampleModel().getDataType());
    }

    private void checkDataType(int dataType) {
        if (dataType == DataBuffer.TYPE_BYTE) {
            System.out.println("tiff image is byte ");
            image2 = image1;
            try {
                System.out.println("image directories : " + TIFFDirectory.getNumDirectories(stream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (dataType == DataBuffer.TYPE_SHORT) {
            System.out.println("tiff image is short ");
            byte[] tableData = new byte[0x10000];
            for (int i = 0; i < 0x10000; i++) {
                tableData[i] = (byte)(i >> 8);
            }
            LookupTableJAI table = new LookupTableJAI(tableData);
            image2 = JAI.create("lookup", image1, table);
        } else {
            System.out.println("this tif cannot be displayed: " + dataType);
            System.exit(0);
        }
    }

    public static BufferedImage getImage() {
        return image2.getAsBufferedImage();
    }

    private RenderedOp image1;
    private static RenderedOp image2 = null;
    private FileSeekableStream stream = null;
}
