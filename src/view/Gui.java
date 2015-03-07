package view;

import java.awt.*;

public class Gui {
    public void run() {
        EventQueue.invokeLater(() -> {
            frame = new view.Frame(500, 500);
            frame.init();
        });
    }

    private view.Frame frame;
}
