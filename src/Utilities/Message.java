package Utilities;

public class Message {

    private static Language _language = Language.German;


    final static String ERROR_TEXT = "HALLO";

    public static String get(MessageType messageType) {
        String result = "";

        if (_language == Language.German) {
            result = getGermanMessage(messageType);
        } else { // English
            result = getEnglishMessage(messageType);
        }

        return result;
    }

    private static String getGermanMessage(MessageType messageType) {
        String result = "KEIN TEXT";

        switch (messageType) {

            case Error_StreetNotEmpty:
                result = "Die Straße muss angegeben werden.";
                break;
            case Error_HouseNumberNotEmpty:
                result = "Die Hausnummer muss angegeben werden.";
                break;
            case Error_PostCodeNotEmpty:
                result = "Die Postleitzahl muss angegeben werden.";
                break;
            case Error_CityNotEmpty:
                result = "Der Ort muss angegeben werden.";
                break;
            case Info_AddressCreated:
                result = "Die Adresse wurde erfolgreich erstellt.";
                break;
            case Error_ArticleNameNotEmpty:
                result = "Der Artikelname darf nicht leer sein.";
                break;
            case Error_ArticlePriceGreaterThanZero:
                result = "Der Artikelpreis muss größer als 0 betragen.";
                break;
            case Error_ArticleNumberExists:
                result = "Die angegebene Artikelnummer ist bereits vergeben.";
                break;
            case Info_ArticleCreated:
                result = "Der Artikel wurde erfolgreich erstellt.";
                break;
            case Error_ChangeValueNotZero:
                result = "Die Bestandsveränderung darf nicht 0 betragen.";
                break;
            case Info_EmployeeCreated:
                result = "Der Mitarbeiter wurde erfolgreich erstellt.";
                break;
            case Info_CustomerCreated:
                result = "Der Kunde erfolgreich erstellt.";
                break;
            case Info_LoginSuccess:
                result = "Sie wurden erfolgreich angemeldet.";
                break;
            case Error_LoginFailed:
                result = "Die Anmeldung ist fehlgeschlagen. Bitte überprüfen Sie den Benutzernamen und das Passwort.";
                break;
            case Error_FirstNameNotEmpty:
                result = "Der Vorname darf nicht leer sein.";
                break;
            case Error_LastNameNotEmpty:
                result = "Der Nachname darf nicht leer sein.";
                break;
            case Error_UsernameExists:
                result = "Der angegebene Benutzername ist bereits vergeben.";
                break;
            case Error_UsernameInvalid:
                result = "Der angegebene Benutzername ist ungültig. (nicht leer, keine Leerzeichen, mindestens 3 Zeichen)"; //TODO: Kriterien
                break;
            case Error_PasswordInvalid:
                result = "Das angegebene Passwort ist ungültig. (nicht leer, mindestens 8 Zeichen)"; //TODO: Kriterien
                break;
            case TotalPrice:
                result = "Gesamtsumme";
                break;
            case Error_NoPrivileges:
                result = "Sie besitzen nicht die erforderlichen Rechte, um diese Aktion durchzuführen.";
                break;
            case Info_ArticleAddedToCart:
                result = "Der Artikel wurde dem Warenkorb hinzugefügt.";
                break;
            case Info_ArticleEdited:
                result = "Der Artikel wurde erfolgreich editiert.";
                break;
            case Error_ArticleNumberNotFound:
                result = "Es wurde kein Artikel unter der angegebenen Artikelnummer gefunden.";
                break;
            case Error_ArticleStockNotEnough:
                result = "Mindestens von einem Artikel im Warenkorb ist nicht genug Lagerbestand vorhanden.";
                break;
            case Error_ArticleItemNumberNotNegative:
                result = "Die Artikel Anzahl darf nicht negativ sein.";
                break;
            case Info_ArticleRemovedFromCartSuccess:
                result = "Artikel erfolgreich aus dem Warenkorb entfernt.";
                break;
            case Info_ArticleRemoved:
                result = "Artikel erfolgreich gelöscht";
                break;
            case Error_ArticleItemNumberNotZero:
                result = "Artikel Anzahl darf nicht negativ sein.";
                break;
            case Info_OrderSuccess:
                result = "Die Bestellung wurde erfolgreich abgeschlossen.";
                break;
            case Error_ArticleStockCartNotEnough:
                result = "Von einem der Artikel im Warenkorb ist nicht mehr genug Lagerbestand vorhanden.";
                break;
            case Info_ShoppingCartClearSuccess:
                result = "Der Warenkorb wurde geleert.";
                break;
            case Info_ArticleStockChanged:
                result = "Der Artikelbestand wurde geändert.";
                break;
            case Error_ArticleStockNotMatchPackagingUnit:
                result = "Artikel Anzahl stimmt nicht mit der Verpackungseinheit überein.";
                break;
            case Info_CustomerEdited:
                result = "Der Kunde wurde erfolgreich editiert.";
                break;
            case Info_PersonRemoved:
                result = "Person wurde erfolgreich gelöscht";
                break;
            case Info_EmployeeEdited:
                result = "Der MItarbeiter wurde editiert.";
                break;
            case Info:
                result = "Information";
                break;
            case Error:
                result = "Fehler";
                break;
            case Info_YouWillLoggedIn:
                result = "Sie werden nun eingeloggt.";
                break;
            case Error_FillAllField:
                result = "Bitte alle Felder ausfüllen.";
                break;
        }

        return result;
    }

    private static String getEnglishMessage(MessageType messageType) {
        String result = "NO TEXT";

        return result;
    }

    public static void setLanguage(Language language) {
        _language = language;
    }

    public enum MessageType {
        Error_StreetNotEmpty,
        Error_HouseNumberNotEmpty,
        Error_PostCodeNotEmpty,
        Error_CityNotEmpty,
        Info_AddressCreated,
        Error_ArticleNameNotEmpty,
        Error_ArticlePriceGreaterThanZero,
        Error_ArticleNumberExists,
        Info_ArticleCreated,
        Info_ArticleEdited,
        Error_ChangeValueNotZero,
        Info_EmployeeCreated,
        Info_CustomerCreated,
        Info_LoginSuccess,
        Error_LoginFailed,
        Error_FirstNameNotEmpty,
        Error_LastNameNotEmpty,
        Error_UsernameExists,
        Error_UsernameInvalid,
        Error_PasswordInvalid,
        TotalPrice,
        Error_NoPrivileges,
        Info_ArticleAddedToCart,
        Error_ArticleNumberNotFound,
        Error_ArticleStockNotEnough,
        Error_ArticleItemNumberNotNegative,
        Info_ArticleRemovedFromCartSuccess,
        Error_ArticleItemNumberNotZero,
        Info_OrderSuccess,
        Error_ArticleStockCartNotEnough,
        Info_ShoppingCartClearSuccess,
        Info_ArticleStockChanged,
        Error_ArticleStockNotMatchPackagingUnit,
        Info_ArticleRemoved,
        Info_CustomerEdited,
        Info_PersonRemoved,
        Info_EmployeeEdited,
        Info,
        Error,
        Info_YouWillLoggedIn,
        Error_FillAllField
    }

    public enum Language {
        German,
        English
    }
}

