package model.readers;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class MetaDataReader {
    public MetaDataReader() {
        //writer = ImageIO.getImageWritersByFormatName("TIFF").next();
        //writeParam = writer.getDefaultWriteParam();
        typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
    }

    public void readAndDisplayMetadata(String imageName) {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(new File(imageName));
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {

                // pick the first available ImageReader
                ImageReader reader = readers.next();

                // attach source to the model.readers
                reader.setInput(iis, true);

                // read metadata of first image
                IIOMetadata metadata = reader.getImageMetadata(0);

                String[] names = metadata.getMetadataFormatNames();
                for (String name : names) {
                    System.out.println("Format name: " + name);
                    displayMetadata(metadata.getAsTree(name));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeMetadata(final String fileName) {
        File file = new File(fileName);
        try {
            ImageOutputStream ios = ImageIO.createImageOutputStream(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageWriter writer;

        // metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);

        IIOMetadataNode root = new IIOMetadataNode("");

        //Add RGB tag, otherwise BMP writing throws NPE
        IIOMetadataNode chroma = new IIOMetadataNode("Chroma");
        IIOMetadataNode colorSpaceType = new IIOMetadataNode("ColorSpaceType");
        colorSpaceType.setAttribute("name", "RGB");
        chroma.appendChild(colorSpaceType);
        root.appendChild(chroma);

        IIOMetadataNode text = new IIOMetadataNode("Text");
        IIOMetadataNode textEntry = new IIOMetadataNode("TextEntry");
        textEntry.setAttribute("keyword", "metainformation");
        textEntry.setAttribute("value", "This is some custom meta information!");
        textEntry.setAttribute("encoding", "UTF-8");
        textEntry.setAttribute("language", "EN");
        textEntry.setAttribute("compression", "none");
        textEntry.setAttribute("illness", "deadbrain");
        text.appendChild(textEntry);

        root.appendChild(text);

        try {
            metadata.mergeTree("", root);
        } catch (IIOInvalidTreeException e) {
            e.printStackTrace();
        }
    }

    public void saveImage() {
        // writing the data
        ImageOutputStream stream = null;
        try {
            stream = ImageIO.createImageOutputStream(new ByteArrayOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //writer.setOutput(stream);

        //IIOImage img = new IIOImage(null, null, metadata);
        //bufferedImages.add(new IIOImage(image, null, null));
        try {
            //writer.write(metadata, img, writeParam);
            assert stream != null;
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }

    private void displayMetadata(Node node, int level) {
        // print open tag of element
        indent(level);
        System.out.print("<" + node.getNodeName());
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                System.out.print(" " + attr.getNodeName() +
                        "=\"" + attr.getNodeValue() + "\"");
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            System.out.println("/>");
            return;
        }

        // children, so close current tag
        System.out.println(">");
        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }

        // print close tag of element
        indent(level);
        System.out.println("</" + node.getNodeName() + ">");
    }

    private void indent(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("    ");
    }

    private ImageWriteParam writeParam;
    //private ImageWriter writer;
    private ImageTypeSpecifier typeSpecifier;
    private IIOMetadata metadata;
}
