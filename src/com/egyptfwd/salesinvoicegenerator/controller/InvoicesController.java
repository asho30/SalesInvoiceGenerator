package com.egyptfwd.salesinvoicegenerator.controller;

import com.egyptfwd.salesinvoicegenerator.model.InvoiceHeader;
import com.egyptfwd.salesinvoicegenerator.model.InvoiceLine;
import com.egyptfwd.salesinvoicegenerator.view.ActionInvoiceView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.egyptfwd.salesinvoicegenerator.Main.startApplication;

public class InvoicesController {
    public static void createNewInvoiceHeader(long invoiceNumber, String invoiceDate, String customerName) {
        ArrayList<InvoiceHeader> invoiceHeaders = FileOperations.readFiles();
        InvoiceHeader invoiceHeader;

        if (isInvoiceNumberExist(invoiceNumber)) {
            System.out.println("The invoice number already exists.");
            return;
        }

        invoiceHeader = addNewInvoiceHeader(invoiceNumber, invoiceDate, customerName);
        invoiceHeaders.add(invoiceHeader);

        FileOperations.writeFiles(invoiceHeaders);
    }

    public static void addItemToHeader(long invoiceNumber, String itemName, String itemPrice, String count) {
        ArrayList<InvoiceHeader> invoiceHeaders = FileOperations.readFiles();
        ArrayList<InvoiceLine> invoiceLines = new ArrayList<InvoiceLine>();
        ArrayList<InvoiceHeader> completedInvoiceHeaders = new ArrayList<InvoiceHeader>();
        InvoiceHeader invoiceHeader = new InvoiceHeader();

        if (!isInvoiceNumberExist(invoiceNumber)) {
            System.out.println("No invoice with the number: " + invoiceNumber);
            return;
        }

        for (InvoiceHeader invoiceHeaderRow : invoiceHeaders) {
            if (invoiceNumber == invoiceHeaderRow.getInvoiceNumber()) {
                invoiceLines = invoiceHeaderRow.getInvoiceLines();
                for (InvoiceLine invoiceLineRow : invoiceLines) {
                    if (itemName.equalsIgnoreCase(invoiceLineRow.getItemName())) {
                        System.out.println("The inserted item (" + itemName + ") already exists in the invoice with the number: " + invoiceNumber);
                        return;
                    }
                }
                invoiceLines.add(addNewItem(invoiceNumber, itemName, itemPrice, count));
                invoiceHeaderRow.setInvoiceLines(invoiceLines);
            }
            completedInvoiceHeaders.add(invoiceHeaderRow);
            invoiceHeaderRow = new InvoiceHeader();
            invoiceLines = new ArrayList<InvoiceLine>();
        }
        FileOperations.writeFiles(completedInvoiceHeaders);
    }

    public static void createNewInvoice(long invoiceNumber, String invoiceDate, String customerName, String itemName, String itemPrice, String count) {
        ArrayList<InvoiceHeader> invoiceHeaders = FileOperations.readFiles();
        ArrayList<InvoiceLine> invoiceLines = new ArrayList<InvoiceLine>();
        InvoiceHeader invoiceHeader;

        if (isInvoiceNumberExist(invoiceNumber)) {
            System.out.println("The invoice number already exists.");
            return;
        }

        invoiceHeader = addNewInvoiceHeader(invoiceNumber, invoiceDate, customerName);
        invoiceLines.add(addNewItem(invoiceNumber, itemName, itemPrice, count));
        invoiceHeader.setInvoiceLines(invoiceLines);
        invoiceHeaders.add(invoiceHeader);

        FileOperations.writeFiles(invoiceHeaders);
    }

    public static boolean isInvoiceNumberExist(long invoiceNumber) {
        ArrayList<InvoiceHeader> freshInvoiceHeaders = FileOperations.readFiles();
        for (InvoiceHeader invoiceHeaderRow : freshInvoiceHeaders) {
            if (invoiceNumber == invoiceHeaderRow.getInvoiceNumber()) {
                return true;
            }
        }
        return false;
    }

    /*
        private static InvoiceHeader linkHeaderWithLines(InvoiceHeader invoiceHeader, ArrayList<InvoiceLine> invoiceLines) {
            invoiceHeader.setInvoiceLines(invoiceLines);
            return invoiceHeader;
        }
    */
    public static InvoiceHeader addNewInvoiceHeader(long invoiceNumber, String invoiceDate, String customerName) {
        InvoiceHeader invoiceHeader = new InvoiceHeader();
        invoiceHeader.setInvoiceNumber(invoiceNumber);
        invoiceHeader.setInvoiceDate(invoiceDate);
        invoiceHeader.setCustomerName(customerName);
        return invoiceHeader;
    }

    public static InvoiceLine addNewItem(long invoiceNumber, String itemName, String itemPrice, String count) {
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setInvoiceNumber(invoiceNumber);
        invoiceLine.setItemName(itemName);
        invoiceLine.setItemPrice(Double.parseDouble(itemPrice));
        invoiceLine.setCount(Integer.parseInt(count));
        return invoiceLine;
    }

    public static void printStoredInvoices(boolean restartApplication) {
        ArrayList<InvoiceHeader> invoiceHeaders = FileOperations.readFiles();
        System.out.println("****************************************************");
        System.out.println("All stored invoices: (Display nothing if files empty)");
        System.out.println("****************************************************");
        for (InvoiceHeader invoiceHeaderRow : invoiceHeaders) {
            printInvoiceHeaderRow(invoiceHeaderRow);
        }
        System.out.println("****************************************************");
        if (restartApplication) {
            startApplication();
        }
    }

    public static void printInvoice(long invoiceNumber) {
        int optionNumber = 0;
        boolean correctInput, isPrinted = false;
        ArrayList<InvoiceHeader> invoiceHeaders = FileOperations.readFiles();
        System.out.println("****************************************************");
        System.out.println("Printing invoice number: " + invoiceNumber);
        System.out.println("****************************************************");
        for (InvoiceHeader invoiceHeaderRow : invoiceHeaders) {
            if (invoiceNumber == invoiceHeaderRow.getInvoiceNumber()) {
                printInvoiceHeaderRow(invoiceHeaderRow);
                isPrinted = true;
            }
        }
        if (isPrinted == false){
            System.out.println("No invoice with this number.");
        }
        System.out.println("****************************************************");
        System.out.println("1- Print another invoice.");
        System.out.println("2- Return to the main menu.");
        System.out.println("Enter option number:");
        do {
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                optionNumber = Integer.parseInt(scanner.next("^[[1-2]]+$"));
                correctInput = true;
                //System.out.println(optionNumber);
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println("Wrong input, Please enter 1 to (Print Invoice) or 2 to (Main Menu):");
            }
        } while (!correctInput);
        if (optionNumber == 1) {
            ActionInvoiceView actionInvoiceView = new ActionInvoiceView();
            actionInvoiceView.openInvoiceMenu();
        } else if (optionNumber == 2) {
            startApplication();
        }
    }

    public static void deleteInvoice(long invoiceNumber) {
        int optionNumber = 0;
        boolean correctInput, isDeleted = false;;
        ArrayList<InvoiceHeader> invoiceHeaders = FileOperations.readFiles();
        ArrayList<InvoiceHeader> newInvoiceHeaders = new ArrayList<>();
        System.out.println("****************************************************");
        System.out.println("Deleting invoice number: " + invoiceNumber);
        System.out.println("****************************************************");
        for (InvoiceHeader invoiceHeaderRow : invoiceHeaders) {
            if (invoiceNumber == invoiceHeaderRow.getInvoiceNumber()) {
                isDeleted = true;
                continue;
            } else {
                newInvoiceHeaders.add(invoiceHeaderRow);
            }
        }
        if (isDeleted == false){
            System.out.println("No invoice with this number.");
        } else {
            FileOperations.writeFiles(newInvoiceHeaders);
            System.out.println("Invoice number " + invoiceNumber + " has been deleted.");
        }
        System.out.println("****************************************************");
        System.out.println("1- Delete another invoice.");
        System.out.println("2- Return to the main menu.");
        System.out.println("Enter option number:");
        do {
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                optionNumber = Integer.parseInt(scanner.next("^[[1-2]]+$"));
                correctInput = true;
                //System.out.println(optionNumber);
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println("Wrong input, Please enter 1 to (Print Invoice) or 2 to (Main Menu):");
            }
        } while (!correctInput);
        if (optionNumber == 1) {
            ActionInvoiceView actionInvoiceView = new ActionInvoiceView();
            actionInvoiceView.deleteInvoiceMenu();
        } else if (optionNumber == 2) {
            startApplication();
        }
    }

    private static void printInvoiceHeaderRow(InvoiceHeader invoiceHeaderRow) {
        System.out.println(invoiceHeaderRow.getInvoiceNumber());
        System.out.println("{");
        System.out.println(invoiceHeaderRow.getInvoiceDateAsString() + ", " + invoiceHeaderRow.getCustomerName());
        for (InvoiceLine invoiceLinesRow : invoiceHeaderRow.getInvoiceLines()) {
            System.out.println(invoiceLinesRow.getItemName() + ", "
                    + invoiceLinesRow.getItemPrice() + ", "
                    + invoiceLinesRow.getCount());
        }
        System.out.println("}");
    }
}
