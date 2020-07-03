package Communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Ein Objekt der Klasse ServerRegisterProcessor ist ein Thread, welcher beim Starten nach dem Server sucht und
 * sich mit diesem verbindet. Unter Virbindung wird verstanden, dass ein ObjectOutputStream und ein
 * ObjectInputStream aufgebaut wird, damit ein Austausch stattfinden kann.
 * Die Verwendung als Thread ist nötig, damit der Verbindungsprozess parallel zur GUI stattfinden kann.
 * <p>
 * BUGS: Wurde die Verbindung erstmalig aufgebaut und bricht später wieder ab, kann die Verbindung
 * nicht ohne einen Neustart des Clients aufgebaut werden. Um dies zu realisieren müsste
 * regelmäßig auf den Verbindungsstatus geprüft werden und bei Verbindungsabbruch das Verbindungsloop
 * neugestartet werden.
 */
public class ServerRegisterProcessor extends Thread {
    private boolean _isActive; // Gibt an, ob das Verbindungsloop ausgeführt werden soll (Abbruchbedingung)

    private Socket _socket; // Schnittstelle zwischen Server und Client
    private ObjectInputStream _objectInputStream; // Eingehender Stream vom Server
    private ObjectOutputStream _objectOutputStream; // Ausgehender Stream zum Server

    public ServerRegisterProcessor() {
        _isActive = true;
    }

    @Override
    /**
     *  Die run()-Methode wird ausgeführt, wenn der Thread gestartet wird.
     *  In diesem Fall wird ein Verbindungsloop ausgeführt, welches versucht eine Verbindung zum Server
     *  aufzubauen und solange läuft bis die Verbindung möglich war oder die Abbruchbedingung _isActive auf
     *  false gesetzt wird.
     */
    public void run() {
        _isActive = true;

        while (_isActive) {
            try {
                _socket = new Socket("localhost", 9999); // Verbindungsaufbau zum Server mir der IP localhost und Port 9999
                _isActive = false; // War die Verbindung erfolgreich, wird die Abbruchbedingung gesetzt
                _objectOutputStream = new ObjectOutputStream(_socket.getOutputStream()); // Baut den ausgehenden Stream auf
                _objectInputStream = new ObjectInputStream(_socket.getInputStream()); // Baut den eingehenden Stream auf
                System.out.println("VERBUNDEN"); // Kurze Info in der Console
            } catch (Exception ex) {
                // Bei jeglichem Fehler, wird eine kurze Ausgabe getätigt und das Loop läuft weiter
                System.out.println("KEINE VERBINDUNGV MÖGLICH! " + ex.getMessage()); // Kurze Info in der Console
            }
        }
    }

    /**
     * Die exit()-Methode setzt die Abbruchbedingung des Verbindungsloops, damit dieses abgebrochen wird
     * Anwendungsbeispiel: Beim Beenden des Clients, würde im Hintergrund weiter versucht werden eine
     * Verbindung aufzubauen (sofern das Loop noch läuft), da dies ein Therad ist.
     */
    public void exit() {
        _isActive = false;
    }

    /**
     * Gibt den ausgehenden Stream zum Server zurück.
     *
     * @return Ausgehender Stream: ObjectOutputStream
     */
    public ObjectOutputStream getObjectOutputStream() {
        return _objectOutputStream;
    }

    /**
     * Gibt den eingehenden Stream vom Server zurück.
     *
     * @return Eingehender Stream: ObjectInputStream
     */
    public ObjectInputStream getObjectInputStream() {
        return _objectInputStream;
    }
}
