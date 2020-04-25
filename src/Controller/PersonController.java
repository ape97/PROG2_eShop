package Controller;

import Model.Address;
import Model.Customer;
import Model.Employee;
import Model.Person;
import Utilities.BooleanString;
import Utilities.BooleanStringObject;

import java.util.ArrayList;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Person-Objekte.
 */
public class PersonController {

    private ArrayList<Person> _personList;

    /**
     * Der Konstruktor erzeugt eine leere ArrayList _personList für Person-Objekte.
     * Es wird ein Person-Objekt vom Typ Employee hinzugefügt, welche den erstmaligen Zugriff auf das Programm ermöglicht.
     * username: admin und password: admin können zum anmelden verwendet werden. (root-Benutzer)
     */
    public PersonController() {
        _personList = new ArrayList<>();
        _personList.add(new Employee("admin", "admin", 0, "admin", "admin"));
    }

    /**
     * Neuer Mitarbeiter:
     * Prüft die Parameter und erzeugt ein neues Employee-Objekt und fügt dieses der ArrayList _personList hinzu.
     *
     * @param firstname Der Vorname des Mitarbeiters
     * @param lastname  Der Nachname des Mitarbeiters
     * @param username  Der Benutzername des Mitarbeiters
     * @param password  Das Passwort des Mitarbeiters
     * @return Gibt ein BooleanString-Objekt zurück.
     * Der boolean gibt an, ob das erzeugen des Employee-Objekts erfolgreich war oder nicht.
     * Der String gibt die entsprechende Meldung an z.B. Benutzername darf nicht leer sein.
     */
    public BooleanString addEmployee(String firstname, String lastname, String username, String password) {

        BooleanString booleanStringResult = new BooleanString(false, "");
        BooleanString personValuesValid = checkPersonValuesValid(firstname, lastname, username, password);

        if (!personValuesValid.getValueB()) {
            booleanStringResult.setValueS(personValuesValid.getValueS());
        } else {
            Employee employee = new Employee(firstname, lastname, generatePersonId(), username, password);
            _personList.add(employee);

            booleanStringResult.setValueB(true);
            booleanStringResult.setValueS("Mitarbeiter wurde erstellt.");
        }

        return booleanStringResult;
    }

    /**
     * Neuer Kunde:
     * Prüft die Parameter und erzeugt ein neues Customer-Objekt und fügt dieses der ArrayList _personList hinzu.
     *
     * @param firstname Der Vorname des Kunden
     * @param lastname  Der Nachname des Kunden
     * @param username  Der Benutzername des Kunden
     * @param password  Das Passwort des Kunden
     * @param address   Das Address-Objekt des Kunden, welches die Adressdaten enthält
     * @return Gibt ein BooleanString-Objekt zurück.
     * Der boolean gibt an, ob das erzeugen des Customer-Objekts erfolgreich war oder nicht.
     * Der String gibt die entsprechende Meldung an z.B. Benutzername darf nicht leer sein.
     */
    public BooleanString addCustomer(String firstname, String lastname, String username, String password, Address address) {
        BooleanString booleanStringResult = new BooleanString(false, "");
        BooleanString personValuesValid = checkPersonValuesValid(firstname, lastname, username, password);

        if (!personValuesValid.getValueB()) {
            booleanStringResult.setValueS(personValuesValid.getValueS());
        } else {
            Customer customer = new Customer(firstname, lastname, generatePersonId(), username, password, address);
            _personList.add(customer);

            booleanStringResult.setValueB(true);
            booleanStringResult.setValueS("Kunde wurde erstellt.");
        }

        return booleanStringResult;
    }

    /**
     * Login:
     * Prüft ob die Anmeldeinformationen auf ein Person-Objekt zutreffen.
     * @param username Der Benutzername
     * @param password Das Passwort
     * @return Gibt ein BooleanStringObject-Objekt zurück.
     * Der boolean gibt an, ob der Login erfolgreich war.
     * Der String gibt die entsprechende Meldung zur Anzeige zurück.
     * Das Object gibt den angemeldeten Benutzer Person-Object zurück, sofern der Vorgang erfolgreich war, ansonsten null.
     */
    public BooleanStringObject login(String username, String password) {
        BooleanStringObject booleanStringObjectResult = new BooleanStringObject(false, "Login nicht erfolgreich. Passwort oder Benutzername ist falsch.", null);

        for (Person person : _personList) {
            if (person.get_username().equals(username) && person.get_password().equals(password)) {
                booleanStringObjectResult.setValueB(true);
                booleanStringObjectResult.setValueS("Login erfolgreich.");
                booleanStringObjectResult.setValueO(person);
                break;
            }
        }

        return booleanStringObjectResult;
    }

    /**
     * Prüft ob die Person-Konstrukor relevanten Parameter den Anforderungen entsprechen.
     * @param firstname Der Vorname der Person
     * @param lastname Der Nachname der Person
     * @param username Der Benutzername der Person
     * @param password Das Passwort der Person
     * @return Gibt ein BooleanSTring-Objekt zurück.
     * Der boolean gibt an, ob die angegebenen Werte den Anforderungen entsprechen und somit gültig sind oder nicht.
     * Der String gibt die entsprechende Meldung an z.B. welcher Wert nicht gültig ist den Grund dafür.
     */
    private BooleanString checkPersonValuesValid(String firstname, String lastname, String username, String password) {
        BooleanString booleanStringResult = new BooleanString(false, "");

        if (firstname.trim().isEmpty()) {
            booleanStringResult.setValueS("Vorname darf nicht leer sein.");
        } else if (lastname.trim().isEmpty()) {
            booleanStringResult.setValueS("Nachname darf nicht leer sein.");
        } else if (checkUsernameExists(username)) {
            booleanStringResult.setValueS("Benutzername existiert bereits.");
        } else if (!checkUsernameIsValid(username)) {
            booleanStringResult.setValueS("Benutzername ist ungültig. (nicht leer, keine Leerzeichen, mindestens 3 Zeichen)");
        } else if (!checkPasswordIsValid(password)) {
            booleanStringResult.setValueS("Passwort ist ungültig. (nicht leer, mindestens 8 Zeichen)");
        } else {
            booleanStringResult.setValueB(true);
        }

        return booleanStringResult;
    }

    /**
     * Prüft ob der angegebene Benutzername bereits einem Person-Objekt zugeordnet ist.
     * @param username Der Benutzername der gesucht werden soll
     * @return Gibt einen boolean zurück.
     * true -> Benutzername ist bereits vergeben
     * false -> Benutzername ist noch nicht vergeben
     */
    private boolean checkUsernameExists(String username) {
        boolean result = false;

        for (Person person : _personList) {
            if (person.get_username().equals(username)) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Prüft ob der angegebene Benutzername den Anforderungen entspricht und somit gültig ist oder nicht.
     * @param username Der Benutzername, welcher geprüft werden soll
     * @return Gibt einen boolean zurück.
     * true -> Benutzername entspricht den Anforderungen, also gültig
     * false -> Benutzername entspricht nicht den Anforderungen, also ungültig
     */
    private boolean checkUsernameIsValid(String username) {
        boolean result = true;

        // nicht leer, keine Leerzeichen, mindestens 3 Zeichen
        if (username.trim().isEmpty() || username.contains(" ") || username.length() < 3) {
            result = false;
        }

        return result;
    }

    /**
     * Prüft ob das angegebene Passwort den Anforderungen entspricht und somit gültig ist oder nicht.
     * @param password Das Passwort, welches geprüft werden soll
     * @return Gibt einen boolean zurück.
     * true -> Passwort entspricht den Anforderungen, also gültig
     * false -> Passwort entspricht nicht den Anforderungen, also ungültig
     */
    private boolean checkPasswordIsValid(String password) {
        // TODO: nicht leer, mindestens 8 Zeichen, 1 Sonderzeichen, 1 Zahl, 1 Buchstabe
        boolean result = true;

        // nicht leer, mindestens 8 Zeichen
        if (password.trim().isEmpty() || password.length() < 8) {
            result = false;
        }
        return result;
    }

    /**
     * Generiert eine neue Identifikationsnummer Id für ein neues Person-Objekt.
     * @return Gibt die neue Id als int zurück.
     * Die neue Id ist immer die größte Id die in einem Person-Objekt gefunden wurde + 1
     */
    private int generatePersonId() {
        int id = 0;
        for (Person person : _personList) {
            if (person.get_id() >= id) {
                id = person.get_id() + 1;
            }
        }
        return id;
    }
}
