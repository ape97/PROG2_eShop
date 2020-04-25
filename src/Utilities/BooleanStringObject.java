package Utilities;

/**
 * Diese Klasse ist eine Erweiterung der BooleanString Klasse.
 * Hier wurde nur um einen dritten Wert Object erweitert.
 * Ein Anwendungsfall wäre, wenn man zusätzlich noch ein Objekt zurückgeben möchte,
 * welches in der Operation erzeugt wurde.
 */
public class BooleanStringObject extends BooleanString{
    private Object _valueO;

    public BooleanStringObject(boolean valueB, String valueS, Object valueO) {
        super(valueB, valueS);
        _valueO = valueO;
    }

    public Object getValueO() {
        return _valueO;
    }

    public void setValueO(Object valueO) {
        _valueO = valueO;
    }
}
