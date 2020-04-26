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

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getArticleNumber() {
        return _articleNumber;
    }

    public int getStock() {
        return _stock;
    }

    public void setStock(int stock) {
        _stock = stock;
    }

    public double getPrice() {
        return _price;
    }

    public void setPrice(double price) {
        _price = price;
    }
}
