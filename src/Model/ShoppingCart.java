/**
 * Info:
 * Der Einkaufswagen ist einem Kunden zugeordnet und enhält die sich darin befindlichen Artikel,
 * sowie die entsprechende Anzahl dieser
 **/

package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart implements Serializable {
    private ArrayList<ShoppingCartItem> _shoppingCartItemList;
    private transient ObservableList<ShoppingCartItem> _shoppingCartObservableList;

    public ShoppingCart()
    {
        _shoppingCartItemList = new ArrayList();
        _shoppingCartObservableList = FXCollections.observableList(_shoppingCartItemList);
    }

    public void initAfterSerialization(){
        _shoppingCartObservableList = FXCollections.observableList(_shoppingCartItemList);
    }

    public ObservableList<ShoppingCartItem> getShoppingCartItemList() {
        return _shoppingCartObservableList;
    }

}
