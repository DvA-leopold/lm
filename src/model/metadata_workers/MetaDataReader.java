package model.metadata_workers;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.util.Iterator;

public class MetaDataReader {
    public void initializeMetadata(String fileName) {
        try {
            File file = new File(fileName);
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(iis, true);

                // read metadata of first image
                IIOMetadata metadata = reader.getImageMetadata(0);

                String[] names = metadata.getMetadataFormatNames();

                for (String name : names) {
                    if (name.equals("com_sun_media_imageio_plugins_tiff_image_1.0")) {
                        initMetadataString(metadata.getAsTree(name));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMetadataInformation() {
        return metadataInformation;
    }

    private void initMetadataString(Node root) {
        initMetadataString(root, 0);
    }

    private void initMetadataString(Node node, int level) {
        NamedNodeMap map = node.getAttributes();
        if (map != null) {
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                if (attr.getNodeValue().equals("51135") || valueFound) {
                    valueFound = true;
                    if (level == nestingLevel) {
                        metadataInformation = attr.getNodeValue();
                    }
                }
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            valueFound = false;
            return;
        }

        while (child != null) {
            initMetadataString(child, level + 1);
            child = child.getNextSibling();
        }
    }


    private String metadataInformation = null;
    private boolean valueFound = false;
    final private int nestingLevel = 4;
}
