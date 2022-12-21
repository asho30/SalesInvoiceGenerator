package com.egyptfwd.salesinvoicegenerator.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputsController {
    public static long getInvoiceNumberInput() {
        String invoiceNumberInputString;
        long invoiceNumberInput = 0;
        boolean correctInput;
        do{
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                invoiceNumberInputString = scanner.next("^[.0-9]+$");
                invoiceNumberInput = Long.parseLong(invoiceNumberInputString);
                //System.out.println(invoiceNumberInput);
                if (invoiceNumberInput == 0){
                    correctInput = false;
                    System.out.println("Invoice number can't be Zero, Please enter invoice number:");
                }else {
                    correctInput = true;
                }
            } catch (NumberFormatException e){
                correctInput = false;
                System.out.println("Please enter the invoice number in an appropriate format:");
                //e.printStackTrace();
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println("Wrong input, please enter invoice number by numbers only:");
            }
        }while(!correctInput);
        return invoiceNumberInput;
    }

    public static String getCustomerNameInput(){
        String customerNameInput = null;
        boolean correctInput;
        do{
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                customerNameInput = scanner.next("^[[\\t\\s][a-zA-Z]]+$");
                //System.out.println(customerNameInput);
                if (customerNameInput.length()<4) {
                    correctInput = false;
                    System.out.println("Please enter a Customer Name with 4 characters or more:");
                } else {
                    correctInput = true;
                }
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println("Wrong input, please enter a Customer Name by characters only:");
            }
        } while(!correctInput);
        return customerNameInput;
    }

    public static String getItemNameInput(){
        String itemNameInput = null;
        boolean correctInput = false;
        do{
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                itemNameInput = scanner.next("^[[\\t\\s][a-zA-Z]]+$");
                //System.out.println(itemNameInput);
                if (itemNameInput.length()<3) {
                    correctInput = false;
                    System.out.println("Please enter a Customer Name with 3 characters or more:");
                } else {
                    correctInput = true;
                }
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println("Wrong input, please enter Item Name by characters only:");
            }
        } while(!correctInput);
        return itemNameInput;
    }

    public static Double getItemPriceInput(){
        String itemPriceInputString = null;
        Double itemPriceInput = 0.0;
        boolean correctInput = false;
        do{
            correctInput = false;
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                itemPriceInputString = scanner.next("^[[.][0-9]]+$");
                correctInput = true;
                itemPriceInput = Double.valueOf(itemPriceInputString);
                if (itemPriceInput == 0.0){
                    correctInput = true;
                    System.out.println("Adding free Item.");
                }
            } catch (NumberFormatException e){
                correctInput = false;
                System.out.println("Please enter the item price in an appropriate format:");
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println("Wrong input, please enter item price by numbers only:");
            }
        }while(!correctInput);
        return itemPriceInput;
    }

    public static int getCountInput(){
        String countInputString = null;
        int countInput = 0;
        boolean correctInput = false;
        do{
            correctInput = false;
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("\n");
                countInputString = scanner.next("^[.0-9]+$");
                if (Integer.valueOf(countInputString) == 0){
                    correctInput = false;
                    System.out.println("Count can't be Zero, Please enter right number:");
                }else {
                    correctInput = true;
                    countInput = Integer.valueOf(countInputString);
                    //System.out.println(countInput);
                }
            } catch (NumberFormatException e){
                correctInput = false;
                System.out.println("Please enter the count in an appropriate format:");
            } catch (InputMismatchException e) {
                correctInput = false;
                System.out.println("Wrong input, please enter invoice number by numbers only:");
            }
        }while(!correctInput);
        return countInput;
    }
}
