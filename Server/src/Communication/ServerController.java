package Communication;

import Controller.DataController;
import Utilities.Result;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerController {

    private ServerSocket _serverSocket;
    private ClientRegisterProcessor _clientRegisterProcesssor;

    public ServerController() {
    }

    public void start() throws IOException {
        System.out.println("Server wird gestartet...");
        Result<Void> loadDataResult = DataController.loadData();
        System.out.println(loadDataResult.getMessage());

        _serverSocket = new ServerSocket(9999);
        _serverSocket.setSoTimeout(5000);

        // Startet die Suche nach Clients, läuft innerhalb eines Threads
        _clientRegisterProcesssor = new ClientRegisterProcessor(_serverSocket);
        _clientRegisterProcesssor.start();
        System.out.println("Server erfolgreich gestartet!");
    }

    public void stop() {
        _clientRegisterProcesssor.exit();
        System.out.println("Server wird beendet...");

        Result<Void> saveDataResult = DataController.getInstance().saveData();
        System.out.println(saveDataResult.getMessage());
    }
}
