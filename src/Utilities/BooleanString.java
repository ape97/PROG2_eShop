package Utilities;

/**
 * Objekte dieser Klasse sind nützlich, wenn man mehr als einen Wert zurückgeben möchte.
 * In den meisten Fällen wird ein boolean und ein String benötigt.
 * Der Boolean gibt an, ob eine Operation erfolgreich war oder nicht.
 * Der String gibt die entsprechende Meldung an, z.B. wieso eine Operation nicht erfolgreich war.
 */
public class BooleanString {
    private boolean _valueB;
    private String _valueS;

    public BooleanString(boolean valueB, String valueS) {
        _valueB = valueB;
        _valueS = valueS;
    }

    public boolean getValueB() {
        return _valueB;
    }

    public void setValueB(boolean valueB) {
        _valueB = valueB;
    }

    public String getValueS() {
        return _valueS;
    }

    public void setValueS(String valueS) {
        _valueS = valueS;
    }
}
