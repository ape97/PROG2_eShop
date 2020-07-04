package Communication;

import Model.*;
import Utilities.*;

import java.util.ArrayList;

/**
 * Die Klasse ClientController implementiert das SingeltonPattern, für einen Zugriff auf das selbe Objekt in dem
 * gesamten Client-Projekt.
 * Der ClientController enthält alle möglichen Server-Requests (Fragen). Über das Objekt dieser Klasse können
 * im Client an den entsprechenden Stellen die benötigten Daten vom Server angefordert und empfangen werden.
 */
public class ClientController {
    private static ClientController _instance; // Singelton: Statsiches eindeutiges Objekt der Klasse
    private ServerRegisterProcessor _serverRegisterProcessor;

    /**
     * Singelton: private, damit nur intern ein Objekt erzeugt werden kann
     */
    private ClientController() {
    }

    /**
     * Singelton:
     * Ermöglicht den Zugriff auf das Singelton ClientController-Objekt.
     * Durch Prüfung auf ein existierendes Objekt, wird eins erstellt oder das vorhandene zurückgegeben.
     *
     * @return Gibt die einzigartige Instanz von ClientController zurück
     */
    public static ClientController getInstance() {
        if (_instance == null) {
            _instance = new ClientController();
        }
        return _instance;
    }

    /**
     * Durch Aufruf wird der interne ServerRegisterProcessor-Thread gestartet,
     * welcher dann das Verbindungsloop startet um sich mit einem Server zu verbinden.
     */
    public void start() {
        _serverRegisterProcessor = new ServerRegisterProcessor();
        _serverRegisterProcessor.start();
    }

    /**
     * Ruft auf dem internen ServerRegisterProcessor-Thread die exit()-Methode auf,
     * dadurch wird das Verbindungsloop bei der nächsten Prüfung der Abbruchbedingung gestoppt.
     */
    public void stop() {
        _serverRegisterProcessor.exit();
    }

    /**
     * Ermöglicht das Senden eines Objektes an den Server und gibt ein Result-Objekt zurück,
     * welches aussagt, ob die Sendung erfolgreich war.
     *
     * @param object Das Objekt welches an den Server gesendet werden soll.
     * @return Gibt ein Result<Void> mit dem Status der Aktion zurück.
     */
    public Result<Void> sendToServer(Object object) {
        try {
            _serverRegisterProcessor.getObjectOutputStream().writeObject(object);
            return new Result<Void>(Result.State.SUCCESSFULL, "", null);
        } catch (Exception ex) {
            return new Result<Void>(Result.State.FAILED, "Keine Verbindung zum Server möglich.", null);
        }
    }

    /**
     * Ermöglicht das Empfangen eines Result<Object>-Objektes vom Server.
     * Die eigentlichem Daten sind aus dem Result mit getObject() zu erhalten,
     * dass Result gibt den Status der Aktion zurück.
     *
     * @return
     */
    public Result<Object> receiveFromServer() {
        try {
            return new Result<Object>(Result.State.SUCCESSFULL, "", _serverRegisterProcessor.getObjectInputStream().readUnshared());
        } catch (Exception ex) {
            return new Result<Object>(Result.State.FAILED, "Keine Verbindung zum Server möglich.", null);
        }
    }

    /**
     * Anmelden:
     * Sendet dem Server eine Login-Anfrage mit den entsprechenden Daten und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion und die nötigen Werte hinetrlegt sind.
     *
     * @param username Benutzername
     * @param password Kennwort
     * @return Gibt ein Result<PersonType>-Objekt zurück
     */
    public Result<PersonType> login(String username, String password) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.LOGIN, new String[]{username, password});
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<PersonType>(Result.State.FAILED, receiveResult.getMessage(), null);
        }

        return (Result<PersonType>) receiveResult.getObject();
    }

    /**
     * Abmelden:
     * Sendet dem Server eine Logout-Anfrage.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion hinterlegt ist.
     *
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> logout() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.LOGOUT, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        }

        return (Result<Void>) receiveResult.getObject();
    }

    /**
     * Hinzufügen eines Mitarbeiters:
     * Sendet dem Server eine addEmployee-Anfrage mit den entsprechenden Daten und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion und die nötigen Werte hinetrlegt sind.
     *
     * @param firstname Vorname
     * @param lastname  Nachname
     * @param username  Benutzername
     * @param password  Kennwort
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> addEmployee(String firstname, String lastname, String username, String password) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.ADD_EMPLOYEE, new String[]{firstname, lastname, username, password});
        return sendToServerAndGetVoidResult(clientRequest);
    }

    /**
     * Hinzufügen eines Kunden / Registrieren:
     * Sendet dem Server eine addCustomer-Anfrage mit den entsprechenden Daten und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion und die nötigen Werte hinetrlegt sind.
     *
     * @param firstname   Vorname
     * @param lastname    Nachname
     * @param username    Benutzername
     * @param password    Kennwort
     * @param street      Straße
     * @param houseNumber Hausnummer
     * @param postCode    Postleitzahl
     * @param city        Stadt/Ort
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> addCustomer(String firstname, String lastname, String username, String password, String street, String houseNumber, String postCode, String city) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.ADD_CUSTOMER, new String[]{firstname, lastname, username, password, street, houseNumber, postCode, city});
        return sendToServerAndGetVoidResult(clientRequest);
    }

    /**
     * Entfernen einer Person (Kunde/Mitarbeiter):
     * Sendet dem Server eine removePerson-Anfrage mit den entsprechenden Daten und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion und die nötigen Werte hinetrlegt sind.
     *
     * @param person Das Objekt der zu entfernenden Person
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> removePerson(Person person) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.REMOVE_PERSON, new String[]{Integer.toString(person.getId())});
        return sendToServerAndGetVoidResult(clientRequest);
    }

    /**
     * Hinzufügen eines Artikels:
     * Sendet dem Server eine addArticle-Anfrage mit den entsprechenden Daten und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion und die nötigen Werte hinetrlegt sind.
     *
     * @param name          Artikelbezeichnung
     * @param stock         Lagerbestand
     * @param price         Preis
     * @param packagingUnit Verpackungseinheit
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> addArticle(String name, String stock, String price, String packagingUnit) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.ADD_ARTICLE, new String[]{name, stock, price, packagingUnit});
        return sendToServerAndGetVoidResult(clientRequest);
    }

    /**
     * Entfernen eines Artikels:
     * Sendet dem Server eine removeArticle-Anfrage mit den entsprechenden Daten und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion und die nötigen Werte hinetrlegt sind.
     *
     * @param article Das Objekt des Artikel der entfernt werden soll.
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> removeArticle(Article article) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.REMOVE_ARTICLE, new String[]{Integer.toString(article.getArticleNumber())});
        return sendToServerAndGetVoidResult(clientRequest);
    }

    /**
     * Hinzufügen eines Artiekls in den Einkaufswagen:
     * Sendet dem Server eine addArticleToShoppingCart-Anfrage mit den entsprechenden Daten und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion und die nötigen Werte hinetrlegt sind.
     *
     * @param article          Der Artikel der dem Einkaufswagen hinzugefügt werden soll
     * @param numberOfArticles Die Anzahl des Artikels
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> addArticleToShoppingCart(Article article, String numberOfArticles) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.ADD_ARTICLE_TO_SHOPPINGCART, new String[]{Integer.toString(article.getArticleNumber()), numberOfArticles});
        return sendToServerAndGetVoidResult(clientRequest);
    }

    /**
     * Entfernen eines Artikels aus dem Einkaufswagen:
     * Sendet dem Server eine removeArticleFromShoppingCart-Anfrage mit den entsprechenden Daten und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion und die nötigen Werte hinetrlegt sind.
     *
     * @param article Der Artikel der entfernt werden soll
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> removeArticleFromShoppingCart(Article article) {
        ClientRequest clientRequest = new ClientRequest(ClientAction.REMOVE_ARTICLE_FROM_SHOPPINGCART, new String[]{Integer.toString(article.getArticleNumber())});
        return sendToServerAndGetVoidResult(clientRequest);
    }

    /**
     * Kaufen der Artikel im Einkaufswagen:
     * Sendet dem Server eine buyShoppingCart-Anfrage und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion hinterlegt ist.
     *
     * @return Gibt ein Result<Bill>-Objekt zurück, die getObject()-Methode gibt die Rechnung zurück
     */
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

    /**
     * Leeren des Einkaufswagens:
     * Sendet dem Server eine clearShoppingCart-Anfrage und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion hinterlegt ist.
     *
     * @return Gibt ein Result<Void>-Objekt zurück
     */
    public Result<Void> clearShoppingCart() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.CLEAR_SHOPPINGCART, null);
        return sendToServerAndGetVoidResult(clientRequest);
    }

    /**
     * Liste der ShoppingCartItems:
     * Sendet dem Server eine getShoppingCartItemList-Anfrage und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion hinterlegt ist.
     *
     * @return Gibt ein Result<ArrayList<ShoppingCartItem>>-Objekt zurück, die getObject()-Methode
     * gibt eine ArrayList mit ShoppingCartItem-Objekten zurück.
     */
    public Result<ArrayList<ShoppingCartItem>> getShoppingCartItemList() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.GET_SHOPPINGCART, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<ArrayList<ShoppingCartItem>>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            Result<ArrayList<ShoppingCartItem>> result = (Result<ArrayList<ShoppingCartItem>>) receiveResult.getObject();
            return result;
        }
    }

    /**
     * Liste der Artikel:
     * Sendet dem Server eine getArticleList-Anfrage und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion hinterlegt ist.
     *
     * @return Gibt ein Result<ArrayList<Article>>-Objekt zurück, die getObject()-Methode
     * gibt eine ArrayList mit Article-Objekten zurück.
     */
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

    /**
     * Liste der Mitarbeiter:
     * Sendet dem Server eine getEmployeeList-Anfrage und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion hinterlegt ist.
     *
     * @return Gibt ein Result<ArrayList<Employee>>-Objekt zurück, die getObject()-Methode
     * gibt eine ArrayList mit Employee-Objekten zurück.
     */
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

    /**
     * Liste der Events (Änderungsprotokoll):
     * Sendet dem Server eine getEventList-Anfrage und empfängt das Ergebnis.
     * Gesendet wird ein ClientRequest-Objekt in dessen die Aktion hinterlegt ist.
     *
     * @return Gibt ein Result<ArrayList<Event>>-Objekt zurück, die getObject()-Methode
     * gibt eine ArrayList mit Event-Objekten zurück.
     */
    public Result<ArrayList<Event>> getEventList() {
        ClientRequest clientRequest = new ClientRequest(ClientAction.GET_EVENT_LIST, null);
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<ArrayList<Event>>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<ArrayList<Event>>) receiveResult.getObject();
        }
    }

    /**
     * Diese Methode sendet ein ClientRequest an den Server und empfängt ein Result<Void>-Objekt.
     * Da der Server an sehr vielen Stellen ein solches Result<Void>-Objekt zurückgibt,
     * wurde dafür diese Mehode erstellt, um doppelten Quellcode zu vermeiden.
     *
     * @param clientRequest Das ClientRequest das an den Server gesndet werden soll
     * @return Gibt ein Result<Void>-Objekt zurück.
     */
    private Result<Void> sendToServerAndGetVoidResult(ClientRequest clientRequest) {
        sendToServer(clientRequest);

        Result<Object> receiveResult = receiveFromServer();
        if (receiveResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, receiveResult.getMessage(), null);
        } else {
            return (Result<Void>) receiveResult.getObject();
        }
    }
}
