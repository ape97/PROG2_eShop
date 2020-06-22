/**
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

    public Address getAddress() {
        return _address;
    }

    public void setAddress(Address address) {
        _address = address;
    }

    public ShoppingCart getShoppingCart() {
        return _shoppingCart;
    }
}
