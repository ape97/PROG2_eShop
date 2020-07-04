package Model;

/**
 * Objekte dieser Klassen bilden die Mitarbeiter ab.
 * Diese Objekte werden zum Speichern serialisiert.
 * Erbt von der abstrakten Klasse Person.
 */
public class Employee extends Person {

    public Employee(String firstname, String lastname, int id, String username, String password) {
        super(firstname, lastname, id, username, password);
    }
}
