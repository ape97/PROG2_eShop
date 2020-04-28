/**
 * Info:
 * Dient als Basis-Klasse für Mitarbeiter (Employee) und Kunden (Customer)
 * Enthält alle Keerndaten einer Person, dient lediglich der Datenkapselung
 **/

package Model;

public abstract class Person {
    private String _firstname; // Vorname
    private String _lastname; // Nachname
    private int _id; // Eindeutige Identifikationsnummer
    private String _username; // Benutzername zum LogIn
    private String _password; // Kennwort zum LogIn

    public Person(String firstname, String lastname, int id, String username, String password) {
        _firstname = firstname;
        _lastname = lastname;
        _id = id;
        _username = username;
        _password = password;
    }

    public String getFirstname() {
        return _firstname;
    }

    public void setFirstname(String firstname) {
        _firstname = firstname;
    }

    public String getLastname() {
        return _lastname;
    }

    public void setLastname(String lastname) {
        _lastname = lastname;
    }

    public int getId() {
        return _id;
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }
}