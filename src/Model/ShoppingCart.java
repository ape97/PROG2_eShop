/**
 * Klasse: ShoppingCart
 * Dateiname: ShoppingCart.java
 * Erstellt am: 22.04.2020
 * Erstellt von: Adrian Peters
 *
 * Info:
 * Der Einkaufswagen ist einem Kunden zugeordnet und enhält die sich darin befindlichen Artikel,
 * sowie die entsprechende Anzahl dieser
 **/

package Model;

import java.util.HashMap;

public class ShoppingCart {
    private HashMap<Article, Integer> _articleAndQuantityMap;

    public ShoppingCart() {
        _articleAndQuantityMap = new HashMap<>();
    }

    public HashMap<Article, Integer> get_articleAndQuantityMap() {
        return _articleAndQuantityMap;
    }
}
