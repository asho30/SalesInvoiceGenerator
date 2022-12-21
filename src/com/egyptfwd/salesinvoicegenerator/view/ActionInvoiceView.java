package com.egyptfwd.salesinvoicegenerator.view;

import static com.egyptfwd.salesinvoicegenerator.controller.InputsController.getInvoiceNumberInput;
import static com.egyptfwd.salesinvoicegenerator.controller.InvoicesController.deleteInvoice;
import static com.egyptfwd.salesinvoicegenerator.controller.InvoicesController.printInvoice;

public class ActionInvoiceView {
    public void openInvoiceMenu() {
        System.out.println("****************************************************");
        System.out.println("Open Invoice");
        System.out.println("****************************************************");
        System.out.println("Please enter the invoice number to display:");
        printInvoice(getInvoiceNumberInput());
    }
    public void deleteInvoiceMenu() {
        System.out.println("****************************************************");
        System.out.println("Delete Invoice");
        System.out.println("****************************************************");
        System.out.println("Please enter the invoice number to delete:");
        deleteInvoice(getInvoiceNumberInput());
    }
}
