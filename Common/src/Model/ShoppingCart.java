package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objekte dieser Klasse bilden die Einkaufswagen der Kunden ab.
 * Der Einkaufswagen ist einem Kunden zugeordnet und enthält die sich darin befindlichen Artikel,
 * sowie die entsprechende Anzahl dieser.
 * Diese Objekte werden zum Speichern serialisiert.
 */
public class ShoppingCart implements Serializable {
    // Liste der Artikel im EEinkaufswagen bzw. hier als ShoppingCartItem,
    // da ein ShoppingCartItem aus Artikel und Anzahl besteht
    private ArrayList<ShoppingCartItem> _shoppingCartItemList;

    public ShoppingCart()
    {
        _shoppingCartItemList = new ArrayList();
    }

    public ArrayList<ShoppingCartItem> getShoppingCartItemList() {
        return _shoppingCartItemList;
    }
}
