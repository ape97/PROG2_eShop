package Controller;

import Model.Bill;
import Model.Customer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Wird zum Hinzufügen von Positionen zu einem Bill-OBjekt verwendet und zum Erstellen eines Bill-Objektes.
 * Da Rechnungen aktuell nicht gespeichert, sondern nur ausgegeben werden, sind die Methoden sehr üebrsichtlich.
 */
public class BillController {

    public Bill createBill(Customer customer) {
        return new Bill(customer, new ArrayList<>());
    }

    public void addBillPosition(Bill bill, String position) {
        bill.getInvoiceItems().add(position);
    }
}
