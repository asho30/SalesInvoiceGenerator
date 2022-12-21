package com.egyptfwd.salesinvoicegenerator;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.egyptfwd.salesinvoicegenerator.controller.FileOperations;
import com.egyptfwd.salesinvoicegenerator.view.ActionInvoiceView;
import com.egyptfwd.salesinvoicegenerator.view.AddItemView;
import com.egyptfwd.salesinvoicegenerator.view.CreateInvoiceView;
import com.egyptfwd.salesinvoicegenerator.view.SalesView;

import static com.egyptfwd.salesinvoicegenerator.controller.InvoicesController.*;

public class Main {

    public static void main(String[] args) {
        //Print all stored invoices.
        printStoredInvoices(false);
        //For Clearing Files and doing some test cases and generating invoice examples.
        //runSomeTestCases();
        startApplication();

    }

    public static void startApplication() {
        SalesView salesView = new SalesView();
        ActionInvoiceView actionInvoiceView = new ActionInvoiceView();
        CreateInvoiceView createInvoiceView = new CreateInvoiceView();
        AddItemView addItemView = new AddItemView();
        salesView.displaySalesInvoiceGeneratorMainMenu();
        switch (salesView.getSelectedOperation()) {
            case 1 -> printStoredInvoices(true);
            case 2 -> actionInvoiceView.openInvoiceMenu();
            case 3 -> createInvoiceView.createInvoice();
            case 4 -> addItemView.addItemToStoredInvoice();
            case 5 -> actionInvoiceView.deleteInvoiceMenu();
            case 6 -> System.exit(0);
        }
    }

    private static void runSomeTestCases() {
        System.out.println("****************************************************");
        System.out.println("Clearing files and running test cases......");
        try {
            FileOperations.clearFiles();
        } catch (FileNotFoundException e) {
            System.out.println("Can't clear files, Please make sure the files exist and have (.csv) extension.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Can't clear files, Please ensure that the files are not in use and are not corrupted.");
            e.printStackTrace();
        }
        System.out.println("****************************************************");
        // Print all Invoices.
        System.out.println("No invoices at the beginning (After clearing the files).");
        printStoredInvoices(false);
        System.out.println("****************************************************");

        // Example for adding invoice with item.
        System.out.println("Creating new invoice (55555) with 1 inner item.");
        long invoiceNumber = 55555;
        String invoiceDate = "12/12/2022";
        String customerName = "Ahmed Ashour";
        String itemName = "Ice cream";
        String itemPrice = "5.5";
        String count = "5";
        createNewInvoice(invoiceNumber, invoiceDate, customerName, itemName, itemPrice, count);
        printStoredInvoices(false);
        System.out.println("****************************************************");

        /* Example for adding invoice with same existed invoice number.
         * It will print "The invoice number already exists.".
         */
        System.out.println("Try to duplicate invoice (55555).");
        createNewInvoice(invoiceNumber, invoiceDate, customerName, itemName, itemPrice, count);
        printStoredInvoices(false);
        System.out.println("****************************************************");

        // Example for adding just Header.
        System.out.println("Creating Empty invoice (66666).");
        invoiceNumber = 66666;
        invoiceDate = "10/11/2022";
        customerName = "Youssef Ahmed";
        createNewInvoiceHeader(invoiceNumber, invoiceDate, customerName);
        printStoredInvoices(false);
        System.out.println("****************************************************");

        // Example for adding an item to an existing invoice.
        System.out.println("Adding an item to an existing invoice (55555).");
        invoiceNumber = 55555;
        itemName = "Pop Corn";
        itemPrice = "5.1";
        count = "2";
        addItemToHeader(invoiceNumber, itemName, itemPrice, count);
        printStoredInvoices(false);
        System.out.println("****************************************************");

        // Example for adding an item to the empty invoice.
        System.out.println("Adding an item to the empty invoice (66666).");
        invoiceNumber = 66666;
        itemName = "Pizza";
        itemPrice = "3.3";
        count = "3";
        addItemToHeader(invoiceNumber, itemName, itemPrice, count);
        printStoredInvoices(false);
        System.out.println("****************************************************");

        // Example for adding an item to a non-existing invoice.
        System.out.println("Adding an item to a non-existing invoice (00000).");
        invoiceNumber = 11111;
        itemName = "Pizza";
        itemPrice = "3.3";
        count = "3";
        addItemToHeader(invoiceNumber, itemName, itemPrice, count);
        printStoredInvoices(false);
        System.out.println("****************************************************");

        // Example for duplicating an item in invoice.
        System.out.println("Try to duplicate an item (Pop Corn) in invoice (55555).");
        invoiceNumber = 55555;
        itemName = "Pop Corn";
        itemPrice = "3.3";
        count = "3";
        addItemToHeader(invoiceNumber, itemName, itemPrice, count);
        printStoredInvoices(false);
        System.out.println("****************************************************");
    }
}
