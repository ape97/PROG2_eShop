package Controller;

import Model.Article;
import Model.Event;
import Model.Person;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Event-Objekte.
 */
public class EventController {

    public EventController() {
    }

    /**
     * Neues Event:
     * Prüft die Parameter und erzeugt ein neues Event-Objekt und fügt dieses der ArrayList _eventList hinzu.
     *
     * @param article          Der Artikel dessen Bestand sich verändert hat
     * @param person           Die Person die den Bestand verändert hat (Kunde/Mitarbeiter)
     * @param stockChangeValue Die Summe um die der Bestand verändert wurde
     */
    public void addEvent(Article article, Person person, int stockChangeValue) {
        Event event = new Event(article, person, stockChangeValue);
        DataController.getInstance().getEventList().add(event);
    }

    public ArrayList<Event> getEventList() {
        return DataController.getInstance().getEventList();
    }
}
