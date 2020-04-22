/**
 * Klasse: Address
 * Dateiname: Address.java
 * Erstellt am: 22.04.2020
 * Erstellt von: Adrian Peters
 *
 * Info:
 * Enthält die wesentlichen Daten einer Adresse, dient lediglich der Datenkapselung
 **/

package Model;

public class Address {
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

    public String toString() {
        return _street + " " + _houseNumber + "\n" + _postCode + " " + _city;
    }

    public String get_street() {
        return _street;
    }

    public void set_street(String street) {
        _street = street;
    }

    public String get_houseNumber() {
        return _houseNumber;
    }

    public void set_houseNumber(String houseNumber) {
        _houseNumber = houseNumber;
    }

    public String get_postCode() {
        return _postCode;
    }

    public void set_postCode(String postCode) {
        _postCode = postCode;
    }

    public String get_city() {
        return _city;
    }

    public void set_city(String city) {
        _city = city;
    }
}
