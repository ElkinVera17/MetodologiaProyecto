// Model/Sale.java
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Sale {
    private String productCode;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double total;
    private String date;

    public Sale(String productCode, String productName, int quantity, double unitPrice, double total) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // Getters
    public String getProductCode() { return productCode; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotal() { return total; }
    public String getDate() { return date; }
}