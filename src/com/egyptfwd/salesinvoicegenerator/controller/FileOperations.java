package com.egyptfwd.salesinvoicegenerator.controller;

import com.egyptfwd.salesinvoicegenerator.model.InvoiceHeader;
import com.egyptfwd.salesinvoicegenerator.model.InvoiceLine;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileOperations {
    final static String headersCsvFile = "InvoiceHeader.csv";
    final static String linesCsvFile = "InvoiceLine.csv";

    public static ArrayList<InvoiceHeader> readFiles() {
        String row;
        String[] data;
        String[] allData = new String[0];
        ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
        ArrayList<InvoiceHeader> completedInvoiceHeaders = new ArrayList<>();
        ArrayList<InvoiceLine> invoiceLines = new ArrayList<InvoiceLine>();
        ArrayList<InvoiceLine> invoiceLinesSorted = new ArrayList<InvoiceLine>();
        InvoiceHeader invoiceHeader = new InvoiceHeader();
        InvoiceLine invoiceLine = new InvoiceLine();
        BufferedReader csvReader;
        try {
            csvReader = new BufferedReader(new FileReader(headersCsvFile));
            while ((row = csvReader.readLine()) != null) {
                data = row.split(",");
                int firstLength = allData.length;
                allData = Arrays.copyOf(allData, firstLength + data.length);
                for (int i = 0; i < data.length; i++) {
                    allData[firstLength + i] = data[i];
                }
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("The read operation failed.");
            System.out.println("Please make sure the InvoiceHeader.csv file exists and has (.csv) extension.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The read operation failed.");
            System.out.println("Please ensure that the InvoiceHeader.csv file is not in use and is not corrupted.");
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < allData.length; i += 3) {
                invoiceHeader = new InvoiceHeader();
                invoiceHeader.setInvoiceNumber(Long.parseLong(allData[i]));
                invoiceHeader.setInvoiceDate(allData[i + 1]);
                invoiceHeader.setCustomerName(allData[i + 2]);
                invoiceHeaders.add(invoiceHeader);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid data in the file.");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data in an Invoice");
            e.printStackTrace();
        }
//////////////////////////////////////////////////////////////////////////
        try {
            csvReader = new BufferedReader(new FileReader(linesCsvFile));
            allData = new String[0];
            while ((row = csvReader.readLine()) != null) {
                data = row.split(",");
                int firstLength = allData.length;
                allData = Arrays.copyOf(allData, firstLength + data.length);
                for (int i = 0; i < data.length; i++) {
                    allData[firstLength + i] = data[i];
                }
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("The read operation failed.");
            System.out.println("Please make sure the InvoiceLines.csv file exists and has (.csv) extension.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The read operation failed.");
            System.out.println("Please ensure that the InvoiceLines.csv file is not in use and is not corrupted.");
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < allData.length; i += 4) {
                invoiceLine = new InvoiceLine();
                invoiceLine.setInvoiceNumber(Long.parseLong(allData[i]));
                invoiceLine.setItemName(allData[i + 1]);
                invoiceLine.setItemPrice(Double.parseDouble(allData[i + 2]));
                invoiceLine.setCount(Integer.parseInt(allData[i + 3]));
                invoiceLines.add(invoiceLine);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid data in the file.");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data in an Invoice");
            e.printStackTrace();
        }
//////////////////////////////////////////////////////////////////////////
        for (InvoiceHeader invoiceHeaderRow : invoiceHeaders) {
            invoiceLinesSorted = new ArrayList<InvoiceLine>();
            for (InvoiceLine invoiceLinesRow : invoiceLines) {
                if (invoiceHeaderRow.getInvoiceNumber() == invoiceLinesRow.getInvoiceNumber()) {
                    invoiceLinesSorted.add(invoiceLinesRow);
                }
            }
            invoiceHeaderRow.setInvoiceLines(invoiceLinesSorted);
            completedInvoiceHeaders.add(invoiceHeaderRow);
        }
        return completedInvoiceHeaders;
    }

    public static void writeFiles(ArrayList<InvoiceHeader> invoiceHeaders) {
        int i = 0, j = 0;
        for (InvoiceHeader invoiceHeaderRow : invoiceHeaders) {
            saveHeaderToFile(invoiceHeaderRow, (i == 0) ? false : true);
            i++;
            for (InvoiceLine invoiceLinesRow : invoiceHeaderRow.getInvoiceLines()) {
                saveLineToFile(invoiceLinesRow, (j == 0) ? false : true);
                j++;
                invoiceLinesRow = new InvoiceLine();
            }
            invoiceHeaderRow = new InvoiceHeader();
        }
    }

    public static void saveHeaderToFile(InvoiceHeader invoiceHeader, boolean isNewFile) {
        try {
            FileWriter csvWriter = new FileWriter(headersCsvFile, isNewFile);
            csvWriter.append(String.valueOf(invoiceHeader.getInvoiceNumber()));
            csvWriter.append(",");
            csvWriter.append(invoiceHeader.getInvoiceDateAsString());
            csvWriter.append(",");
            csvWriter.append(invoiceHeader.getCustomerName());
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("The write operation failed.");
            System.out.println("Please make sure the InvoiceHeader.csv file exists and has (.csv) extension.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The write operation failed.");
            System.out.println("Please ensure that the InvoiceHeader.csv file is not in use and is not corrupted.");
            e.printStackTrace();
        }

    }

    public static void saveLineToFile(InvoiceLine invoiceLinesRow, boolean isNewFile) {
        try {
            FileWriter csvWriter = new FileWriter(linesCsvFile, isNewFile);
            csvWriter.append(String.valueOf(invoiceLinesRow.getInvoiceNumber()));
            csvWriter.append(",");
            csvWriter.append(invoiceLinesRow.getItemName());
            csvWriter.append(",");
            csvWriter.append(String.valueOf(invoiceLinesRow.getItemPrice()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(invoiceLinesRow.getCount()));
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("The write operation failed.");
            System.out.println("Please make sure the InvoiceHeader.csv file exists and has (.csv) extension.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The write operation failed.");
            System.out.println("Please ensure that the InvoiceHeader.csv file is not in use and is not corrupted.");
            e.printStackTrace();
        }
    }

    public static void clearFiles() throws IOException {
        FileWriter csvWriter = new FileWriter(headersCsvFile, false);
        csvWriter = new FileWriter(linesCsvFile, false);
        csvWriter.flush();
        csvWriter.close();
    }
}
