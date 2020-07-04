package Utilities;



/**
 * Diese statische Klasse enthält nützliche Funktionen zum Parsen von Werten.
 *
 * Quelle: https://stackoverflow.com/questions/8391979/does-java-have-a-int-tryparse-that-doesnt-throw-an-exception-for-bad-data
 */
public class Parse {
    private Parse() { // private, weil statisch
    }

    /**
     * Versucht einen String in einen Integer zu parsen.
     * Gibt ein Result<Integer> mit dem Ergebis zurück.
     * @param value Der String zum Parsen
     * @return Das Ergebnis der Aktion als Result<Integer> (hier wird keine Benutzermeldung angegeben, da es für interne Zwecke gedacht ist)
     */
    public static Result<Integer> tryParseInt(String value) {
        try {
            return new Result<Integer>(Result.State.SUCCESSFULL, "", Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return new Result<Integer>(Result.State.FAILED, "", null);
        }
    }

    /**
     * Wandelt einen String in einen Integer mit der Standard-Funktion um.
     * Dient lediglich der einheitlichen Nutzung von dieser Parse-Klasse.
     * @param value Der String zum Parsen
     * @return Der Integer
     */
    public static int parseInteger(String value){
        return Integer.parseInt(value);
    }

    /**
     * Versucht einen String in einen Double zu parsen.
     * Gibt ein Result<Double> mit dem Ergebis zurück.
     * @param value Der String zum Parsen
     * @return Das Ergebnis der Aktion als Result<Double> (hier wird keine Benutzermeldung angegeben, da es für interne Zwecke gedacht ist)
     */
    public static Result<Double> tryParseDouble(String value) {
        try {
            return new Result<Double>(Result.State.SUCCESSFULL, "", Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return new Result<Double>(Result.State.FAILED, "", null);
        }
    }

    /**
     * Wandelt einen String in einen Double mit der Standard-Funktion um.
     * Dient lediglich der einheitlichen Nutzung von dieser Parse-Klasse.
     * @param value Der String zum Parsen
     * @return Der Double
     */
    public static double parseDouble(String value){
        return Double.parseDouble(value);
    }
}
