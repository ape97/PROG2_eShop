package Core;


import Utilities.ClientRequest;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientRequestProcessor extends Thread {

    private boolean _isActive;
    private Socket _socket;
    private ObjectInputStream _objectInputStream;
    private ObjectOutputStream _objectOutputStream;
    private ClientRequestInterpreter _clientRequestInterpreter;

    public ClientRequestProcessor(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        _isActive = true;
        _socket = socket;
        _objectInputStream = objectInputStream;
        _objectOutputStream = objectOutputStream;
        _clientRequestInterpreter = new ClientRequestInterpreter(_objectOutputStream);
    }

    @Override
    public void run() {
        System.out.println("ClientRequestProcessor gestartet!");
        while (_isActive) {
            try {
                System.out.println("Server lesen...");
                ClientRequest clientRequest = (ClientRequest) _objectInputStream.readObject();
                System.out.println("Server hat Nachricht empfangen: ");
               // System.out.println(clientRequest.getClientAction().toString());

                _clientRequestInterpreter.interpret(clientRequest);

                System.out.println("Nachricht gesendet");
            } catch (IOException ex) {
                System.out.println("Leider Fehler X" + ex);
                _isActive = false;
            } catch (ClassNotFoundException ex) {
                System.out.println("Leider Fehler Y" + ex);
                // Daten konnten nicht verabeitet werden oder so
            }

            try
            {
                _objectOutputStream.close();
                _objectInputStream.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
