package com.egyptfwd.salesinvoicegenerator.view;


import com.egyptfwd.salesinvoicegenerator.controller.FileOperations;
import com.egyptfwd.salesinvoicegenerator.model.InvoiceHeader;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.egyptfwd.salesinvoicegenerator.Main.startApplication;
import static com.egyptfwd.salesinvoicegenerator.controller.InvoicesController.createNewInvoiceHeader;

public class SalesView {
    private int selectedOperation = 0;

    public SalesView() {
        this.selectedOperation = 0;
    }

    public int getSelectedOperation() {
        return selectedOperation;
    }

    public void setSelectedOperation(int selectedOperation) {
        this.selectedOperation = selectedOperation;
    }

    public void displaySalesInvoiceGeneratorMainMenu() {
        System.out.println("****************************************************");
        System.out.println("Sales Invoice Generator");
        System.out.println("****************************************************");
        System.out.println("1- Print All Stored Invoices.");
        System.out.println("2- Open Invoice.");
        System.out.println("3- Create Invoice.");
        System.out.println("4- Add Item To Stored Invoice.");
        System.out.println("5- Delete Invoice.");
        System.out.println("6- Close The Application.");
        System.out.println("Enter operation number:");
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                this.selectedOperation = Integer.valueOf(scanner.next("[1-6]"));
                if (!(this.selectedOperation >= 1 && this.selectedOperation <= 6)) {
                    System.out.println("Out of the available options, Please select option from 1 to 6:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, Please select option from 1 to 6:");
            }
        } while (!(this.selectedOperation >= 1 && this.selectedOperation <= 6));
    }
}

/*
//I canceled what I started doing for the GUI part when informed it is just a console application.
import javax.swing.*;
import java.awt.*;

public class SalesView extends JFrame {
    //JPanel salesJPanel= new JPanel();
    JButton addInvoiceJButton,addItemJButton,openInvoiceJButton;
    JTextField invoiceNumberText,invoiceDateText,customerNameText,itemNameText,itemPriceText,countText;
    public SalesView() { //throws HeadlessException
        addInvoiceJButton = new JButton("New Invoice");
        addItemJButton = new JButton("New Item");
        openInvoiceJButton = new JButton("Open Invoice");
        invoiceNumberText = new JTextField("New Invoice");
                //,invoiceDateText,customerNameText,itemNameText,itemPriceText,countText;
        this.setTitle("Sales Invoice Generator");
        this.setSize(500,500);
        this.setResizable(false);
        this.setLocation(100,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setLayout(null);
        this.setVisible(true);


        //salesJPanel.setBackground(Color.BLACK);
        //this.add(salesJPanel);


        addInvoiceJButton.setBounds(20,10,120,25);
        addItemJButton.setBounds(180,10,120,25);
        openInvoiceJButton.setBounds(340,10,120,25);

        this.add(addInvoiceJButton);
        this.add(addItemJButton);
        this.add(openInvoiceJButton);
        this.add(invoiceNumberText);
    }
}
*/