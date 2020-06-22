package Core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    private boolean _isActive;
    private ServerSocket _serverSocket;
    private ClientRegisterProcessor _clientRegisterProcesssor;

    public ServerController() {
        _isActive = true;
    }

    public void start() throws IOException {
        _serverSocket = new ServerSocket(9999);
        _serverSocket.setSoTimeout(5000);

        System.out.println("Server gestartet.");

        // Startet die Suche nach Clients, läuft innerhalb eines Threads
        _clientRegisterProcesssor = new ClientRegisterProcessor(_serverSocket);
        _clientRegisterProcesssor.start();
    }

    public void stop(){
        _clientRegisterProcesssor.exit();
    }
}
