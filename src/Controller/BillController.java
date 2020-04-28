package Controller;

import Model.Bill;
import Model.Customer;

import java.util.ArrayList;

public class BillController {

    public Bill createBill(Customer customer){
        return new Bill(customer, new ArrayList<>());
    }

    public void addBillPosition(Bill bill, String position){
        bill.getInvoiceItems().add(position);
    }
}
