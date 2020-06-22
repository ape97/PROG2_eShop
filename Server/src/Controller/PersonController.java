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
 */
public class PersonController implements Serializable {

   // private ArrayList<Person> _personList;
    //private transient ObservableList<Person> _personObservableList;

    //private transient ObservableList<Employee> _employeeObservableList;
    //private transient ObservableList<Customer> _customerObservableList;

    private void refreshEmployeeAndCustomerObservableList() {

//       if(_employeeObservableList == null){
//           ArrayList<Employee> employeeList = new ArrayList<>();
//           _employeeObservableList = FXCollections.observableList(employeeList);
//       }
//
//       if(_customerObservableList == null){
//           ArrayList<Customer> customerList = new ArrayList<>();
//           _customerObservableList = FXCollections.observableList(customerList);
//       }
//
//        _employeeObservableList.clear();
//        _customerObservableList.clear();
//
//        for (Person person : _personObservableList) {
//            if (getPersonTypeByPerson(person) == PersonType.Employee) {
//                _employeeObservableList.add((Employee) person);
//            } else {
//                _customerObservableList.add((Customer) person);
//            }
//        }

        //_employeeObservableList = FXCollections.observableList(employeeList);
        //_customerObservableList = FXCollections.observableList(customerList);
    }

    private Person _registeredPerson; // Angemeldete Person

    /**
     * Der Konstruktor erzeugt eine leere ArrayList _personList für Person-Objekte.
     * Es wird ein Person-Objekt vom Typ Employee hinzugefügt, welche den erstmaligen Zugriff auf das Programm ermöglicht.
     * username: admin und password: admin können zum anmelden verwendet werden. (root-Benutzer)
     */
    public PersonController() {
       // _personList = new ArrayList<>();
        //_personObservableList = FXCollections.observableList(_personList);

        DataController.getInstance().getPersonList().add(new Employee("admin", "admin", 0, "admin", "admin"));
        DataController.getInstance().getPersonList().add(new Customer("kunde", "kunde", 99, "kunde", "kunde", new Address("", "", "", "")));

//        _personObservableList.addListener(new ListChangeListener<Person>() {
//            @Override
//            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Person> c) {
//                refreshEmployeeAndCustomerObservableList();
//            }
//        });

        refreshEmployeeAndCustomerObservableList();
    }


    public void InitAfterSerialization(){
//        _personObservableList = FXCollections.observableList(_personList);
//        _personObservableList.addListener(new ListChangeListener<Person>() {
//            @Override
//            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Person> c) {
//                refreshEmployeeAndCustomerObservableList();
//            }
//        });
//
//
//
//        refreshEmployeeAndCustomerObservableList();
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
     * Der String gibt die entsprechende (Fehler-) Meldung an.
     */
    public Result addEmployee(String firstname, String lastname, String username, String password) {

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

    public Result<Void> editEmployee(Employee employee, String firstname, String lastname, String username, String password) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);
        Result<Void> personValuesValidResult = checkPersonValuesValid(employee, firstname, lastname, username, password);

        if (personValuesValidResult.getState() == Result.State.FAILED) {
            result.setMessage(personValuesValidResult.getMessage());
        } else {
            employee.setFirstname(firstname);
            employee.setLastname(lastname);
            employee.setUsername(username);
            employee.setPassword(password);

            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_EmployeeEdited));
        }

        return result;
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
     * Der String gibt die entsprechende (Fehler-) Meldung an.
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

    public Result<Void> editCustomer(Customer customer, String firstname, String lastname, String username, String password, Address address) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);
        Result<Void> personValuesValidResult = checkPersonValuesValid(customer, firstname, lastname, username, password);

        if (personValuesValidResult.getState() == Result.State.FAILED) {
            result.setMessage(personValuesValidResult.getMessage());
        } else {
            customer.setFirstname(firstname);
            customer.setLastname(lastname);
            customer.setUsername(username);
            customer.setPassword(password);
            customer.setAddress(address);

            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_CustomerEdited));
        }

        return result;
    }


    public Result<Void> removePerson(int personID) {
        Person person = getPersonById(personID);
        if(person != null){
            DataController.getInstance().getPersonList().remove(person);
            return new Result<Void>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_PersonRemoved), null);
        } else{
            return new Result<Void>(Result.State.FAILED, "Keine Person mit dieser ID gefunden.", null);
        }
    }

    /**
     * Login:
     * Prüft ob die Anmeldeinformationen auf ein Person-Objekt zutreffen.
     *
     * @param username Der Benutzername
     * @param password Das Passwort
     * @return Gibt ein BooleanStringObject-Objekt zurück.
     * Der boolean gibt an, ob der Login erfolgreich war.
     * Der String gibt die entsprechende (Fehler-) Meldung an.
     * Das Object gibt den angemeldeten Benutzer Person-Object zurück, sofern der Vorgang erfolgreich war, ansonsten null.
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
     * @return Gibt ein BooleanSTring-Objekt zurück.
     * Der boolean gibt an, ob die angegebenen Werte den Anforderungen entsprechen und somit gültig sind oder nicht.
     * Der String gibt die entsprechende (Fehler-) Meldung an.
     */
    private Result<Void> checkPersonValuesValid(String firstname, String lastname, String username, String password) {
        return checkPersonValuesValid(null, firstname, lastname, username, password);
    }

    private Result<Void> checkPersonValuesValid(Person person, String firstname, String lastname, String username, String password) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);

        if (firstname.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_FirstNameNotEmpty));
        } else if (lastname.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_LastNameNotEmpty));
        } else if ((person == null || ( person != null && person.getUsername() != username)) && checkUsernameExists(username)) { // der username nicht geändert hat, dann muss er auch nicht geprüft werden
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

    private Person getPersonById(int personID){
        Person person = null;

        for (Person curPerson : DataController.getInstance().getPersonList()) {
            if (curPerson.getId() == personID) {
                person = curPerson;
            }
        }

        return person;
    }

    public void logout() {
        _registeredPerson = null;
    }

    public ArrayList<Person> getPersonList() {
        return DataController.getInstance().getPersonList();
    }

    public ArrayList<Employee> getEmployeeList(){
      ArrayList<Employee> employeeList = new ArrayList<>();
        for (Person person : DataController.getInstance().getPersonList()) {
            if (getPersonTypeByPerson(person) == PersonType.Employee) {
                employeeList.add((Employee) person);
            }
        }
        return employeeList;
    }

    public ArrayList<Customer> getCustomerList(){
        ArrayList<Customer> customerList = new ArrayList<>();
        for (Person person : DataController.getInstance().getPersonList()) {
            if (getPersonTypeByPerson(person) == PersonType.Customer) {
                customerList.add((Customer) person);
            }
        }
        return customerList;
    }

//    public ObservableList<Employee> getEmployeeList() {
//        return _employeeObservableList;
//    }
//
//    public ObservableList<Customer> getCustomerList() {
//        return _customerObservableList;
//    }

    public Person getRegisteredPerson() {
        return _registeredPerson;
    }
}
