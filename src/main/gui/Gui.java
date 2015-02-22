package main.gui;

import java.awt.*;

public class Gui {
    public Gui() {
        EventQueue.invokeLater(() -> {
            frame = new Frame(500, 500);
            frame.init();
        });
    }

    private Frame frame;
}
