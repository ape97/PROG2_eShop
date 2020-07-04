package Model;

import java.io.Serializable;

/**
 * Objekte dieser Klassen bilden die Artikel ab.
 * Diese Objekte werden zum Speichern serialisiert.
 */
public class Article implements Serializable {
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

    // Wird zum Erstellen einer Rechnung benötigt, weil jeder Artikel eine Rechnungsposition abbildet
    public String toString(boolean withStock) {
        String result = _articleNumber + " - " + _name + " - " + _price + "€";

        if (withStock) {
            result += " - " + _stock;
        }

        return result;
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
