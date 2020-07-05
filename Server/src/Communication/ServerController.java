package Communication;

import Controller.DataController;
import Utilities.Result;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Startet und Stoppt die Server Funktionalität.
 */
public class ServerController {

    private ServerSocket _serverSocket;
    private ClientRegisterProcessor _clientRegisterProcessor;

    public ServerController() {
    }

    /**
     * Startet den Server.
     * Dafür wird das ServerSocket initialisiert und
     * der ClientRegisterProcessor Thread gestartet, welcher Verbindungen zu Clients aufbaut.
     *
     * @throws IOException Kann beim Öffnen des Sockets auftreten.
     */
    public void start() throws IOException {
        System.out.println("Server wird gestartet...");
        Result<Void> loadDataResult = DataController.loadData();
        System.out.println(loadDataResult.getMessage());

        _serverSocket = new ServerSocket(9999);
        _serverSocket.setSoTimeout(5000);

        // Startet die Suche nach Clients, läuft innerhalb eines Threads
        _clientRegisterProcessor = new ClientRegisterProcessor(_serverSocket);
        _clientRegisterProcessor.start();
        System.out.println("Server erfolgreich gestartet!");
    }

    /**
     * Beendet den Server.
     * Dafür wird dem ClientRegisterProcessor signalisiert, dass dieser stoppen soll.
     * Danach werden die Daten gespeichert.
     */
    public void stop() {
        _clientRegisterProcessor.exit();
        System.out.println("Server wird beendet...");

        Result<Void> saveDataResult = DataController.getInstance().saveData();
        System.out.println(saveDataResult.getMessage());
    }
}
