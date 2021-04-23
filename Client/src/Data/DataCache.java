package Data;

import Communication.ClientController;
import Model.*;
import Utilities.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Die Klasse DataCache implementiert das Singleton-Pattern, für einen Zugriff auf das selbe Objekt in dem
 * gesamten Client-Projekt.
 * Der DataCache enthält die für die Anzeige relevanten Daten und kann diese bei Bedarf aktualisieren.
 *
 * Singleton wurde implementiert, weil diese Daten in mehreren Klassen in Verwendung sind und es zu
 * unübersichtlich werden würde, wenn man anderen Objekten Referenzen über Konstruktoren/Methoden auf ein
 * Objekt von DataCache geben würde. Statisch wurde nicht verwendet, da sich bewusst für die Verwendung eines
 * Konstruktors entschieden wurde, im Zusammenhang mit der Prüfung auf die Instanz.
 */
public class DataCache {

    private static DataCache _instance; // Singleton: Statisches eindeutiges Objekt der Klasse

    private ObservableList<Article> _articleList; // Die Artikel-Liste
    private ObservableList<Employee> _employeeList; // Die Mitarbeiter-Liste
    private ObservableList<Event> _eventList; // Die Event-Liste
    private ObservableList<ShoppingCartItem> _shoppingCartItemList; // Die Liste der Artikel im Einkaufswagen

    /**
     * Singleton: private, damit nur intern ein Objekt erzeugt werden kann
     */
    private DataCache() {
        _articleList = FXCollections.observableArrayList(new ArrayList<>());
        _employeeList = FXCollections.observableList(new ArrayList<>());
        _eventList = FXCollections.observableList(new ArrayList<>());
        _shoppingCartItemList = FXCollections.observableList(new ArrayList<>());
    }

    /**
     * Singleton:
     * Ermöglicht den Zugriff auf das Singleton DataCache-Objekt.
     * Durch Prüfung auf ein existierendes Objekt, wird eins erstellt oder das vorhandene zurückgegeben.
     *
     * @return Gibt die einzigartige Instanz von DataCache zurück.
     */
    public static DataCache getInstance() {
        if (_instance == null) {
            _instance = new DataCache();
        }
        return _instance;
    }

    /**
     * Aktualisiert die Artikel-Daten und gibt die Liste zurück.
     * @return Die Artikel-Liste ObservableList-Article
     */
    public ObservableList<Article> getArticleList() {
        refreshArticleList();
        return _articleList;
    }

    /**
     * Aktualisiert die Mitarbeiter-Daten und gibt die Liste zurück.
     * @return Die Mitarbeiter-Liste
     */
    public ObservableList<Employee> getEmployeeList() {
        refreshEmployeeList();
        return _employeeList;
    }

    /**
     * Aktualisiert die ShoppingCartItem-Daten und gibt die Liste zurück.
     * @return Die ShoppingCartItem-Liste
     */
    public ObservableList<ShoppingCartItem> getShoppingCartItemList() {
        refreshShoppingCartItemList();
        return _shoppingCartItemList;
    }

    /**
     * Aktualisiert die Event-Daten und gibt die Liste zurück.
     * @return Die Event-Liste
     */
    public ObservableList<Event> getEventList() {
        refreshEventList();
        return _eventList;
    }

    /**
     * Aktualisiert die Artikel-Daten bzw. die Liste.
     * Dafür werden die alten Daten entfernt, die neuen angefordert und in die ObservableList eingefügt.
     * @return Gibt ein Result-Void zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    public Result<Void> refreshArticleList() {
        Result<ArrayList<Article>> result = ClientController.getInstance().getArticleList();
        if (result.getState() == Result.State.SUCCESSFUL) {
            _articleList.clear();
            for (Article article : result.getObject()) {
                _articleList.add(article);
            }
        }
        return new Result<Void>(result.getState(), result.getMessage(), null);
    }

    /**
     * Aktualisiert die Mitarbeiter-Daten bzw. die Liste.
     * Dafür werden die alten Daten entfernt, die neuen angefordert und in die ObservableList eingefügt.
     * @return Gibt ein Result-Void zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    public Result<Void> refreshEmployeeList() {
        Result<ArrayList<Employee>> result = ClientController.getInstance().getEmployeeList();
        if (result.getState() == Result.State.SUCCESSFUL) {
            _employeeList.clear();
            for (Employee employee : result.getObject()) {
                _employeeList.add(employee);
            }

        }
        return new Result<Void>(result.getState(), result.getMessage(), null);
    }

    /**
     * Aktualisiert die Event-Daten bzw. die Liste.
     * Dafür werden die alten Daten entfernt, die neuen angefordert und in die ObservableList eingefügt.
     * @return Gibt ein Result-Void zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    public Result<Void> refreshEventList() {
        Result<ArrayList<Event>> result = ClientController.getInstance().getEventList();
        if (result.getState() == Result.State.SUCCESSFUL) {
            _eventList.clear();
            for (Event event : result.getObject()) {
                _eventList.add(event);
            }

        }
        return new Result<Void>(result.getState(), result.getMessage(), null);
    }

    /**
     * Aktualisiert die ShoppingCartItem-Daten bzw. die Liste.
     * Dafür werden die alten Daten entfernt, die neuen angefordert und in die ObservableList eingefügt.
     * @return Gibt ein Result-Void zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    public Result<Void> refreshShoppingCartItemList() {
        Result<ArrayList<ShoppingCartItem>> result = ClientController.getInstance().getShoppingCartItemList();
        if (result.getState() == Result.State.SUCCESSFUL) {
            _shoppingCartItemList.clear();
            for (ShoppingCartItem shoppingCartItem : result.getObject()) {
                _shoppingCartItemList.add(shoppingCartItem);
            }
        }
        return new Result<Void>(result.getState(), result.getMessage(), null);
    }
}