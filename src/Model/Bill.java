/**
 * Klasse: Bill
 * Dateiname: Bill.java
 * Erstellt am: 22.04.2020
 * Erstellt von: Adrian Peters
 * <p>
 * Info:
 * Stellt eine Rechnung mit den einzelnen Positionen dar
 * Referenziert auf den entsprechenden Kunden & Zeitstempel
 * Artikeldaten werden jeweils als String in eine Liste gespeichert,
 * damit sich diese Daten später nich verfälschen können, wird hier bewusst auf Referenzen von Artikeln verzichtet
 * <p>
 * <p>
 * Hinweis: Hier werden keine Artikel referenziert,
 * damit sich die Daten der Rechnung später nicht ändern z.B. der Preis
 **/

package Model;

import java.util.ArrayList;
import java.util.Date;

public class Bill {
    private Customer _customer; // Kunde
    private ArrayList<String> _invoiceItems; // Liste mit den Positionen der Rechnung, jeder Srring steht für eine Position
    private Date _timeStamp; // Zeitstempel
    private double _totalPrice; // Gesamtsumme

    public Bill(Customer customer, ArrayList<String> invoiceItems) {
        _customer = customer;
        _invoiceItems = invoiceItems;
        _timeStamp = new Date();
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
}
