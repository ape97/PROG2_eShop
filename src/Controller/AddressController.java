package Controller;

import Model.Address;
import Utilities.BooleanStringObject;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Address-Objekte.
 * Die Verwaltung der Address-Objekte ist hier nur begrenzt möglich,
 * da diese jeweils den Person-Objekten zugeordnet sind.
 */
public class AddressController {

    /**
     * Erzeugt ein neues Address-Objekt und gibt dieses zurück, sofern die Parameter den Anforderungen entsprechen
     * @param street Straß
     * @param houseNumber Hausnummer
     * @param postCode Postleitzahl
     * @param city Stadt/Ort
     * @return Gibt ein BooleanStringObject-Objekt zurück.
     * Der boolean gibt an, ob das Address-Object erzeugt wurde oder nicht
     * Der String gibt die entsprechende Meldung an, z.B. den Grund weshalb die Adresse nicht erzeugt werden konnte
     * Das Object gibt das Address-Objekt zurück, sofern es erstellt werden konnte, ansonsten null
     */
    public BooleanStringObject createAddress(String street, String houseNumber, String postCode, String city) {
        BooleanStringObject booleanStringObjectResult = new BooleanStringObject(false, "", null);

        if (street.trim().isEmpty()) {
            booleanStringObjectResult.setValueS("Die Straße darf nicht leer sein.");
        } else if (houseNumber.trim().isEmpty()) {
            booleanStringObjectResult.setValueS("Die Hausnummer darf nicht leer sein.");
        } else if (postCode.trim().isEmpty()) {
            booleanStringObjectResult.setValueS("Die Potsleitzahl darf nicht leer sein.");
        } else if (city.trim().isEmpty()) {
            booleanStringObjectResult.setValueS("Die Stadt darf nicht leer sein.");
        } else{
            Address address = new Address(street, houseNumber, postCode, city);

            booleanStringObjectResult.setValueB(true);
            booleanStringObjectResult.setValueS("Die Adesse wurde erfolgreich erstellt.");
            booleanStringObjectResult.setValueO(address);
        }

        return booleanStringObjectResult;
    }
}
