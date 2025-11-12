// Model/Inventory.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;
    private List<Sale> sales;

    public Inventory() {
        this.products = new ArrayList<>();
        this.sales = new ArrayList<>();
        // Add some sample data for testing
        initializeSampleData();
    }

    private void initializeSampleData() {
        products.add(new Product("P001", "Hammer", "Steel construction hammer", 15, 12.50, 5));
        products.add(new Product("P002", "Screwdriver Set", "6-piece screwdriver set", 8, 25.00, 3));
        products.add(new Product("P003", "Nails 2in", "Box of 100 2-inch nails", 20, 8.75, 10));
        products.add(new Product("P004", "Paint Brush", "3-inch paint brush", 12, 15.30, 4));
    }

    // Product management
    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean deleteProduct(String code) {
        return products.removeIf(product -> product.getCode().equals(code));
    }

    public Product findProductByCode(String code) {
        return products.stream()
                .filter(product -> product.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public Product[] getAllProducts() {
        return products.toArray(new Product[0]);
    }

    // Sales management
    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public Sale[] getAllSales() {
        return sales.toArray(new Sale[0]);
    }

    public double getTotalSales() {
        return sales.stream().mapToDouble(Sale::getTotal).sum();
    }
}