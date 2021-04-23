package Utilities;

import java.io.Serializable;

/**
 * Diese genrische Klasse dient der Weiterreichung von Informationen und Daten.
 * Außerdem ist diese Klasse Bestandteil des Fehlerhandlings in Client, Server und Common.
 *
 * Ein Objekt dieser Klasse besitzt einen State (Status), welcher aussagt, ob eine Aktion erfolgreich war oder nicht.
 * Desweiteren gibt es eine Message (Nachricht) als String, welche passend zum Status i.d.R. eine aussagekräftige
 * Information über den Erfolg oder den Fehler enthält.
 * Zuletzt Besitzt das Result ein generischen Typen, welcher ein beliebiges Objekt sein kann, welches zusaätzlich
 * übermittelt werden soll.
 *
 * Anwendungsbeispiel:
 * GUI ruft die Login-Funktion auf: foo.login(username, password);
 * Logik prüft die Daten und stellt fest, dass es keinen Benutzer zu den angegeben Daten gibt:
 * Logik: return new Result-Void (FAILED, "Anmeldedaten falsch!", null);
 * Oder die Daten sind korrekt und es hat sich ein Kunde angemeldet:
 * Logik: return new Result-PersonType (SUCCESSFUL, "Willkommen zurück.", PersonType.Customer)
 *
 * Wissenswertes:
 * Result-Objekte werden ebenfalls für die Server zum Client Kommunikation verwendet.
 * Der Server antwortet auf jede Anfrage des Clients mit einem Result.
 *
 * @param <T> Das Objekt, welches mit dem Result übergeben werden soll z.B. Void oder Integer
 */
public class Result<T> implements Serializable {
    private State _state;
    private String _message;
    private T _object;

    public Result(State state, String message, T object) {
        _state = state;
        _message = message;
        _object = object;
    }

    public State getState() {
        return _state;
    }

    public void setState(State state) {
        _state = state;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public T getObject() {
        return _object;
    }

    public void setObject(T object) {
        _object = object;
    }

    public enum State {
        SUCCESSFUL,
        FAILED
    }
}