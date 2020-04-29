package View.CUI;

public class CUI {
    public CUI() {}

    public static void showEmployeeMenu(){
        System.out.println("Sie sind als Mitarbeiter angemeldet.");
        System.out.println("Optionen: ");
        System.out.println("Artikel erstellen: 'n'");
        System.out.println("Artikelbestand bearbeiten: 'b'");
        System.out.println("Artikel anzeigen: 's'");
        System.out.println("Neuen Mitarbeiter anlegen: 'm'");
        System.out.println("Beenden: 'q'");
        System.out.println("-------------------------------");
        System.out.flush();
    }


    public static void subMenu(){
        System.out.println("Optionen:");
        System.out.println("Drücken Sie 's' um zu starten!");
        System.out.println("Hinweis: Nachfolgendes Menü kann nur verlassen werden, wenn alle Punkte ausgefüllt wurden sind!");
        System.out.println("Verlassen: 'q'");
        System.out.println("-------------------------------");
        System.out.flush();
    }

    public static void showAddArticleMenu(){
        System.out.println("Artikel erstellen");
        subMenu();

    }

    public static void showChangeArticleMenu(){
        System.out.println("Artikelbestand bearbeiten");
        subMenu();
    }

    public static void showAddEmployeeMenu(){
        System.out.println("Neuen Mitarbeiter anlegen");
        subMenu();
    }

    public static void showArticleListMenu(){
        System.out.println("Artikel anzeigen");
        System.out.println("Optionen:");
        System.out.println("Artikel nach Nummer sortieren: 'n'");
        System.out.println("Artikel nach Bezeichnung sortieren: 'b'");
        System.out.println("Verlassen: 'q'");
        System.out.println("-------------------------------");
        System.out.flush();

    }

    public static void showArticleListSubMenu(){
        System.out.println("Artikel anzeigen");
        System.out.println("Optionen:");
        System.out.println("Artikel in den Warenkorb legen: 'k'");
        System.out.println("Verlassen: 'q'");
        System.out.println("-------------------------------");
        System.out.flush();

    }



    public static void showCustomerMenu(){
        System.out.println("Sie sind als Kunde angemeldet.");
        System.out.println("Optionen: ");
        System.out.println("Artikel anzeigen: 's'");
        System.out.println("Warenkorb anzeigen: 'w'");
        System.out.println("Beenden: 'q'");
        System.out.println("-------------------------------");
        System.out.flush();
    }

    public static void showShoppingCartMenu(){
        System.out.println("Warenkorb anzeigen");
        System.out.println("Optionen:");
        System.out.println("Warenkorb bearbeiten: 's'");
        System.out.println("Kaufen: 'k'");
        System.out.println("Verlassen: 'q'");
        System.out.println("-------------------------------");
        System.out.println("Warenkorb:");
        //Warenkorb ausgeben
        System.out.println("-------------------------------");

    }
}
