package View.CUI;

import Controller.MainController;
import Utilities.ArticleSortMode;
import Utilities.BooleanString;
import Utilities.BooleanStringObject;
import Utilities.PersonType;

import java.util.Scanner;

public class CUIController {

    private MainController _mainController;

    public CUIController(MainController mainController) {
        _mainController = mainController;
    }

    public void runMainMenu() {
        CUI.showMainMenu();
        String inputdata = readInput();
        processinputMainMenu(inputdata);
    }

    public void processinputMainMenu(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Beenden
                exit();
                break;
            case "o": //Login
                login();
                break;
            case "r": //Registrieren
                // register();
                break;
        }
    }

    public void exit() {
        return;
    }

    /**
     * Employee
     **/
    public void runEmployeeMenu() {
        CUI.showEmployeeMenu();
        String inputdata = readInput();
        processinputEmployee(inputdata);
    }


    public void runAddArticleMenu() {
        CUI.showAddArticleMenu();
        String inputdata = readInput();
        processinputAddArticle(inputdata);
    }

    public void runChangeArticleMenu() {
        CUI.showChangeArticleMenu();
        String inputdata = readInput();
        processinputChangeArticle(inputdata);

    }

    public void runAddEmployeeMenu() {
        CUI.showAddEmployeeMenu();
        String inputdata = readInput();
        processinputAddEmployee(inputdata);

    }

    public void runArticleListEMenu() {
        CUI.showArticleListMenu();
        String inputdata = readInput();
        processinputArticleListE(inputdata);

    }


    public void addArticleSubMenu() {
        String name;
        int articleNumber;
        int stock;
        double price;
        System.out.println("Bitte geben Sie die Aritkelnummer ein:");
        articleNumber = readInt();
        System.out.println("Bitte geben Sie die Artiekbezeichnung ein:");
        name = readInput();
        System.out.println("Bitte geben Sie die Bestandsmenge ein:");
        stock = readInt();
        System.out.println("Bitte geben Sie den Preis ein:");
        price = readDouble();

        BooleanString booleanStringResult = _mainController.addArticle(name, articleNumber, stock, price);

        if (booleanStringResult.getValueB()) {
            // Vorgang erfolgreich
            // AUsgabe:
            booleanStringResult.getValueS();
            runAddArticleMenu();
        } else {
            // Vorgang nicht erfolgreich
            // Ausgabe:
            booleanStringResult.getValueS();

            // VORGANG ERNEUT STARTEN
            runAddArticleMenu();
        }


    }

    public void addEmployeeMenu() {
        String firstname;
        String lastname;
        String username;
        String password;
        System.out.println("Bitte geben Sie einen Vornamen ein:");
        firstname = readInput();
        System.out.println("Bitte geben Sie einen Nachnamen ein:");
        lastname = readInput();
        System.out.println("Bitte geben Sie einen Benutzernamen ein:");
        username = readInput();
        System.out.println("Bitte geben Sie ein Passwort ein:");
        password = readInput();

        BooleanString booleanStringResult = _mainController.addEmployee(firstname, lastname, username, password);

        if (booleanStringResult.getValueB()) {
            // Vorgang erfolgreich
            // AUsgabe:
            booleanStringResult.getValueS();
            runAddEmployeeMenu();
        } else {
            // Vorgang nicht erfolgreich
            // Ausgabe:
            booleanStringResult.getValueS();

            // VORGANG ERNEUT STARTEN
            runAddEmployeeMenu();
        }


    }


    public void processinputEmployee(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Beenden
                exit();
            case "n": //Artikel hinzufügen
                runAddArticleMenu();
                break;
            case "b": //Artikel bearbeiten
                runChangeArticleMenu();
                break;
            case "s": //Arikel anzeigen
                runArticleListEMenu();
                break;
            case "m": //Mitarbeiter anlegen
                runAddEmployeeMenu();
                break;
            case "o":
                ;//Logout
                break;

        }
    }

    public void processinputAddArticle(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runEmployeeMenu();
                break;
            case "s":
                addArticleSubMenu();
                break;

        }
    }

    public void processinputChangeArticle(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runEmployeeMenu();
            case "s":
                ;//Funktion Artikel ändern
                break;

        }
    }

    public void processinputAddEmployee(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runEmployeeMenu();
            case "s":
                addEmployeeMenu();
                break;

        }

    }

    public void processinputArticleListE(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runEmployeeMenu();
            case "n":
                ; //Funktion Artikel anzeigen sortiert nach Artikelnummer
                break;
            case "b":
                ; //Funktion Artikel anzeigen sortiert nach Artikelbezeichnung
                break;

        }

    }


    /**
     * Customer
     **/

    public void runCustomerMenu() {
        CUI.showCustomerMenu();
        String inputdata = readInput();
        processinputCustomer(inputdata);

    }

    public void runShoppingCartMenu() {
        CUI.showCustomerMenu();
        String inputdata = readInput();
        processinputCustomer(inputdata);

    }

    public void runArticleListCMenu() {
        CUI.showArticleListMenu();
        String inputdata = "main_" + readInput();
        processinputArticleListC(inputdata);

    }

    public void runArticleListCSubMenu() {
        CUI.showArticleListSubMenu();
        String inputdata = "sub_" + readInput();
        processinputArticleListC(inputdata);

    }


    public void processinputCustomer(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Beenden
                exit();
            case "s": //Artikel Liste anzeigen
                runArticleListCMenu();
                break;

            case "w":
                System.out.println("Test erfolgreich!");
                break;
            case "o": //Logout
                ;
                break;

        }
    }


    public void processinputArticleListC(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "main_q": //Verlassen
                runEmployeeMenu();
            case "main_n":
                runArticleListCSubMenu(); //Funktion Artikel anzeigen sortiert nach Artikelnummer
                break;
            case "main_b":
                runArticleListCSubMenu(); //Funktion Artikel anzeigen sortiert nach Artikelbezeichnung
                break;
            case "sub_q": //Verlassen
                runArticleListCMenu();
            case "sub_k":
                ; //Funktion Artikel in den Warenkorb
                break;


        }

    }

    public void register() {
        //TODO
    }

    public void login() {

        String user;
        String password;
        System.out.println("Login:");
        System.out.println("Bitte den Benutzernamen eingeben.");
        user = readInput();
        System.out.println("Bitte Passwort eingeben.");
        password = readInput();

        BooleanStringObject booleanStringObjectResult = _mainController.login(user, password);

        if (booleanStringObjectResult.getValueB()) {

            System.out.println(booleanStringObjectResult.getValueS());
            PersonType personType = (PersonType) booleanStringObjectResult.getValueO();

            if (personType == PersonType.Customer) {
                runCustomerMenu();
            } else {
                runEmployeeMenu();
            }

        } else {
            System.out.print(booleanStringObjectResult.getValueS());
            login();
        }
    }


    public String readInput() {
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        return in;
    }

    public int readInt() {
        Scanner inputInt = new Scanner(System.in);
        int input = inputInt.nextInt();
        return input;
    }

    public double readDouble() {
        Scanner inputDouble = new Scanner(System.in);
        double input = inputDouble.nextDouble();
        return input;
    }

}
