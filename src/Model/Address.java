package Model;

import java.io.Serializable;

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

    public String toString() {
        return _street + " " + _houseNumber + "\n" + _postCode + " " + _city;
    }

    public String getStreet() {
        return _street;
    }

    public void set_street(String street) {
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
