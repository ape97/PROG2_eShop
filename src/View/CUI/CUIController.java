package View.CUI;

import Controller.MainController;
import Model.Bill;
import Model.Person;
import Utilities.ArticleSortMode;
import Utilities.PersonType;
import Utilities.Result;

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
                register();
                break;
            default: //Default Case
                runMainMenu();
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
        int packageUnit;
        System.out.println("Bitte geben Sie die Aritkelnummer ein:");
        articleNumber = readInt();
        System.out.println("Bitte geben Sie die Artikelbezeichnung ein:");
        name = readInput();
        System.out.println("Bitte geben Sie die Verpackungseinheit ein:");
        packageUnit = readInt();
        System.out.println("Bitte geben Sie die Bestandsmenge ein:");
        stock = readInt();
        System.out.println("Bitte geben Sie den Preis ein:");
        price = readDouble();

        Result<Void> result = _mainController.addArticle(name, articleNumber, stock, price, packageUnit);

        if (result.getState() == Result.State.SUCCESSFULL) {
            System.out.println(result.getMessage());
            runAddArticleMenu();
        } else {
            System.out.println(result.getMessage());
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

        Result<Void> result = _mainController.addEmployee(firstname, lastname, username, password);

        if (result.getState() == Result.State.SUCCESSFULL) {
            // Vorgang erfolgreich
            // AUsgabe:
            System.out.println(result.getMessage());
            runAddEmployeeMenu();
        } else {
            // Vorgang nicht erfolgreich
            // Ausgabe:
            System.out.println(result.getMessage());

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
                break;
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
                _mainController.logout();//Logout
                break;
            case "e": //Event Protokoll
                eventProtocol();
                break;
            default:
                runEmployeeMenu();
                break;

        }
    }

    public void processinputEventProtocol(String inputdata){
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata){
            case "q"://Menü verlassen
                runEmployeeMenu();
                break;
            default:
                eventProtocol();
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
            default:
                runAddArticleMenu();
                break;

        }
    }

    public void processinputChangeArticle(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runEmployeeMenu();
                break;
            case "s":
                updateStock();//Funktion Artikel ändern
                break;
            default:
                runChangeArticleMenu();
                break;


        }
    }

    public void processinputAddEmployee(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runEmployeeMenu();
                break;
            case "s":
                addEmployeeMenu();
                break;
            default:
                runAddEmployeeMenu();
                break;


        }

    }

    public void processinputArticleListE(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runEmployeeMenu();
                break;
            case "n":
                ; //Funktion Artikel anzeigen sortiert nach Artikelnummer
                articleListByNumber();
                break;
            case "b":
                //Funktion Artikel anzeigen sortiert nach Artikelbezeichnug
                articleListByName();
                break;


            default:
                runArticleListEMenu();
                break;


        }

    }

    public void processinputArticleListESub(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runArticleListEMenu();
                break;
            default:
                runArticleListEMenu();
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
        CUI.showShoppingCartMenu();

        Result<Void> result = _mainController.getShoppingCartString();

        if (result.getState() == Result.State.SUCCESSFULL) {
            String shoppingCartList = (String) result.getMessage();
            System.out.println(shoppingCartList);
        } else {
            System.out.print(result.getMessage());
            runMainMenu();
        }

        String inputdata = readInput();
        processinputShoppingCart(inputdata);

    }

    public void runArticleListCMenu() {
        CUI.showArticleListMenu();
        String inputdata = readInput();
        processinputArticleListC(inputdata);

    }

    public void runArticleListCSubMenuByName() {

        String list = _mainController.getSortedArticleStringList(ArticleSortMode.ArticleName);
        CUI.showArticleListSubMenu();
        System.out.println(list);

        String inputdata = readInput();
        processinputArticleListCSub(inputdata);

    }

    public void runArticleListCSubMenuByNumber() {

        String list = _mainController.getSortedArticleStringList(ArticleSortMode.ArticleNumber);
        CUI.showArticleListSubMenu();
        System.out.println(list);

        String inputdata = readInput();
        processinputArticleListCSub(inputdata);

    }


    public void processinputCustomer(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Beenden
                exit();
                break;
            case "s": //Artikel Liste anzeigen
                runArticleListCMenu();
                break;

            case "w":
                runShoppingCartMenu();
                break;
            case "o": //Logout
                _mainController.logout();
                break;
            default:
                runCustomerMenu();
                break;


        }
    }

    public void processinputShoppingCart(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Beenden
                runCustomerMenu();
                break;
            case "k": //Kaufen
                buy();
                break;
            case "s": //Artikel hinzugügen
                addArticleToShoppingCart();
                break;
            case "c": //Arikel leeren
                clearShoppingCart();
                break;
            default:
                runShoppingCartMenu();
                break;


        }
    }


    public void processinputArticleListC(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {
            case "q": //Verlassen
                runCustomerMenu();
                break;
            case "n":
                runArticleListCSubMenuByNumber(); //Funktion Artikel anzeigen sortiert nach Artikelnummer
                break;
            case "b":
                runArticleListCSubMenuByName(); //Funktion Artikel anzeigen sortiert nach Artikelbezeichnung
                break;

            default:
                runArticleListCMenu();
                break;



        }

    }

    public void processinputArticleListCSub(String inputdata) {
        System.out.println("Eingabe: " + inputdata);
        System.out.println("-------------------------------");
        switch (inputdata) {

            case "q": //Verlassen
                runArticleListCMenu();
                break;
            case "k":
                addArticleToShoppingCart(); //Funktion Artikel in den Warenkorb
                break;
            default:
                runArticleListCMenu();
                break;



        }

    }

    public void addArticleToShoppingCart() {
        int articlenumber;
        int numberOfArticle;

        System.out.println("Bitte geben Sie die Artikelnummer ein.");
        articlenumber = readInt();
        System.out.println("Bitte geben Sie die Anzahl an Artikel ein.");
        numberOfArticle = readInt();


        Result<Void> result = _mainController.addArticleToShoppingCart(articlenumber, numberOfArticle);

        if (result.getState()== Result.State.SUCCESSFULL) {
            System.out.println(result.getMessage());
            runShoppingCartMenu();
        } else {
            System.out.println(result.getMessage());
            runShoppingCartMenu();
        }

    }

    public void updateStock() {
        int articleNumber;
        int stockChangeValue;

        System.out.println("Bitte geben Sie die Artikelnummern ein.");
        articleNumber = readInt();
        System.out.println("Bitte geben die Bestandsänderung ein.");
        stockChangeValue = readInt();

        Result<Void> result = _mainController.updateStock(articleNumber, stockChangeValue);

        System.out.println(result.getMessage());

        if (result.getState() == Result.State.SUCCESSFULL) {
            runChangeArticleMenu();
        } else {
            runChangeArticleMenu();
        }


    }

    public void buy() {
        Result<Bill> result = _mainController.buyShoppingCart();

        if (result.getState() == Result.State.SUCCESSFULL) {
            Bill bill = result.getObject();
            System.out.println(result.getMessage());
            System.out.println(bill);
            runShoppingCartMenu();

        } else {
            System.out.println(result.getMessage());
            runShoppingCartMenu();
        }
    }


    public void register() {
        String firstname;
        String lastname;
        String username;
        String password;
        String street;
        String housenumber;
        String postcode;
        String city;

        System.out.println("Registrieren:");
        System.out.println("Bitte geben Sie Ihren Vornamen ein.");
        firstname = readInput();
        System.out.println("Bitte geben Sie Ihren Nachnamen ein.");
        lastname = readInput();
        System.out.println("Bitte geben Sie einen Benutzernamen ein.");
        username = readInput();
        System.out.println("Bitte geben Sie ein Passwort ein.");
        password = readInput();
        System.out.println("Bitte geben Sie Ihre Straße ein.");
        street = readInput();
        System.out.println("Bitte geben Sie Ihre Hausnummer ein.");
        housenumber = readInput();
        System.out.println("Bitte geben Sie eine Postleitzahl ein.");
        postcode = readInput();
        System.out.println("Bitte geben Sie den Ort ein.");
        city = readInput();

        Result result = _mainController.addCustomer(firstname, lastname, username, password, street, housenumber, postcode, city);

        if (result.getState() == Result.State.SUCCESSFULL) {

            System.out.println(result.getMessage());
            runMainMenu();
        } else {
            System.out.println(result.getMessage());
            runMainMenu();
        }
        //TODO
    }

    public void articleListByName() {
        String inputdata;

        String list = _mainController.getSortedArticleStringList(ArticleSortMode.ArticleName);
        CUI.showArticleList();
        System.out.println(list);

        inputdata = readInput();
        processinputArticleListESub(inputdata);
    }

    public void articleListByNumber() {
        String inputdata;

        String list = _mainController.getSortedArticleStringList(ArticleSortMode.ArticleNumber);
        CUI.showArticleList();
        System.out.println(list);

        inputdata = readInput();
        processinputArticleListESub(inputdata);

    }

    public void eventProtocol() {
        Result<Void> result = _mainController.getEventsString();
        String inputdata;
        if (result.getState() == Result.State.SUCCESSFULL) {
            System.out.println(result.getMessage());

        } else {
            System.out.println(result.getMessage());
            runCustomerMenu();
        }
        System.out.println("Drücke 'q' um zurück zu kehren.");
        inputdata = readInput();
        processinputEventProtocol(inputdata);
    }

    public void login() {

        String user;
        String password;
        System.out.println("Login:");
        System.out.println("Bitte den Benutzernamen eingeben.");
        user = readInput();
        System.out.println("Bitte Passwort eingeben.");
        password = readInput();

        Result<PersonType> result = _mainController.login(user, password);

        if (result.getState() == Result.State.SUCCESSFULL) {

            System.out.println(result.getMessage());
            PersonType personType = (PersonType) result.getObject();

            if (personType == PersonType.Customer) {
                runCustomerMenu();
            } else {
                runEmployeeMenu();
            }
        } else {
            System.out.print(result.getMessage());
            runMainMenu();
        }
    }


    public String readInput() {
        Scanner input = new Scanner(System.in);
        String in;
        try{
            in = input.nextLine();
        }catch(Exception stringerror){
            System.out.println("Eingabe ungültig! Bitte erneut versuchen.");
            in = readInput();
        }
        return in;
    }

    public int readInt() {
        Scanner inputInt = new Scanner(System.in);
        int input;
        try{
            input = inputInt.nextInt();
        } catch(Exception interror){
            System.out.println("Eingabe ungültig! Bitte erneut versuchen.");
            input = readInt();
        }
        return input;
    }

    public double readDouble() {
        Scanner inputDouble = new Scanner(System.in);
        double input;
        try{
            input =  inputDouble.nextDouble();
        } catch (Exception doubleerror){
            System.out.println("Eingabe ungültig! Bitte erneut versuchen.");
            input = readDouble();
        }
        return input;
    }

    public void clearShoppingCart(){
        Result<Void> result = _mainController.clearShoppingCart();
        System.out.println(result.getMessage());
        runShoppingCartMenu();
    }
}
