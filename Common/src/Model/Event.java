/**
 * Info:
 * Dient dem Aufbau eines Änderungsprotokolls
 * Event bezieht sich auf eine Änderung des Lagerbestands eines Artikels
 * Festgehalten wird der Zeitstempel, der entsprechende Artikel und die positive oder negative Änderung des Bestands
 **/

package Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Objekte dieser Klassen bilden die Events ab. (Eintrag im Änderungsprotokoll)
 * Diese Objekte werden zum Speichern serialisiert.
 */
public class Event implements Serializable {
    private Date _timeStamp; // Zeitstempel
    private int _stockChangeValue; // Summer der Bestandsveränderung (negativ oder positiv)

    // Person wird bewusst nicht referenziert, weil bei Löschung der referenzierten Person, würden die Daten inkonsisten werden
    private String _personFirstname;
    private String _personLastname;
    private int _personId;

    // Artikel wird bewusst nicht referenziert, weil bei Löschung des referenzierten Artikels, würden die Daten inkonsisten werden
    private String _articleName;
    private int _articleNumber;

    public Event(Article article, Person person, int changeValue) {
        _timeStamp = new Date();
        _articleName = article.getName();
        _articleNumber = article.getArticleNumber();
        _personId = person.getId();
        _personFirstname = person.getFirstname();
        _personLastname = person.getLastname();
        _stockChangeValue = changeValue;
    }

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
