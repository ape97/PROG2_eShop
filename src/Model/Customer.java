/**
 * Klasse: Customer
 * Dateiname: Customer.java
 * Erstellt am: 22.04.2020
 * Erstellt von: Adrian Peters
 * <p>
 * Info:
 * Enthält die wesentlichen Daten eines Kunden
 * Leitet von Person ab und erweitert den super-Konstruktor um ein Adress-Objekt
 **/

package Model;

public class Customer extends Person {
    private Address _address; // Adresse
    private ShoppingCart _shoppingCart; // Einkaufswagen

    public Customer(String firstname, String lastname, int id, String username, String password, Address address) {
        super(firstname, lastname, id, username, password);
        _address = address;
        _shoppingCart = new ShoppingCart();
    }

    public Address get_address() {
        return _address;
    }

    public ShoppingCart get_shoppingCart() {
        return _shoppingCart;
    }
}
