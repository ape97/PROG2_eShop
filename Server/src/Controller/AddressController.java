package Controller;

import Model.Address;
import Utilities.Message;
import Utilities.Result;

/**
 * WARNING: Sollte nur vom MainController verwendet werden.
 * Verwaltet die Address-Objekte.
 * Die Verwaltung der Address-Objekte ist hier nur begrenzt möglich,
 * da diese jeweils den Person-Objekten zugeordnet sind.
 */
public class AddressController {

    /**
     * Erzeugt ein neues Address-Objekt und gibt dieses zurück, sofern die Parameter den Anforderungen entsprechen.
     * @param street Straße
     * @param houseNumber Hausnummer
     * @param postCode Postleitzahl
     * @param city Stadt/Ort
     * @return Gibt ein Result-Address zurück. Die getObject()-Methode gibt bei Erfolg der Aktion das Address-Objekt zurück.
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
            result.setState(Result.State.SUCCESSFUL);
            result.setMessage(Message.get(Message.MessageType.Info_AddressCreated));
            result.setObject(address);
        }

        return result;
    }
}
