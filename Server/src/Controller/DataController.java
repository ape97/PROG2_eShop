package Controller;

import Data.DataWriterReader;

import Model.*;
import Utilities.Result;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Die Klasse DataController implementiert das SingeltonPattern, für einen Zugriff auf das selbe Objekt und somit
 * auf die selben Daten in dem gesamten Server-Projekt.
 * (Die einzelnen Threads für die Clients arbeiten somit auf dem sekben Datenbestand.)
 * Der DataController enthält alle relevanten Daten die gespeichert (serialisiert) werden sollen.
 */
public class DataController implements Serializable {

    private static DataController _instance;  // Singelton: Statsiches eindeutiges Objekt der Klasse

    private ArrayList<Article> _articleList; // Liste der Artikel
    private ArrayList<Event> _eventList; // Liste der Events (Änderungsprotokoll)
    private ArrayList<Person> _personList; // Liste der Personen

    /**
     * Singelton: private, damit nur intern ein Objekt erzeugt werden kann
     */
    private DataController() {
        _articleList = new ArrayList<>();
        _eventList = new ArrayList<>();
        _personList = new ArrayList<>();

        // ERZEUGT DEN ADMIN, SOWIE EINEN KUNDEN
        _personList.add(new Employee("admin", "admin", 0, "admin", "admin"));
        _personList.add(new Customer("kunde", "kunde", 99, "kunde", "kunde", new Address("Straße", "1", "29292", "Dorf")));
    }

    /**
     * Singelton:
     * Ermöglicht den Zugriff auf das Singelton DataController-Objekt.
     * Durch Prüfung auf ein existierendes Objekt, wird eins erstellt oder das vorhandene zurückgegeben.
     *
     * @return Gibt die einzigartige Instanz von DataController zurück
     */
    public static DataController getInstance() {
        if (_instance == null) {
            _instance = new DataController();
        }

        return _instance;
    }

    /**
     * Lädt mithilfe von DataWriterReader die Daten ein.
     * Statisch, damit die Daten geladen werden können, bevor ein Objekt von DataController existiert,
     * weil es der DataController ist, der ggf. geladen wird.
     * <p>
     * War das Laden erfolgreich, wird die Instanz des DataControllers auf das geladene Objekt referenziert.
     *
     * @return Gibt den Erfolgsstatus der Aktion als Result<Void> zurück.
     */
    public static Result<Void> loadData() {
        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");

        Result<Object> loadDataResult = dataWriterReader.load();
        if (loadDataResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, loadDataResult.getMessage(), null);
        }

        _instance = (DataController) loadDataResult.getObject();
        return new Result<Void>(Result.State.SUCCESSFULL, loadDataResult.getMessage(), null);
    }

    /**
     * Speichert mithilfe von DataWriterReader die Daten.
     * Dafür wird dem DataWriterReader die Instanz (im Prinzip: this) des DataControllers übergeben, da dieser alle daten enthält.
     *
     * @return Gibt den Erfolgsstatus der Aktion als Result<Void> zurück.
     */
    public Result<Void> saveData() {
        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
        Result<Void> saveDataResult = dataWriterReader.save(_instance);
        return saveDataResult;
    }

    public ArrayList<Article> getArticleList() {
        return _articleList;
    }

    public ArrayList<Event> getEventList() {
        return _eventList;
    }

    public ArrayList<Person> getPersonList() {
        return _personList;
    }
}
