package Controller;

import Model.Bill;
import Model.Customer;

import java.io.Serializable;
import java.util.ArrayList;

public class BillController implements Serializable {

    public Bill createBill(Customer customer) {
        return new Bill(customer, new ArrayList<>());
    }

    public void addBillPosition(Bill bill, String position) {
        bill.getInvoiceItems().add(position);
    }
}
