package View;

import Controller.MainController;
import Model.Person;
import Utilities.BooleanString;
import Utilities.BooleanStringObject;
import Utilities.PersonType;

public class ConsoleTest {

    private MainController _mainController;

    public ConsoleTest(){
        _mainController = new MainController();
    }

    public void start(){

        // Neuer Artikel erstellen
        String name;
        int articleNumber;
        int stock;
        double price;

        // Geben Sie den Namen ein:
        name = "ArtikelXY"; // Eingabe von Benutzer
        articleNumber = 1; // Eingabe von Benutzer
        stock = 11; // Eingabe von Benutzer
        price = 10; // Eingabe von Benutzer

        BooleanString booleanStringResult = _mainController.addArticle(name, articleNumber, stock, price);

        if(booleanStringResult.getValueB()){
            // Vorgang erfolgreich
            // AUsgabe:
            booleanStringResult.getValueS();
        } else{
            // Vorgang nicht erfolgreich
            // Ausgabe:
            booleanStringResult.getValueS();

            // VORGANG ERNEUT STARTEN
            start();
        }

        // Menü anzeigen




        /// BENUTZER BEISPIEL
        BooleanStringObject booleanStringObjectResult = new BooleanStringObject(true, "", null);

        if(booleanStringObjectResult.getValueB()){
            //LOGIN ERFOLGREICH
            // MEDLUNG AUSGEBEN
            PersonType personType = (PersonType) booleanStringObjectResult.getValueO();

            if(personType == PersonType.Customer){
                // CUSTOMER MENU
            } else{
                // EMPLOYEE MENU
            }


        }

        // WICHTIG NEU


    }
}
