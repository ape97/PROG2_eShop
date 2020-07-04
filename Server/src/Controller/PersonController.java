package Controller;

import Model.Address;
import Model.Customer;
import Model.Employee;
import Model.Person;
import Utilities.Message;
import Utilities.PersonType;
import Utilities.Result;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Person-Objekte.
 * Hinweis: Für jeden Verbundenen Client gibt es ein eigenes PersonController-Objekt.
 */
public class PersonController {

    private Person _registeredPerson; // Angemeldete Person

    public PersonController() {
    }

    /**
     * Neuer Mitarbeiter:
     * Prüft die Parameter und erzeugt ein neues Employee-Objekt und fügt diese den Daten hinzu.
     *
     * @param firstname Der Vorname des Mitarbeiters
     * @param lastname  Der Nachname des Mitarbeiters
     * @param username  Der Benutzername des Mitarbeiters
     * @param password  Das Passwort des Mitarbeiters
     * @return Gibt ein Result<Void> zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    public Result<Void> addEmployee(String firstname, String lastname, String username, String password) {

        Result<Void> result = new Result(Result.State.FAILED, "", null);
        Result<Void> personValuesValidResult = checkPersonValuesValid(firstname, lastname, username, password);

        if (personValuesValidResult.getState() == Result.State.FAILED) {
            result.setMessage(personValuesValidResult.getMessage());
        } else {
            Employee employee = new Employee(firstname, lastname, generatePersonId(), username, password);
            DataController.getInstance().getPersonList().add(employee);

            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_EmployeeCreated));
        }

        return result;
    }

    /**
     * Neuer Kunde:
     * Prüft die Parameter und erzeugt ein neues Customer-Objekt und fügt diese den Daten hinzu.
     *
     * @param firstname Der Vorname des Kunden
     * @param lastname  Der Nachname des Kunden
     * @param username  Der Benutzername des Kunden
     * @param password  Das Passwort des Kunden
     * @param address   Das Address-Objekt des Kunden, welches die Adressdaten enthält
     * @return Gibt ein Result<Void> zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    public Result<Void> addCustomer(String firstname, String lastname, String username, String password, Address address) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);
        Result<Void> personValuesValidResult = checkPersonValuesValid(firstname, lastname, username, password);

        if (personValuesValidResult.getState() == Result.State.FAILED) {
            result.setMessage(personValuesValidResult.getMessage());
        } else {
            Customer customer = new Customer(firstname, lastname, generatePersonId(), username, password, address);
            DataController.getInstance().getPersonList().add(customer);

            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_CustomerCreated));
        }

        return result;
    }

    /**
     * Person löschen:
     * Löscht die Person mit der angegebenen ID aus den Daten.
     *
     * @param personID ID der Person die gelöscht werden soll
     * @return Gibt ein Result<Void> zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    public Result<Void> removePerson(int personID) {
        Person person = getPersonById(personID);
        if (person != null) {
            DataController.getInstance().getPersonList().remove(person);
            return new Result<Void>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_PersonRemoved), null);
        } else {
            return new Result<Void>(Result.State.FAILED, "Keine Person mit dieser ID gefunden.", null);
        }
    }

    /**
     * Login:
     * Prüft ob die Anmeldeinformationen auf ein Person-Objekt zutreffen.
     *
     * @param username Der Benutzername
     * @param password Das Passwort
     * @return Gibt ein Result<PersonType> zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     * Der PersonType gibt an, ob es sich bei der angemeldeten Person um einen Kunden oder Mitarbeiter handelt.
     */
    public Result<PersonType> login(String username, String password) {
        Result<PersonType> result = new Result<PersonType>(Result.State.FAILED, Message.get(Message.MessageType.Error_LoginFailed), null);

        for (Person person : DataController.getInstance().getPersonList()) {
            if (person.getUsername().equals(username) && person.getPassword().equals(password)) {
                _registeredPerson = person;
                result.setState(Result.State.SUCCESSFULL);
                result.setMessage(Message.get(Message.MessageType.Info_LoginSuccess));
                result.setObject(getRegisteredPersonType());
                break;
            }
        }

        return result;
    }

    /**
     * Prüft ob die Person-Konstrukor relevanten Parameter den Anforderungen entsprechen.
     *
     * @param firstname Der Vorname der Person
     * @param lastname  Der Nachname der Person
     * @param username  Der Benutzername der Person
     * @param password  Das Passwort der Person
     * @return Gibt ein Result<Void> zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    private Result<Void> checkPersonValuesValid(String firstname, String lastname, String username, String password) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);

        if (firstname.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_FirstNameNotEmpty));
        } else if (lastname.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_LastNameNotEmpty));
        } else if (checkUsernameExists(username)) {
            result.setMessage(Message.get(Message.MessageType.Error_UsernameExists));
        } else if (!checkUsernameIsValid(username)) {
            result.setMessage(Message.get(Message.MessageType.Error_UsernameInvalid));
        } else if (!checkPasswordIsValid(password)) {
            result.setMessage(Message.get(Message.MessageType.Error_PasswordInvalid));
        } else {
            result.setState(Result.State.SUCCESSFULL);
        }

        return result;
    }

    /**
     * Prüft ob der angegebene Benutzername bereits einem Person-Objekt zugeordnet ist.
     *
     * @param username Der Benutzername der gesucht wird
     * @return Gibt einen boolean zurück.
     * true -> Benutzername ist bereits vergeben
     * false -> Benutzername ist noch nicht vergeben
     */
    private boolean checkUsernameExists(String username) {
        boolean result = false;

        for (Person person : DataController.getInstance().getPersonList()) {
            if (person.getUsername().equals(username)) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Prüft ob der angegebene Benutzername den Anforderungen entspricht und somit gültig ist oder nicht.
     *
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
     *
     * @param password Das Passwort, welches geprüft werden soll
     * @return Gibt einen boolean zurück.
     * true -> Passwort entspricht den Anforderungen, also gültig
     * false -> Passwort entspricht nicht den Anforderungen, also ungültig
     */
    private boolean checkPasswordIsValid(String password) {
        boolean result = true;

        // nicht leer, mindestens 8 Zeichen
        if (password.trim().isEmpty() || password.length() < 8) {
            result = false;
        }
        return result;
    }

    /**
     * Generiert eine neue Identifikationsnummer Id für ein neues Person-Objekt.
     *
     * @return Gibt die neue Id als int zurück.
     * Die neue Id ist immer die größte Id die in einem Person-Objekt gefunden wurde + 1
     */
    private int generatePersonId() {
        int id = 0;
        for (Person person : DataController.getInstance().getPersonList()) {
            if (person.getId() >= id) {
                id = person.getId() + 1;
            }
        }
        return id;
    }

    public PersonType getRegisteredPersonType() {
        return getPersonTypeByPerson(_registeredPerson);
    }

    /**
     * Ermittelt den tatsächlichen Typen eines Person-Objektes, Customer oder Employee
     *
     * @param person Das Person-Objekt, dessen Typ ermittelt werden soll
     * @return Gibt den Typen in Form des PersonType enums zurück
     */
    private PersonType getPersonTypeByPerson(Person person) {
        PersonType personTypeResult;

        if (person == null) {
            personTypeResult = PersonType.Guest;
        } else {
            String className = person.getClass().getSimpleName();

            if (className.equals(Customer.class.getSimpleName())) {
                personTypeResult = PersonType.Customer;
            } else { // Employee
                personTypeResult = PersonType.Employee;
            }
        }

        return personTypeResult;
    }

    /**
     * Ermittelt das Person-Objekt, welches der übergebenen ID zugeordnet ist
     * @param personID Die eindeutige ID der Person
     * @return Die Person mit der entsprechenden ID, null falls diese nicht extistiert
     */
    private Person getPersonById(int personID) {
        Person person = null;

        for (Person curPerson : DataController.getInstance().getPersonList()) {
            if (curPerson.getId() == personID) {
                person = curPerson;
            }
        }

        return person;
    }

    /**
     * Setzt den Wert der angemeldeten Person auf null, was dazu führt, dass die Rechte für jegliche Aktionen erlischen.
     */
    public void logout() {
        _registeredPerson = null;
    }

    /**
     * Ermittelt aus den Personen-Daten alle Mitarbeiter und gibt diese als Liste zurück.
     * @return Liste aller Mitarbeiter
     */
    public ArrayList<Employee> getEmployeeList() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        for (Person person : DataController.getInstance().getPersonList()) {
            if (getPersonTypeByPerson(person) == PersonType.Employee) {
                employeeList.add((Employee) person);
            }
        }
        return employeeList;
    }

    /**
     * Ermittelt aus den Personen-Daten alle Kunden und gibt diese als Liste zurück.
     * @return Liste aller Kunden
     */
    public ArrayList<Customer> getCustomerList() {
        ArrayList<Customer> customerList = new ArrayList<>();
        for (Person person : DataController.getInstance().getPersonList()) {
            if (getPersonTypeByPerson(person) == PersonType.Customer) {
                customerList.add((Customer) person);
            }
        }
        return customerList;
    }


    public Person getRegisteredPerson() {
        return _registeredPerson;
    }
}
