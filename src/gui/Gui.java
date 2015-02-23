package gui;

import java.awt.*;

public class Gui {
    public Gui() {
        EventQueue.invokeLater(() -> {
            frame = new gui.Frame(500, 500);
            frame.init();
        });
    }

    private gui.Frame frame;
}
