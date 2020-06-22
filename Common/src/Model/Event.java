/**
 * Info:
 * Dient dem Aufbau eines Änderungsprotokolls
 * Event bezieht sich auf eine Änderung des Lagerbestands eines Artikels
 * Festgehalten wird der Zeitstempel, der entsprechende Artikel und die positive oder negative Änderung des Bestands
 **/

package Model;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private Date _timeStamp; // Zeitstempel
    //private Article _article; // Der betreffende Artiekl
   // private Person _person; // Person die für die Änderung des Lagerbestandes verantwortlich ist (Kunde beim Kauf, Mitarbeiter beim Buchen neuer Bestände)
    private int _stockChangeValue; // Summer der Bestandsveränderung (negativ oder positiv)

    // Person wird nicht referenziert, weil TODO
    private String _personFirstname;
    private String _personLastname;
    private int _personId;

    // Artikel wird nicht referenziert, falls dieser gelöscht wird, führt das zu inkonsistenten Daten
    private String _articleName;
    private int _articleNumber;

    public Event(Article article, Person person, int changeValue) {
        _timeStamp = new Date();
        //_article = article;
        _articleName = article.getName();
        _articleNumber = article.getArticleNumber();
        _personId = person.getId();
        _personFirstname = person.getFirstname();
        _personLastname = person.getLastname();
        _stockChangeValue = changeValue;
    }

/*    public String toString() {
        String stockText = "eingelagert";

        if (_stockChangeValue < 0) {
            stockText = "verkauft";
        }

        return "Zeitstempel: " + _timeStamp + " - " + " Bestandsveränderung: " + _stockChangeValue +
                " Artikel:" + _articleName + " " + stockText + " Artikelnummer:" + _articleNumber +
                " - " + " Person: " + _personFirstname+ " " + _personLastname;
    }*/

    public Date getTimeStamp() {
        return _timeStamp;
    }

    public String getArticleName() {
        return _articleName;
    }

    public int getArticleNumber() {
        return _articleNumber;
    }

    public int getPersonId() {
        return _personId;
    }

    public String getPersonFirstname() {
        return _personFirstname;
    }

    public String getPersonLastname() {
        return _personLastname;
    }

    public int getStockChangeValue() {
        return _stockChangeValue;
    }
}
