import Communication.ClientController;
import View.GUI.MainFrame;
import javafx.application.Application;

public class Client {
    public static void main(String[] args) {
        ClientController.getInstance().start();
        Application.launch(MainFrame.class, args);
        ClientController.getInstance().stop();
    }
}