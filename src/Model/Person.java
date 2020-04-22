/**
 * Klasse: Person
 * Dateiname: Person.java
 * Erstellt am: 22.04.2020
 * Erstellt von: Adrian Peters
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

    public String get_firstname() {
        return _firstname;
    }

    public void set_firstname(String firstname) {
        _firstname = firstname;
    }

    public String get_lastname() {
        return _lastname;
    }

    public void set_lastname(String lastname) {
        _lastname = lastname;
    }

    public int get_id() {
        return _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String username) {
        _username = username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String password) {
        _password = password;
    }
}