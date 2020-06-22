package Communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientRegisterProcessor extends Thread {

    private boolean _isActive;
    private ServerSocket _serverSocket;

    public ClientRegisterProcessor(ServerSocket serverSocket) {
        _isActive = true;
        _serverSocket = serverSocket;
    }

    @Override
    public void run() {
        System.out.println("Suche nach Clients läuft...");

        while (_isActive) {
            Socket socket = null;
            try {
                socket = _serverSocket.accept();
                System.out.println("Verbindung zu Client wird aufgebaut... ");
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                // TODO: die clientrequestprocessor müssen noch in eine liste und alle beendet werden oder dann wenn sich ein client abmeldet
                ClientRequestProcessor clientRequestProcessor = new ClientRequestProcessor(socket, objectInputStream, objectOutputStream);
                clientRequestProcessor.start();
                System.out.println("Verbindung zu Client erfolgreich aufgebaut.");

            } catch (SocketTimeoutException ex){
                // Timeout, kann ignoriert werden
                // Sorgt dafür, dass die while-Schleife weiter läuft und die Bedingung prüfen kann
                // Ansonsten würde das Programmm bei .accept() ewig stehen bleiben
            } catch (IOException ex) {
                System.out.println("Verbindung zu Client fehlgeschlagen. " + ex.getMessage());
            }
        }

        System.out.println("Suche nach Clients beendet.");
    }

    public void exit(){
        System.out.println("Suche nach Clients wird beendet....");
        _isActive = false;
    }
}
