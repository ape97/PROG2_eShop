/**
 * Info:
 * Stellt eine Rechnung mit den einzelnen Positionen dar
 * Referenziert auf den entsprechenden Kunden & Zeitstempel
 * Artikeldaten werden jeweils als String in eine Liste gespeichert,
 * damit sich diese Daten später nich verfälschen können, wird hier bewusst auf Referenzen von Artikeln verzichtet
 * <p>
 * Hinweis: Hier werden keine Artikel referenziert,
 * damit sich die Daten der Rechnung später nicht ändern z.B. der Preis
 **/

package Model;

import Utilities.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Bill implements Serializable {
    private Customer _customer; // Kunde
    private ArrayList<String> _invoiceItems; // Liste mit den Positionen der Rechnung, jeder Srring steht für eine Position
    private Date _timeStamp; // Zeitstempel
    private double _totalPrice; // Gesamtsumme

    public Bill(Customer customer, ArrayList<String> invoiceItems) {
        _customer = customer;
        _invoiceItems = invoiceItems;
        _timeStamp = new Date();
    }

    public String toString() {
        String result = _timeStamp.toString() + "\n" + _customer.getFirstname() + " " + _customer.getLastname();
        result += "\n" + _customer.getAddress().toString();

        for (String billPosition : _invoiceItems) {
            result += "\n" + billPosition;
        }

        result += "\n" + "\n" + Message.get(Message.MessageType.TotalPrice) + ": " + _totalPrice + "€";

        return result;
    }

    public Customer getCustomer() {
        return _customer;
    }

    public ArrayList<String> getInvoiceItems() {
        return _invoiceItems;
    }

    public Date getTimeStamp() {
        return _timeStamp;
    }

    public double getTotalPrice() {
        return _totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        _totalPrice = totalPrice;
    }
}
