/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControllerPackage;


import ModelPackage.FileOperations;
import ModelPackage.HeaderInvoice;
import ModelPackage.InvoiceLine;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import java.util.regex.Pattern;

/**
 * @author halas
 */
public class MainController {

    private FileOperations fileOperations;
    private ArrayList<HeaderInvoice> invoices;
    Scanner scanner = new Scanner(System.in);

    public MainController() throws IOException {
        fileOperations = new FileOperations();
        invoices = fileOperations.readFile(
                System.getProperty("user.dir") + "/InvoiceHeader.csv",
                System.getProperty("user.dir") + "/InvoiceLine.csv"
                );
        if (invoices==null){
            System.exit(0);
        }

    }


    public void handleAction() {
        System.out.println("Select an action:\n1. Create\n2. read\n3. Delete\n");
        String action = scanner.nextLine();
        try {
            switch (action.toLowerCase()) {
                case "create":
                    HeaderInvoice inv = createInvoice();
                    newItems(inv);
                    break;

                case "delete":
                    deleteInvoice();
                    break;

                case "read":
                    readInvoice();
                    break;

                default:
                    System.out.println("Action not found");
                    handleAction();
            }

        } catch (IOException exception) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, exception);
        }

    }


    private HeaderInvoice createInvoice() {
        while (true) {
            System.out.println("Enter invoice data\n1. Customer name");
            String customerName = scanner.nextLine();
            System.out.println("2. Invoice date");
            String invoiceDate = scanner.nextLine();
            //try {

            if (!parseDate(invoiceDate)) {
                System.out.println("Date should be like dd-MM-yyyy ");
                continue;
            }
            // } //catch (ParseException e) {System.out.println("Date should be like d/m/yyyy"); }
            int nextInvoiceNumber = invoices.get(invoices.size() - 1).getNum() + 1;
            HeaderInvoice invoice = new HeaderInvoice(nextInvoiceNumber, customerName, invoiceDate);
            return invoice;
        }
    }
   public  boolean parseDate(String maybeDate){
            try {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                df.setLenient(false);
                df.parse(maybeDate);
                return true;
            } catch (ParseException e) {
                return false;
            }

    }
    private void newItems(HeaderInvoice invoice) {
        System.out.println("enter item name");
        String itemName = scanner.nextLine();
        System.out.println("enter item count");
        String itemCount = scanner.nextLine();
        System.out.println("enter item price");
        String itemPrice = scanner.nextLine();
        InvoiceLine invoiceLine = new InvoiceLine(invoice, itemName, Integer.parseInt(itemCount), Integer.parseInt(itemPrice));
        invoice.addItem(invoiceLine);
        System.out.println("Add another item? (y/n)");
        String response = scanner.nextLine();
        switch (response.toLowerCase()) {
            case "y" : newItems(invoice);
            default : {
                invoices.add(invoice);
                this.saveInvoices();
                System.out.println("invoice added successfully " + invoice);
                handleAction();
            }
        }
    }

    private HeaderInvoice getInvoiceFromNum(int num) {
        for (HeaderInvoice invoice : invoices) {
            if (invoice.getNum() == num) {
                return invoice;
            }
        }
        return null;
    }

    private void deleteInvoice() {
        System.out.println("Enter Invoice Num");
        int invoiceNum = Integer.parseInt(scanner.nextLine());
        invoices.remove(getInvoiceFromNum(invoiceNum));
        this.saveInvoices();
        this.handleAction();
    }

    private void deleteItem() {

    }

    private void readInvoice() throws IOException {
        System.out.println("Enter Invoice num");
        int invoiceNum = Integer.parseInt(scanner.nextLine());
        System.out.println(getInvoiceFromNum(invoiceNum));
        handleAction();
    }


    private void saveFile() {
        // todo file operation loadfile
    }

    /**
     * validate invoice
     */
//    private void AddInvoiceOK(int invoiceNum) {
//        String date = invoiceForm.getInvDateTF().getText();
//        String cuctomer = invoiceForm.getCustNameTF().getText();
//        int num = invoiceNum;
//        try {
//            String[] dateValues = date.split("-");
//            if (dateValues.length < 3) {
////                JOptionPane.showMessageDialog(frame, "Invalid Date format", "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                int day = Integer.parseInt(dateValues[0]);
//                int month = Integer.parseInt(dateValues[1]);
//                int year = Integer.parseInt(dateValues[2]);
//                if (day > 31 || month > 12) {
//                    // JOptionPane.showMessageDialog(frame, "Invalid Date format", "Error", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    HeaderInvoice invoices = new HeaderInvoice(num, cuctomer, date);
////                    frame.getInvoices().add(invoices);
////                    frame.getInvoicesModelTable().fireTableDataChanged();
//
//                }
//            }
//        } catch (Exception ex) {
//            // JOptionPane.showMessageDialog(frame, "Invalid Date format", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    private void CancelInvoice() {
    }

    private void AddItemOK() {

//        String item = itemForm.getItemNameTF().getText();
//        String countStr = itemForm.getItemCountTF().getText();
//        String priceStr = itemForm.getItemPriceTF().getText();
//        int count = Integer.parseInt(countStr);
//        double price = Double.parseDouble(priceStr);
//        int selectedInvoice = frame.getHeaderTable().getSelectedRow();
//        if (selectedInvoice != -1) {
//            HeaderInvoice invoice = frame.getInvoices().get(selectedInvoice);
//            InvoiceLine items = new InvoiceLine(invoice, item, count, price);
//            invoice.getItems().add(items);
//            ItemsTableModel itemsTableModel = (ItemsTableModel) frame.getItemtable().getModel();
//            //linesTableModel.getLines().add(line);
//            itemsTableModel.fireTableDataChanged();
//            frame.getInvoicesModelTable().fireTableDataChanged();
//        }

    }

    private void CancelItem() {
    }


    private void saveInvoices() {
        fileOperations.writeFile(invoices, System.getProperty("user.dir") + "/InvoiceHeader.csv", System.getProperty("user.dir") + "/InvoiceLine.csv");
    }

}
