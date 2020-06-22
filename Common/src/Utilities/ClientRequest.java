package Utilities;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private ClientAction _clientAction;
    private String[] _params;

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
