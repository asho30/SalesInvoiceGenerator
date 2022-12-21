package com.egyptfwd.salesinvoicegenerator.view;

import com.egyptfwd.salesinvoicegenerator.controller.FileOperations;
import com.egyptfwd.salesinvoicegenerator.model.InvoiceHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.egyptfwd.salesinvoicegenerator.Main.startApplication;
import static com.egyptfwd.salesinvoicegenerator.controller.InputsController.*;
import static com.egyptfwd.salesinvoicegenerator.controller.InvoicesController.*;

public class CreateInvoiceView {
    public void createInvoice() {
        InvoiceHeader invoiceHeader = new InvoiceHeader();
        String customerNameInput = null, autoInput = null;
        int optionNumber = 0;
        Date currentDate = new Date();
        boolean correctInput = false;
        long invoiceNumberInput = 0;
        System.out.println("****************************************************");
        System.out.println("Create New Invoice");
        System.out.println("****************************************************");
        System.out.println("Do you want to auto-generate the invoice number?(Y/N)");
        do {
            correctInput = false;
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                autoInput = scanner.next("[YyNn]");
                //System.out.println(auto);
                correctInput = true;
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println("Wrong input, please enter Y|y for Yes Or N|n for No:");
            }
        } while (!correctInput);
        if (autoInput.equalsIgnoreCase("y")) {
            invoiceNumberInput = System.currentTimeMillis();
            System.out.println("Invoice number: " + invoiceNumberInput);
        } else if (autoInput.equalsIgnoreCase("n")) {
            System.out.println("Please enter invoice number:");
            invoiceNumberInput = getInvoiceNumberInput();
        }
        if (isInvoiceNumberExist(invoiceNumberInput)) {
            System.out.println("The invoice number already exists.");
        } else {
            invoiceHeader.setInvoiceNumber(invoiceNumberInput);
            //System.out.println( invoiceHeader.getInvoiceNumber());
            System.out.println("Enter Customer Name:");
            invoiceHeader.setCustomerName(getCustomerNameInput());
            invoiceHeader.setInvoiceDate(currentDate);
            //System.out.println(invoiceHeader.getInvoiceDateAsString());
            createNewInvoiceHeader(invoiceHeader.getInvoiceNumber(),
                    invoiceHeader.getInvoiceDateAsString(), invoiceHeader.getCustomerName());
            System.out.println("Invoice has been saved as a blank invoice for a future operation.");
        }
        System.out.println("****************************************************");
        System.out.println("1- Add an item to the invoice number: " + invoiceNumberInput);
        System.out.println("2- Create another invoice.");
        System.out.println("3- Return to the main menu.");
        System.out.println("Enter option number:");
        do {
            correctInput = false;
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                optionNumber = Integer.valueOf(scanner.next("[1-3]"));
                correctInput = true;
                //System.out.println(optionNumber);
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println(
                        "Wrong input, Please enter 1 to (Add Item) or 2 to (Create Invoice) or 3 to (Main Menu):");
            }
        } while (!correctInput);
        if (optionNumber == 1) {
            AddItemView addItemView = new AddItemView();
            addItemView.addItemToStoredInvoice(invoiceHeader.getInvoiceNumber());
        } else if (optionNumber == 2) {
            CreateInvoiceView createInvoiceView = new CreateInvoiceView();
            createInvoiceView.createInvoice();
        } else if (optionNumber == 3) {
            startApplication();
        }
    }
}
