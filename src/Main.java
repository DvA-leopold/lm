import com.sun.media.jai.codec.SeekableStream;
import gui.Gui;

import javax.media.jai.JAI;
import java.awt.image.RenderedImage;

public class Main {
    public static void main(String[] args) {
        Gui gui = new Gui();
    }

    public void jaiTest() {
        RenderedImage sourceImage = JAI.create("TIFF", "sagra.tif");
        SeekableStream stream;
        //TIFFDirectory.getNumDirectories(stream);
    }
}