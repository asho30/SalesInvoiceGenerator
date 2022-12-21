package com.egyptfwd.salesinvoicegenerator.view;

import com.egyptfwd.salesinvoicegenerator.model.InvoiceLine;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.egyptfwd.salesinvoicegenerator.Main.startApplication;
import static com.egyptfwd.salesinvoicegenerator.controller.InputsController.*;
import static com.egyptfwd.salesinvoicegenerator.controller.InvoicesController.addItemToHeader;
import static com.egyptfwd.salesinvoicegenerator.controller.InvoicesController.isInvoiceNumberExist;

public class AddItemView {
    public void addItemToStoredInvoice() {
        long invoiceNumberInput;
        System.out.println("****************************************************");
        System.out.println("Add New Item");
        System.out.println("****************************************************");
        System.out.println("Please enter the invoice number to which you want to add the item:");
        addItemToStoredInvoice(getInvoiceNumberInput());
    }

    public void addItemToStoredInvoice(long invoiceNumberInput) {
        boolean correctInput;
        int optionNumber = 0;
        InvoiceLine invoiceLine = new InvoiceLine();
        if (!isInvoiceNumberExist(invoiceNumberInput)) {
            System.out.println("No invoice with this number: invoiceNumberInput");
            System.out.println("****************************************************");
            System.out.println("1- Try with another invoice number.");
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
                    System.out.println("Wrong input, Please enter 1 to (Try Again) or 2 to (Main Menu):");
                }
            } while (!correctInput);
            if (optionNumber == 1) {
                addItemToStoredInvoice();
            } else if (optionNumber == 2) {
                startApplication();
            }
        } else {
            invoiceLine.setInvoiceNumber(invoiceNumberInput);
            System.out.println("Enter Item Name:");
            invoiceLine.setItemName(getItemNameInput());
            //System.out.println(invoiceLine.getItemName());
            System.out.println("Enter Item Price:");
            invoiceLine.setItemPrice(getItemPriceInput());
            //System.out.println(invoiceLine.getItemPrice());
            System.out.println("Enter Item Count:");
            invoiceLine.setCount(getCountInput());
            //System.out.println(invoiceLine.getCount());
            addItemToHeader(invoiceLine.getInvoiceNumber(), invoiceLine.getItemName(),
                    String.valueOf(invoiceLine.getItemPrice()), String.valueOf(invoiceLine.getCount()));
            System.out.println("Item has been added to invoice number:" + invoiceLine.getInvoiceNumber());
            System.out.println("****************************************************");
            System.out.println("1- Add another item to the invoice.");
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
                    System.out.println("Wrong input, Please enter 1 to (Add Item) or 2 to (Main Menu):");
                }
            } while (!correctInput);
            if (optionNumber == 1) {
                AddItemView addItemView = new AddItemView();
                addItemView.addItemToStoredInvoice(invoiceLine.getInvoiceNumber());
            } else if (optionNumber == 2) {
                startApplication();
            }
        }
    }
}
