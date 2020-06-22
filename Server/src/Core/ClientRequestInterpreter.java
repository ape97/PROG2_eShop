package Core;

import Controller.MainController;
import Model.*;
import Utilities.ClientRequest;
import Utilities.PersonType;
import Utilities.Result;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ClientRequestInterpreter {

    private MainController _mainController;
    private ObjectOutputStream _objectOutputStream;

    public ClientRequestInterpreter(ObjectOutputStream objectOutputStream) {
        _mainController = new MainController();
        _objectOutputStream = objectOutputStream;
    }

    public void interpret(ClientRequest clientRequest) {
        try {
            switch (clientRequest.getClientAction()) {
                case LOGIN:
                    login(clientRequest.getParams());
                    break;
                case LOGOUT:
                    logout(clientRequest.getParams());
                    break;
                case ADD_EMPLOYEE:
                    addEmployee(clientRequest.getParams());
                    break;
                case ADD_CUSTOMER:
                    addCustomer(clientRequest.getParams());
                    break;
                case REMOVE_PERSON:
                    removePerson(clientRequest.getParams());
                    break;
                case GET_CUSTOMER_LIST:
                    getCustomerList(clientRequest.getParams());
                    break;
                case GET_EMPLOYEE_LIST:
                    getEmployeeList(clientRequest.getParams());
                    break;
                case ADD_ARTICLE:
                    addArticle(clientRequest.getParams());
                    break;
                case REMOVE_ARTICLE:
                    removeArticle(clientRequest.getParams());
                    break;
                case UPDATE_STOCK:
                    updateStock(clientRequest.getParams());
                    break;
                case GET_ARTICLE_LIST:
                    getArticleList(clientRequest.getParams());
                    break;
                case GET_EVENT_LIST:
                    getEventList(clientRequest.getParams());
                    break;
                case ADD_ARTICLE_TO_SHOPPINGCART:
                    addArticleToShoppingCart(clientRequest.getParams());
                    break;
                case REMOVE_ARTICLE_FROM_SHOPPINGCART:
                    removeArticleFromShoppingCart(clientRequest.getParams());
                    break;
                case BUY_SHOPPINGCART:
                    buyShoppingCart(clientRequest.getParams());
                    break;
                case CLEAR_SHOPPINGCART:
                    clearShoppingCart(clientRequest.getParams());
                    break;
                case GET_SHOPPINGCART:
                    getShoppingCart(clientRequest.getParams());
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            Result<Void> result = new Result<>(Result.State.FAILED, "Falsche Parameter Anzahl", null);
            sendToClient(result);
        }
    }

    private void getShoppingCart(String[] params) {
        Result<ShoppingCart> result = _mainController.getShoppingCart();
        sendToClient(result);
    }

    private void clearShoppingCart(String[] params) {
        Result<Void> result = _mainController.clearShoppingCart();
        sendToClient(result);
    }

    private void buyShoppingCart(String[] params) {
        Result<Bill> result = _mainController.buyShoppingCart();
        sendToClient(result);
    }

    private void removeArticleFromShoppingCart(String[] params) {
        Result<Void> result = _mainController.removeArticleFromShoppingCart(params[0]);
        sendToClient(result);
    }

    private void addArticleToShoppingCart(String[] params) {
        Result<Void> result = _mainController.addArticleToShoppingCart(params[0], params[1]);
        sendToClient(result);
    }

    private void getEventList(String[] params) {
        Result<ArrayList<Event>> result = _mainController.getEventList();
        sendToClient(result);
    }

    private void getArticleList(String[] params) {
        Result<ArrayList<Article>> result = _mainController.getArticleList();
        sendToClient(result);
    }

    private void updateStock(String[] params) {
        Result<Void> result = _mainController.updateStock(params[0], params[1]);
        sendToClient(result);
    }

    private void removeArticle(String[] params) {
        Result<Void> result = _mainController.removeArticle(params[0]);
        sendToClient(result);
    }

    private void addArticle(String[] params) {
        Result<Void> result = _mainController.addArticle(params[0], params[1], params[2], params[3]);
        sendToClient(result);
    }

    private void getEmployeeList(String[] params) {
        Result<ArrayList<Employee>> result = _mainController.getEmployeeList();
        sendToClient(result);
    }

    private void getCustomerList(String[] params) {
        Result<ArrayList<Customer>> result = _mainController.getCustomerList();
        sendToClient(result);
    }

    private void removePerson(String[] params) {
        Result<Void> result = _mainController.removePerson(params[0]);
        sendToClient(result);
    }

    private void addCustomer(String[] params) {
        Result<Void> result = _mainController.addCustomer(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        sendToClient(result);
    }

    private void addEmployee(String[] params) {
        Result<Void> result = _mainController.addEmployee(params[0], params[1], params[2], params[3]);
        sendToClient(result);
    }

    private void logout(String[] params) {
        Result<Void> result = _mainController.logout();
        sendToClient(result);
    }

    private void login(String[] params) {
        Result<PersonType> result = _mainController.login(params[0], params[1]);
        sendToClient(result);
    }

    public void sendToClient(Object object) {
        try {
            _objectOutputStream.writeObject(object);
        } catch (InvalidClassException ex) {
            System.out.println(ex);
        } catch (NotSerializableException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
