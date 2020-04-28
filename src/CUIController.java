import Controller.ArticleController;
import Controller.MainController;
import Utilities.BooleanString;
import Utilities.BooleanStringObject;
import Utilities.PersonType;

import java.io.IOException;
import java.util.Scanner;
public class CUIController {

    private static MainController _mainController;

    public CUIController(){
        this._mainController = new MainController();
    }

    /**Employee**/
    public static void runEmployeeMenu(){
        CUI.showEmployeeMenu();
        String inputdata = readInput();
        processinputEmployee(inputdata);
    }


    public static void runAddArticleMenu(){
        CUI.showAddArticleMenu();
        String inputdata = readInput();
        processinputAddArticle(inputdata);
    }

    public static void runChangeArticleMenu(){
        CUI.showChangeArticleMenu();
        String inputdata = readInput();
        processinputChangeArticle(inputdata);

    }

    public static void runAddEmployeeMenu(){
        CUI.showAddEmployeeMenu();
        String inputdata = readInput();
        processinputAddEmployee(inputdata);

    }

    public static void runArticleListEMenu(){
        CUI.showArticleListMenu();
        String inputdata = readInput();
        processinputArticleListE(inputdata);

    }



    public static void addArticleSubMenu(){
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

        if(booleanStringResult.getValueB()){
            // Vorgang erfolgreich
            // AUsgabe:
            booleanStringResult.getValueS();
            runAddArticleMenu();
        } else{
            // Vorgang nicht erfolgreich
            // Ausgabe:
            booleanStringResult.getValueS();

            // VORGANG ERNEUT STARTEN
            runAddArticleMenu();
        }


    }

    public static void addEmployeeMenu(){
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

        if(booleanStringResult.getValueB()){
            // Vorgang erfolgreich
            // AUsgabe:
            booleanStringResult.getValueS();
            runAddEmployeeMenu();
        } else{
            // Vorgang nicht erfolgreich
            // Ausgabe:
            booleanStringResult.getValueS();

            // VORGANG ERNEUT STARTEN
            runAddEmployeeMenu();
        }


    }


    public static void processinputEmployee(String inputdata){
        System.out.println("Eingabe: "+inputdata);
        System.out.println("-------------------------------");
        switch(inputdata){
            case "q": //Beenden
                System.exit(0);
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

        }
    }

    public static void processinputAddArticle(String inputdata){
        System.out.println("Eingabe: "+inputdata);
        System.out.println("-------------------------------");
        switch(inputdata){
            case "q": //Verlassen
                runEmployeeMenu();
                break;
            case "s":
                addArticleSubMenu();
                break;

        }
    }

    public static void processinputChangeArticle(String inputdata){
        System.out.println("Eingabe: "+inputdata);
        System.out.println("-------------------------------");
        switch(inputdata){
            case "q": //Verlassen
                runEmployeeMenu();
            case "s":
                ;//Funktion Artikel ändern
                break;

        }
    }

    public static void processinputAddEmployee(String inputdata){
        System.out.println("Eingabe: "+inputdata);
        System.out.println("-------------------------------");
        switch(inputdata){
            case "q": //Verlassen
                runEmployeeMenu();
            case "s":
                addEmployeeMenu();
                break;

        }

    }

    public static void processinputArticleListE(String inputdata){
        System.out.println("Eingabe: "+inputdata);
        System.out.println("-------------------------------");
        switch(inputdata){
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







    /**Customer**/

    public static void runCustomerMenu(){
        CUI.showCustomerMenu();
        String inputdata = readInput();
        processinputCustomer(inputdata);

    }

    public static void runShoppingCartMenu(){
        CUI.showCustomerMenu();
        String inputdata = readInput();
        processinputCustomer(inputdata);

    }

    public static void runArticleListCMenu(){
        CUI.showArticleListMenu();
        String inputdata = "main_"+readInput();
        processinputArticleListC(inputdata);

    }

    public static void runArticleListCSubMenu(){
        CUI.showArticleListSubMenu();
        String inputdata = "sub_"+readInput();
        processinputArticleListC(inputdata);

    }




    public static void processinputCustomer(String inputdata){
        System.out.println("Eingabe: "+inputdata);
        System.out.println("-------------------------------");
        switch(inputdata){
            case "q": //Beenden
                System.exit(0);
            case "s": //Artikel Liste anzeigen
                runArticleListCMenu();
                break;

            case "w":
                System.out.println("Test erfolgreich!");
                ;
                break;

        }
    }


    public static void processinputArticleListC(String inputdata){
        System.out.println("Eingabe: "+inputdata);
        System.out.println("-------------------------------");
        switch(inputdata){
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






    public void login(){

        String user;
        String password;
        System.out.println("Login:");
        System.out.println("Bitte den Benutzernamen eingeben.");
        user = readInput();
        System.out.println("Bitte Passwort eingeben.");
        password = readInput();

        BooleanStringObject booleanStringObjectResult = _mainController.login(user, password);

        if(booleanStringObjectResult.getValueB()){

            System.out.println("Login erfolgreich!");

            PersonType personType = (PersonType) booleanStringObjectResult.getValueO();

            if(personType == PersonType.Customer){
                CUI.showCustomerMenu();
            } else{
                CUI.showEmployeeMenu();
            }


        }else{
            System.out.print("Login nicht erfolgreich! Bitte erneut versuchen.");
            login();
        }
    }




    public static String readInput(){
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        return in;
    }

    public static int readInt(){
        Scanner inputInt = new Scanner(System.in);
        int input = inputInt.nextInt();
        return input;
    }

    public static double readDouble(){
        Scanner inputDouble = new Scanner(System.in);
        double input = inputDouble.nextDouble();
        return input;
    }

}
