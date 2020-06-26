package Communication;

import Model.*;
import Utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ClientController {

    private static ClientController _instance;
    private boolean _isActive;
    private boolean _isConnected;
    private Socket _socket;

    private ObjectInputStream _objectInputStream;
    private ObjectOutputStream _objectOutputStream;

    private ClientController() {
        _isActive = true;
    }

    public static ClientController getInstance() {
        if (_instance == null) {
            _instance = new ClientController();
        }
        return _instance;
    }

    public void connect() {
        _isActive = true;
        _isConnected = false;

        while (_isActive && !_isConnected) {
            try {
                _socket = new Socket("localhost", 9999);
                _isConnected = true;
                _objectOutputStream = new ObjectOutputStream(_socket.getOutputStream());
                _objectInputStream = new ObjectInputStream(_socket.getInputStream());

                System.out.println("VERBUNDEN");
            } catch (UnknownHostException ex) {
                System.out.println(ex);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public Result<Void> sendToServer(Object object) {

        try {
            _objectOutputStream.writeObject(object);
            return new Result<Void>(Result.State.SUCCESSFULL, "", null);
        } catch (InvalidClassException ex) {
            return new Result<Void>(Result.State.FAILED, "Keine Verbindung zum Server möglich.", null);
        } catch (NotSerializableException ex) {
            return new Result<Void>(Result.State.FAILED, "Keine Verbindung zum Server möglich.", null);
        } catch (IOException ex) {
            return new Result<Void>(Result.State.FAILED, "Keine Verbindung zum Server möglich.", null);
        }
    }

    public Result<Object> receiveFromServer() {
        try {
            return new Result<Object>(Result.State.SUCCESSFULL, "", _objectInputStream.readObject());
        } catch (IOException ex) {
            return new Result<Object>(Result.State.FAILED, "Keine Verbindung zum Server möglich.", null);
        } catch (ClassNotFoundException ex) {
            return new Result<Object>(Result.State.FAILED, "Keine Verbindung zum Server möglich.", null);
        }
    }

    public Result<PersonType> login(String username, String password) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.LOGIN, new String[]{username, password});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<PersonType>(Result.State.FAILED, receiveResult.getMessage(), null);
        }

        return (Result<PersonType>) receiveResult.getObject();
    }


    public Result<Void> logout() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.LOGOUT, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        }

        return (Result<Void>) receiveResult.getObject();
    }

    public Result<Void> addEmployee(String firstname, String lastname, String username, String password) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.ADD_EMPLOYEE, new String[]{firstname, lastname, username, password});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }

    public Result<Void> addCustomer(String firstname, String lastname, String username, String password, String street, String houseNumber, String postCode, String city) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.ADD_CUSTOMER, new String[]{firstname, lastname, username, password, street, houseNumber, postCode, city});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }

    public Result<Void> removePerson(Person person) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.REMOVE_PERSON, new String[]{Integer.toString(person.getId())});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }

    public Result<Void> addArticle(String name, String stock, String price, String packagingUnit) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.ADD_ARTICLE, new String[]{name, stock, price, packagingUnit});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }

    public Result<Void> editArticle(Article article, String name, String price) {
        // TODO
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);
        return result;
    }

    public Result<Void> removeArticle(Article article) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.REMOVE_ARTICLE, new String[]{Integer.toString(article.getArticleNumber())});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }


    public Result<Void> updateStock(Article article, String stockChangeValue) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.UPDATE_STOCK, new String[]{Integer.toString(article.getArticleNumber()), stockChangeValue});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }


    public Result<Void> addArticleToShoppingCart(Article article, String numberOfArticles) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.ADD_ARTICLE_TO_SHOPPINGCART, new String[]{Integer.toString(article.getArticleNumber()), numberOfArticles});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }

    public Result<Void> removeArticleFromShoppingCart(Article article) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.REMOVE_ARTICLE_FROM_SHOPPINGCART, new String[]{Integer.toString(article.getArticleNumber())});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }

    public Result<Bill> buyShoppingCart() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.BUY_SHOPPINGCART, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Bill>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Bill>) receiveResult.getObject();
        }
    }

    public Result<Void> clearShoppingCart() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.CLEAR_SHOPPINGCART, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }


    public Result<ArrayList<ShoppingCartItem>> getShoppingCartItemList() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.GET_SHOPPINGCART, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<ArrayList<ShoppingCartItem>>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<ArrayList<ShoppingCartItem>>) receiveResult.getObject();
        }
    }


    public Result<ArrayList<Article>> getArticleList() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.GET_ARTICLE_LIST, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<ArrayList<Article>>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<ArrayList<Article>>) receiveResult.getObject();
        }
    }


    public Result<ArrayList<Customer>> getCustomerList() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.GET_CUSTOMER_LIST, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<ArrayList<Customer>>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<ArrayList<Customer>>) receiveResult.getObject();
        }
    }


    public Result<ArrayList<Employee>> getEmployeeList() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.GET_EMPLOYEE_LIST, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<ArrayList<Employee>>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<ArrayList<Employee>>) receiveResult.getObject();
        }
    }


    public Result<ArrayList<Event>>getEventList() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.GET_CUSTOMER_LIST, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<ArrayList<Event>>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<ArrayList<Event>>) receiveResult.getObject();
        }
    }




}
