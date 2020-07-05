package Model;

import java.io.Serializable;

/**
 * Objekte dieser Klassen bilden die Adressen ab.
 * Diese Objekte werden zum Speichern serialisiert.
 */
public class Address implements Serializable {

    private String _street; // Straße
    private String _houseNumber; // Hausnummer
    private String _postCode; // PLZ
    private String _city; // Stadt/Ort

    public Address(String street, String houseNumber, String postCode, String city) {
        _street = street;
        _houseNumber = houseNumber;
        _postCode = postCode;
        _city = city;
    }

    public String getStreet() {
        return _street;
    }

    public void setStreet(String street) {
        _street = street;
    }

    public String getHouseNumber() {
        return _houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        _houseNumber = houseNumber;
    }

    public String getPostCode() {
        return _postCode;
    }

    public void setPostCode(String postCode) {
        _postCode = postCode;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        _city = city;
    }
}
