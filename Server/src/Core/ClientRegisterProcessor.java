package Core;

import java.io.IOException;
import java.io.InterruptedIOException;
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
        while (_isActive) {
            Socket socket = null;
            try {
                socket = _serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                ClientRequestProcessor clientRequestProcessor = new ClientRequestProcessor(socket, objectInputStream, objectOutputStream);
                clientRequestProcessor.start();

                System.out.println("Verbindung zu neuem Client aufgebaut.");
            } catch (SocketTimeoutException ex){
                // Hier muss nichts unternommen werden, der Timeout wurde nur aktiviert, damit das Loop weiter läuft und den bool prüft
            } catch (IOException ex) {
                System.out.println("Verbindung zu Client fehlgeschlagen. " + ex + " " + ex.getStackTrace());
            }
        }

        System.out.println("Suche nach Clients beendet.");
    }

    public void exit(){
        _isActive = false;
    }
}
