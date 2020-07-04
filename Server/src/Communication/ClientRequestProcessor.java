package Communication;

import Controller.MainController;
import Utilities.ClientRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 *  Der ClientRequestProcessor ist ein Thread, welcher für einen einzelnen Client alle Anfragen entgegen nimmt.
 */
public class ClientRequestProcessor extends Thread {

    private MainController _mainController;
    private boolean _isActive; // Abbruchbedingung
    private Socket _socket; // Schnittstelle zwischen Client und Server
    private ObjectInputStream _objectInputStream; // Stream für Client -> Server
    private ObjectOutputStream _objectOutputStream; // Stream für Server -> Client
    private ClientRequestInterpreter _clientRequestInterpreter; // Verarbeitet ClientRequests

    public ClientRequestProcessor(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        _isActive = true;
        _socket = socket;
        _objectInputStream = objectInputStream;
        _objectOutputStream = objectOutputStream;
        _mainController = new MainController();
        _clientRequestInterpreter = new ClientRequestInterpreter(_objectOutputStream, _mainController);
    }

    @Override
    /**
     * Wird ausgeführt, wenn der Thread über start() gestartet wurde.
     * Dieses Loop nimmt durchgehend Anfragen von dem verbundenen Client entgegen und üebrgibt diese an
     * den ClientRequestInterpreter, dieser verarbeitet die Anfragen und antwortet dem Client.
     */
    public void run() {
        System.out.println("Server wartet auf Client Anfragen...");
        while (_isActive) {
            try {
                ClientRequest clientRequest = (ClientRequest) _objectInputStream.readObject();
                System.out.println("Nachricht empfangen: " + clientRequest.getClientAction().toString());

                _clientRequestInterpreter.interpret(clientRequest);

                System.out.println("Nachricht gesendet");
            } catch (SocketException ex) {
                System.out.println("Fehler: Verbindung zu Client verloren!");
                exit();
            } catch (IOException ex) {
                System.out.println("Fehler: " + ex);
                _isActive = false;
            } catch (ClassNotFoundException ex) {
                System.out.println("Fehler: " + ex);
                // Daten konnten nicht verabeitet werden oder so
            }
        }
        System.out.println("Verbindung zum Client wurde getrennt!");
    }

    /**
     * Setzt die Abbruchbedingung und schließt somit die Verbindung mit dem Client.
     * Aktuell wird diese Methode noch nicht von außerhalb aufgerufen.
     * TODO: Muss eigentlich von außen aufegrufen werden können, beim stoppen des Servers
     */
    public void exit() {
        System.out.println("Verbindung zum Client wird getrennt...");
        _isActive = false;
        _mainController.logout(); // Abmelden erzwingen
        try {
            _objectOutputStream.flush();
            _objectOutputStream.close();
            _objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
