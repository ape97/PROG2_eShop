import Communication.ClientController;
import View.GUI.MainFrame;
import javafx.application.Application;

/**
 * Der Einstiegpunkt der Client-Anwendung.
 */
public class Client {
    public static void main(String[] args) {
        // Startet zuerst das Verbindung-Loop im ClientController (intern als Thread)
        ClientController.getInstance().start();
        // Startet die GUI
        Application.launch(MainFrame.class, args);
        // Nach Schließung der GUI, wird auch das ggf. aktive Verbindung-Loop gestoppt,
        // damit das Programm nicht ohne GUI weiter läuft
        ClientController.getInstance().stop();
    }
}