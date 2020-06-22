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
        if(receiveResult.getState() == Result.State.FAILED){
            return new Result<PersonType>(Result.State.FAILED, receiveResult.getMessage(), null);
        }

        return (Result<PersonType>)receiveResult.getObject();
    }


    public Result<Void> addEmployee(String firstname, String lastname, String username, String password) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        return result;
    }

    public Result<Void> addCustomer(String firstname, String lastname, String username, String password,
                                    String street, String houseNumber, String postCode, String city) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }

    public Result<Void> removePerson(Person person) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public void logout() {

    }


    public Result<Void> addArticle(String name, int stock, double price, int packagingUnit) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public Result<Void> editArticle(Article article, String name, double price) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }

    public Result<Void> removeArticle(Article article) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public Result<Void> updateStock(int articleNumber, int stockChangeValue) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public Result<Void> addArticleToShoppingCart(int articleNumber, int numberOfArticles) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        return result;
    }

    public Result<Void> addArticleToShoppingCart(Article article, int numberOfArticles) {
        return addArticleToShoppingCart(article.getArticleNumber(), numberOfArticles);
    }

    public Result<Void> addArticleToShoppingCart(ShoppingCartItem shoppingCartItem, int numberOfArticles) {
        return addArticleToShoppingCart(shoppingCartItem.getArticle(), numberOfArticles);
    }

    public Result<Void> removeArticleFromShoppingCart(ShoppingCartItem shoppingCartItem) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public Result<Bill> buyShoppingCart() {
        Result<Bill> result = new Result<Bill>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        return result;
    }

    public Result<Void> clearShoppingCart() {
        Result<Void> result = new Result<Void>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_ShoppingCartClearSuccess), null);


        return result;
    }

    public Result<ShoppingCart> getShoppingCart() {
        Result<ShoppingCart> result = new Result<ShoppingCart>(Result.State.SUCCESSFULL, "", null);

        return result;
    }

    private void addEvent(Article article, int stockChangeValue) {

    }

    public Result<ObservableList<Article>> getArticleList() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.GET_ARTICLE_LIST, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if(receiveResult.getState() == Result.State.FAILED){
            return new Result<>(Result.State.FAILED, receiveResult.getMessage(), FXCollections.observableList(new ArrayList<Article>()));
        }

        Result<ArrayList<Article>> articleListResult = (Result<ArrayList<Article>>)receiveResult.getObject();
        return new Result<>(Result.State.SUCCESSFULL, articleListResult.getMessage(), FXCollections.observableList(articleListResult.getObject()));
    }


    public Result<ObservableList<Customer>> getCustomerList() {
        Result<ObservableList<Customer>> result = new Result<ObservableList<Customer>>(Result.State.SUCCESSFULL, "", null);

        return result;
    }


    public Result<ObservableList<Employee>> getEmployeeList() {
        Result<ObservableList<Employee>> result = new Result<ObservableList<Employee>>(Result.State.SUCCESSFULL, "", null);

        return result;
    }


    public Result<ObservableList<Event>> getEventList() {
        Result<ObservableList<Event>> result = new Result<ObservableList<Event>>(Result.State.SUCCESSFULL, "", null);

        return result;
    }


}
