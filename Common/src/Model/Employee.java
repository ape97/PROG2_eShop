/**
 *  Info:
 *  Enthält die wesentlichen Daten eines Mitarbeiters, dient lediglich der Datenkapselung
 *  Wird von Person abgeleitet
 *  Durch den eigenen Typ Employee können Mitarbeiter von Kunden unterschieden werden
 **/

package Model;

public class Employee extends Person {

    public Employee(String firstname, String lastname, int id, String username, String password) {
        super(firstname, lastname, id, username, password);
    }
}
