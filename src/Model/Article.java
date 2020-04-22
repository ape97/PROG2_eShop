/**
 * Klasse: Article
 * Dateiname: Article.java
 * Erstellt am: 22.04.2020
 * Erstellt von: Adrian Peters
 *
 * Info:
 * Enthält die wesentlichen Daten eines Artikels, dient lediglich der Datenkapselung
 **/

package Model;

public class Article {
    private String _name; // Artikel-Bezeichnung
    private int _articleNumber; // Artikel-Nummer
    private int _stock; // Lagerbestand/Anzahl
    private double _price;  // Verkaufspreis

    public Article(String name, int articleNumber, int stock, double price) {
        _name = name;
        _articleNumber = articleNumber;
        _stock = stock;
        _price = price;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        _name = name;
    }

    public int get_articleNumber() {
        return _articleNumber;
    }

    public int get_stock() {
        return _stock;
    }

    public void set_stock(int stock) {
        _stock = stock;
    }

    public double get_price() {
        return _price;
    }

    public void set_price(double price) {
        _price = price;
    }
}
