package Communication;

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

/**
 * Dem ClientRequestInterpreter werden ClientRequest-Objkete übergeben, diese werde hier ausgewertet und behandelt.
 * Hat Zugriff auf den MainController und kann somit dessen Methoden aufrufen und die Daten verändern oder erhalten.
 */
public class ClientRequestInterpreter {

    private MainController _mainController;
    private ObjectOutputStream _objectOutputStream; // Stream Server -> Client

    public ClientRequestInterpreter(ObjectOutputStream objectOutputStream, MainController mainController) {
        _mainController = mainController;
        _objectOutputStream = objectOutputStream;
    }

    /**
     * Diese Methode wird vom ClientRequestProcessor aufgerufen, wenn dieser ein neues ClientRequest
     * vom Client empfangen hat.
     * Basierend auf der definierten ClientAction, wird die entsprechende Methode aufgerufen um
     * die Anfrage zu behandeln und zu beantworten.
     *
     * @param clientRequest Das empfangene ClientRequest
     */
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
                    getShoppingCartItems(clientRequest.getParams());
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            Result<Void> result = new Result<>(Result.State.FAILED, "Falsche Parameter Anzahl", null);
            sendToClient(result);
        }
    }

    /**
     * Schickt dem Client die ShoppingCartItems.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void getShoppingCartItems(String[] params) {
        Result<ArrayList<ShoppingCartItem>> result = _mainController.getShoppingCartItemList();
        sendToClient(result);
    }

    /**
     * Leert das ShoppingCart.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void clearShoppingCart(String[] params) {
        Result<Void> result = _mainController.clearShoppingCart();
        sendToClient(result);
    }

    /**
     * Kauft die Artikel im ShoppingCart.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void buyShoppingCart(String[] params) {
        Result<Bill> result = _mainController.buyShoppingCart();
        sendToClient(result);
    }

    /**
     * Entfernt einen Artikel aus dem ShoppingCart.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void removeArticleFromShoppingCart(String[] params) {
        Result<Void> result = _mainController.removeArticleFromShoppingCart(params[0]);
        sendToClient(result);
    }

    /**
     * Fügt einen Artikel dem ShoppingCart hinzu.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void addArticleToShoppingCart(String[] params) {
        Result<Void> result = _mainController.addArticleToShoppingCart(params[0], params[1]);
        sendToClient(result);
    }

    /**
     * Schickt dem Client die Event-Liste (Änderungsprotokoll).
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void getEventList(String[] params) {
        Result<ArrayList<Event>> result = _mainController.getEventList();
        sendToClient(result);
    }

    /**
     * Schickt dem Client die Artikel-Liste.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void getArticleList(String[] params) {
        Result<ArrayList<Article>> result = _mainController.getArticleList();
        sendToClient(result);
    }

    /**
     * Ändert den Lagerbestand eines Artikels.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void updateStock(String[] params) {
        Result<Void> result = _mainController.updateStock(params[0], params[1]);
        sendToClient(result);
    }

    /**
     * Entfernt einen Artikel aus dem Datenbestand.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void removeArticle(String[] params) {
        Result<Void> result = _mainController.removeArticle(params[0]);
        sendToClient(result);
    }

    /**
     * Erstellt einen neuen Artikel.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void addArticle(String[] params) {
        Result<Void> result = _mainController.addArticle(params[0], params[1], params[2], params[3]);
        sendToClient(result);
    }

    /**
     * Schickt dem Client die Mitarbeiter-Liste.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void getEmployeeList(String[] params) {
        Result<ArrayList<Employee>> result = _mainController.getEmployeeList();
        sendToClient(result);
    }

    /**
     * Schickt dem Client die Kunden-Liste.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void getCustomerList(String[] params) {
        Result<ArrayList<Customer>> result = _mainController.getCustomerList();
        sendToClient(result);
    }

    /**
     * Entfernt eine Person aus dem Datenbestand.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void removePerson(String[] params) {
        Result<Void> result = _mainController.removePerson(params[0]);
        sendToClient(result);
    }

    /**
     * Registriert einen neuen Kunden.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void addCustomer(String[] params) {
        Result<Void> result = _mainController.addCustomer(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        sendToClient(result);
    }

    /**
     * Fügt einen neuen Mitarbeiter dem Datenbestand hinzu.
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void addEmployee(String[] params) {
        Result<Void> result = _mainController.addEmployee(params[0], params[1], params[2], params[3]);
        sendToClient(result);
    }

    /**
     * Meldet die angemeldete Person ab. (LOGOUT)
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void logout(String[] params) {
        Result<Void> result = _mainController.logout();
        sendToClient(result);
    }

    /**
     * Meldetet eine Person an. (LOGIN)
     *
     * @param params Die Parameter werden dem MainController übergeben, auf Client-Seite muss
     *               sichergestellt werden, dass die Parameter Anzahl und Typen mit den geforderten im
     *               MainController übereinstimmen.
     */
    private void login(String[] params) {
        Result<PersonType> result = _mainController.login(params[0], params[1]);
        sendToClient(result);
    }

    /**
     * Sendet ein Result-Objekt an den Client, als Antwort auf dessen Anfrage.
     * Wichtig ist, dass auf die jeweilige Anfrage der richtige Typ zurückgeschickt wird.
     *
     * @param result Das Result-Objekt, welches an den Client geschickt werden soll.
     * @param <T> Der generische Typ des Result-Objektes
     */
    public <T> void sendToClient(Result<T> result) {
        try {
            _objectOutputStream.reset();
            _objectOutputStream.writeUnshared(result);
        } catch (InvalidClassException ex) {
            System.out.println(ex);
        } catch (NotSerializableException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
