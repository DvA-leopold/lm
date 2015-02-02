package gui;

import java.awt.*;

public class Gui {
    public Gui() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new Frame(500, 500);
                frame.init();
            }
        });
    }

    private Frame frame;
}
