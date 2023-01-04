/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelPackage;

import java.util.Date;
import java.util.ArrayList;

/**
 * @author halas
 */
public class HeaderInvoice {
    private int invoiceNumber;
    private String customerName;
    private String invoiceDate;
    private ArrayList<InvoiceLine> items;

    public HeaderInvoice(int invoiceNumber, String customerName, String invoiceDate) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
    }


    public String getDate() {
        return invoiceDate;
    }

    public void setDate(String date) {
        this.invoiceDate = date;
    }

    public int getNum() {
        return invoiceNumber;
    }

    public void setNum(int num) {
        this.invoiceNumber = num;
    }

    public String getName() {
        return customerName;
    }

    public void setName(String name) {
        this.customerName = name;
    }

    public double getTotal() {
        double total = 0.0;
        for (InvoiceLine invoiceLine : getItems()) {
            total += invoiceLine.getTotal();
        }
        return total;
    }

    public ArrayList<InvoiceLine> getItems() {
        if (items == null) {
            items = new ArrayList();
        }
        return items;
    }

    public ArrayList<InvoiceLine> addItem(InvoiceLine invoiceLine) {
        if (items == null)
            items = new ArrayList<>();
        items.add(invoiceLine);
        return items;
    }

    @Override
    public String toString() {
        return "HeaderInvoice{" + "num=" + invoiceNumber + ", name=" + customerName + ", date=" + invoiceDate + ", items=" + items + '}';
    }

    public String getAsCSVFormat() {
        return invoiceNumber + "," + invoiceDate + "," + customerName;
    }

}
