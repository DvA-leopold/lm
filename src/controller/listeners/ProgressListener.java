package controller.listeners;

public class ProgressListener implements Listener {
    public static int notifyController(int progress) {
        return progress;
    }

    @Override
    public void registerListener() {
        System.out.println("register listener");
    }

    @Override
    public void actionPerformed() {
        System.out.println("action performed");
    }
}
