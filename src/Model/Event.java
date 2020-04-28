/**
 * Info:
 * Dient dem Aufbau eines Änderungsprotokolls
 * Event bezieht sich auf eine Änderung des Lagerbestands eines Artikels
 * Festgehalten wird der Zeitstempel, der entsprechende Artikel und die positive oder negative Änderung des Bestands
 **/

package Model;

import java.util.Date;

public class Event {
    private Date _timeStamp; // Zeitstempel
    private Article _article; // Der betreffende Artiekl
    private Person _person; // Person die für die Änderung des Lagerbestandes verantwortlich ist (Kunde beim Kauf, Mitarbeiter beim Buchen neuer Bestände)
    private int _stockChangeValue; // Summer der Bestandsveränderung (negativ oder positiv)

    public Event(Article article, Person person, int changeValue) {
        _timeStamp = new Date();
        _article = article;
        _person = person;
        _stockChangeValue = changeValue;
    }

    public Date getTimeStamp() {
        return _timeStamp;
    }

    public Article getArticle() {
        return _article;
    }

    public Person getPerson() {
        return _person;
    }

    public int getStockChangeValue() {
        return _stockChangeValue;
    }
}
