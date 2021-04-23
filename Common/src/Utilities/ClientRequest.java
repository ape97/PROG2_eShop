package Utilities;

import java.io.Serializable;

/**
 * Objekte dieser Klasse werden für den Client zu Server Datenaustausch verwendet.
 * Der Client schickt dem Server ausschließlich Objekte dieser Klasse.
 * Der Server bekommt die auszuführende ClientAction inkl. der Werte als String-Array.
 *
 */
public class ClientRequest implements Serializable {
    private ClientAction _clientAction; // Die auszuführende Aktion
    private String[] _params; // Die für die Aktion relevanten Daten

    public ClientRequest(ClientAction clientAction, String[] params){
        _clientAction = clientAction;
        _params = params;
    }

    public ClientAction getClientAction(){
        return _clientAction;
    }

    public String[] getParams(){
        return _params;
    }
}
