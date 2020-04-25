package Controller;

import Model.Address;
import Utilities.BooleanString;
import Utilities.BooleanStringObject;

/**
 * Verbindet alle Controller miteinader.
 * Implementiert das Singelton und ermöglicht somit von überall den Zugriff auf alle Daten und Funktionen.
 */
public class MainController {

    private PersonController _personController;
    private AddressController _addressController;

    public MainController() {
        _personController = new PersonController();
        _addressController = new AddressController();
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController, für weitere Informationen siehe: PersonController:addEmployee(...)
     */
    public BooleanString addEmployee(String firstname, String lastname, String username, String password) {
        return _personController.addEmployee(firstname, lastname, username, password);
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController und AdressController
     * Für weitere Informationen siehe:
     * PersonController:addCustomer(...)
     * AddressController:createAddress(...)
     *
     * Erstellt mit AddressController ein Address-Objekt und gib dieses weiter an PersonController
     */
    public BooleanString addCustomer(String firstname, String lastname, String username, String password,
                                     String street, String houseNumber, String postCode, String city) {
        BooleanString booleanStringResult = new BooleanString(false, "");

        BooleanStringObject createAddressResult = _addressController.createAddress(street, houseNumber, postCode, city);

        if (!createAddressResult.getValueB()) {
            booleanStringResult.setValueS(createAddressResult.getValueS());
        } else {
            _personController.addCustomer(firstname, lastname, username, password, (Address)createAddressResult.getValueO());
        }

        return booleanStringResult;
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController
     * Für weitere Informationen siehe: PersonController:login(...)
     */
    public BooleanStringObject login(String username, String password) {
        return _personController.login(username, password);
    }
}
