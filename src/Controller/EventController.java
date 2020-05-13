package Controller;

import Model.Article;
import Model.Event;
import Model.Person;
import Utilities.ArticleSortMode;
import Utilities.BooleanString;
import Utilities.EventSortMode;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Event-Objekte.
 */
public class EventController implements Serializable {
    private ArrayList<Event> _eventList;

    public EventController() {
        _eventList = new ArrayList<>();
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
        _eventList.add ( event);
    }

    public String getEventsString() {
        sortEvents(EventSortMode.EventDate);
        String result = "";

        for (Event event : _eventList) {
            result += event.toString() + "\n";
        }

        return result;
    }


    /**
     * Sortiert die Events nach dem angegebenen Schema
     *
     * @param eventSortMode Das angegebene Schema nachdem sortiert werden soll
     */
    private void sortEvents(EventSortMode eventSortMode) {
        Collections.sort(_eventList, new EventController.EventComparator(eventSortMode));
    }

    /**
     * Ein Comparator für Event-Objekte, damit diese nach ihren Eigenschaften sortiert werden können
     * Quelle: // https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
     */
    private class EventComparator implements Comparator<Event> {
        private EventSortMode _eventSortMode;

        /**
         * Im Konstruktor wird das Schema, der SortMode als Member gesetzt
         *
         * @param eventSortMode Das Schema nachdem sortiert werden soll
         */
        public EventComparator(EventSortMode eventSortMode) {
            _eventSortMode = eventSortMode;
        }

        /**
         * Je nachdem welcher SortMode in _eventSortMode festgelegt ist,
         * werden die Events anders verglichen.
         *
         * @param o1 Event 1 zum vergleichen
         * @param o2 Event 2 zum vergleichen
         * @return https://www.javatpoint.com/java-string-compareto
         */
        @Override
        public int compare(Event o1, Event o2) {
            int result;

            switch (_eventSortMode) {
                case EventDate:
                    result = o1.getTimeStamp().compareTo(o2.getTimeStamp());
                    break;
                default:
                    throw new InvalidParameterException();
            }
            return result;
        }
    }
}
