package Controller;

import Model.Address;
import Utilities.Message;
import Utilities.Result;

import java.io.Serializable;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Address-Objekte.
 * Die Verwaltung der Address-Objekte ist hier nur begrenzt möglich,
 * da diese jeweils den Person-Objekten zugeordnet sind.
 */
public class AddressController implements Serializable {

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
    public Result<Address> createAddress(String street, String houseNumber, String postCode, String city) {
        Result<Address> result = new Result<Address>(Result.State.FAILED, "", null);

        if (street.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_StreetNotEmpty));
        } else if (houseNumber.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_HouseNumberNotEmpty));
        } else if (postCode.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_PostCodeNotEmpty));
        } else if (city.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_CityNotEmpty));
        } else{
            Address address = new Address(street, houseNumber, postCode, city);

            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_AddressCreated));
            result.setObject(address);
        }

        return result;
    }
}
