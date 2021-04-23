package Communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Der ClientRegisterProcessor ist ein Thread, welcher in einem Loop Verbindungsanfragen von Clients entgegen nimmt.
 */
public class ClientRegisterProcessor extends Thread {

    private boolean _isActive; // Abbruchbedingung für das Loop
    private ServerSocket _serverSocket;

    public ClientRegisterProcessor(ServerSocket serverSocket) {
        _isActive = true;
        _serverSocket = serverSocket;
    }

    @Override
    /**
     * Wird ausgeführt, wenn der Thread über start() gestartet wurde.
     * Läuft in einem Loop und nimmt Verbindungsanfragen von Clients entgegen.
     * Konnte die Verbindung zu einem neuen Client aufgebaut werden, wird für diesen ein ClientRequestProcessor erstellt.
     * Dieser sit ebenfalls ein Thread und nimmt ab sofort die Anfragen dieses Clients entgegen.
     * Für jeden Client wird ein eigener ClientRequestProcessor-Thread ausgeführt.
     * Dadurch können alle Clients asynchron auf dem Server arbeiten.
     */
    public void run() {
        System.out.println("Suche nach Clients läuft...");

        while (_isActive) {
            Socket socket = null;
            try {
                socket = _serverSocket.accept();
                System.out.println("Verbindung zu Client wird aufgebaut... ");
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                ClientRequestProcessor clientRequestProcessor = new ClientRequestProcessor(socket, objectInputStream, objectOutputStream);
                clientRequestProcessor.start();
                System.out.println("Verbindung zu Client erfolgreich aufgebaut.");

            } catch (SocketTimeoutException ex) {
                // Timeout, kann ignoriert werden
                // Sorgt dafür, dass die while-Schleife weiter läuft und die Bedingung prüfen kann
                // Ansonsten würde das Programm bei .accept() ewig stehen bleiben
            } catch (IOException ex) {
                System.out.println("Verbindung zu Client fehlgeschlagen. " + ex.getMessage());
            }
        }

        System.out.println("Suche nach Clients beendet.");
    }

    /**
     * Setzt die Abbruchbedingung für das Loop in run().
     */
    public void exit() {
        System.out.println("Suche nach Clients wird beendet....");
        _isActive = false;
    }
}
