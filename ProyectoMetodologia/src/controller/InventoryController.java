// Controller/InventoryController.java
package controller;

import model.Inventory;
import model.Product;
import model.Sale;
import view.MainWindow;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class InventoryController implements ActionListener, ListSelectionListener, DocumentListener {
    private MainWindow view;
    private Inventory model;

    public InventoryController(MainWindow view, Inventory model) {
        this.view = view;
        this.model = model;
        
        // Register listeners
        this.view.productPanel.btnSaveProduct.addActionListener(this);
        this.view.productPanel.btnNewProduct.addActionListener(this);
        this.view.productPanel.btnDeleteProduct.addActionListener(this);
        this.view.salesPanel.btnProcessSale.addActionListener(this);
        this.view.salesPanel.btnNewSale.addActionListener(this);
        this.view.reportsPanel.btnGenerateReport.addActionListener(this);
        this.view.productPanel.productTable.getSelectionModel().addListSelectionListener(this);
        this.view.productPanel.txtSearch.getDocument().addDocumentListener(this);
        
        // Load initial data
        loadProductsToComboBox();
        updateProductTable();
        updateSalesTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == view.productPanel.btnSaveProduct) {
            saveProduct();
        } else if (source == view.productPanel.btnNewProduct) {
            clearProductForm();
        } else if (source == view.productPanel.btnDeleteProduct) {
            deleteProduct();
        } else if (source == view.salesPanel.btnProcessSale) {
            processSale();
        } else if (source == view.salesPanel.btnNewSale) {
            clearSaleForm();
        } else if (source == view.reportsPanel.btnGenerateReport) {
            generateReport();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && e.getSource() == view.productPanel.productTable.getSelectionModel()) {
            int selectedRow = view.productPanel.productTable.getSelectedRow();
            if (selectedRow != -1) {
                int modelRow = view.productPanel.productTable.convertRowIndexToModel(selectedRow);
                String code = (String) view.productPanel.tableModel.getValueAt(modelRow, 0);
                Product product = model.findProductByCode(code);
                if (product != null) {
                    fillProductForm(product);
                }
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        filterProducts();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        filterProducts();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        filterProducts();
    }

    private void saveProduct() {
        String code = view.productPanel.txtCode.getText().trim();
        String name = view.productPanel.txtName.getText().trim();
        String description = view.productPanel.txtDescription.getText().trim();
        String stockStr = view.productPanel.txtStock.getText().trim();
        // Obtiene el texto del precio
        String priceStr = view.productPanel.txtPrice.getText().trim(); 
        String minStockStr = view.productPanel.txtMinStock.getText().trim();

        // Validation
        if (code.isEmpty() || name.isEmpty() || stockStr.isEmpty() || priceStr.isEmpty() || minStockStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please complete all required fields.", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Limpiar el símbolo de dólar si está presente
        String cleanPriceStr = priceStr.startsWith("$") ? priceStr.substring(1) : priceStr;
        
        // Validación de formato numérico para el precio limpio. 
        // Permite números enteros o decimales (con punto).
        if (!Pattern.matches("^\\d+(\\.\\d+)?$", cleanPriceStr)) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric value for the price (e.g., 10.50 or $10.50).", "Invalid Price Format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int stock = Integer.parseInt(stockStr);
            // Usa el string limpio para la conversión a double
            double price = Double.parseDouble(cleanPriceStr); 
            int minStock = Integer.parseInt(minStockStr);

            if (stock < 0 || price < 0 || minStock < 0) {
                JOptionPane.showMessageDialog(view, "Stock, price and minimum stock must be positive values.", "Invalid Values", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product existingProduct = model.findProductByCode(code);
            if (existingProduct != null && view.productPanel.txtCode.isEditable()) {
                JOptionPane.showMessageDialog(view, "A product with that code already exists.", "Duplicate Code", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (existingProduct != null && !view.productPanel.txtCode.isEditable()) {
                // Update existing product
                existingProduct.setName(name);
                existingProduct.setDescription(description);
                existingProduct.setStock(stock);
                existingProduct.setPrice(price);
                existingProduct.setMinStock(minStock);
                JOptionPane.showMessageDialog(view, "Product updated successfully.", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Create new product
                Product newProduct = new Product(code, name, description, stock, price, minStock);
                model.addProduct(newProduct);
                JOptionPane.showMessageDialog(view, "Product saved successfully.", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            }
            
            updateProductTable();
            loadProductsToComboBox();
            clearProductForm();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter valid integer values for stock and minimum stock.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void deleteProduct() {
        int selectedRow = view.productPanel.productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a product to delete.", "No Product Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.productPanel.productTable.convertRowIndexToModel(selectedRow);
        String code = (String) view.productPanel.tableModel.getValueAt(modelRow, 0);
        String name = (String) view.productPanel.tableModel.getValueAt(modelRow, 1);
        
        int response = JOptionPane.showConfirmDialog(view, 
            "Are you sure you want to delete the product '" + name + "'?", 
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            boolean deleted = model.deleteProduct(code);
            if (deleted) {
                JOptionPane.showMessageDialog(view, "Product deleted successfully.", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                updateProductTable();
                loadProductsToComboBox();
                clearProductForm();
            } else {
                JOptionPane.showMessageDialog(view, "Could not delete the product.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void processSale() {
        String productCode = (String) view.salesPanel.cmbProducts.getSelectedItem();
        String quantityStr = view.salesPanel.txtQuantity.getText().trim();

        if (productCode == null || productCode.equals("Select Product") || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a product and enter quantity.", "Incomplete Sale", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(view, "Quantity must be greater than zero.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product = model.findProductByCode(productCode);
            if (product == null) {
                JOptionPane.showMessageDialog(view, "Selected product not found.", "Product Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check stock availability
            if (product.getStock() < quantity) {
                JOptionPane.showMessageDialog(view, 
                    "Insufficient stock. Available: " + product.getStock(), 
                    "Stock Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Process sale
            double total = product.getPrice() * quantity;
            Sale sale = new Sale(productCode, product.getName(), quantity, product.getPrice(), total);
            model.addSale(sale);
            
            // Update product stock
            product.setStock(product.getStock() - quantity);
            
            JOptionPane.showMessageDialog(view, 
                "Sale processed successfully!\nTotal: $" + String.format("%.2f", total), 
                "Sale Complete", JOptionPane.INFORMATION_MESSAGE);
            
            updateProductTable();
            updateSalesTable();
            loadProductsToComboBox();
            clearSaleForm();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid quantity.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReport() {
        String reportType = (String) view.reportsPanel.cmbReportType.getSelectedItem();
        StringBuilder report = new StringBuilder();
        
        switch (reportType) {
            case "Sales Report":
                report.append("=== SALES REPORT ===\n");
                report.append("Date: ").append(LocalDate.now()).append("\n\n");
                double totalSales = 0;
                for (Sale sale : model.getAllSales()) {
                    report.append(String.format("Product: %s | Qty: %d | Total: $%.2f | Date: %s\n", 
                        sale.getProductName(), sale.getQuantity(), sale.getTotal(), sale.getDate()));
                    totalSales += sale.getTotal();
                }
                report.append(String.format("\nTOTAL SALES: $%.2f", totalSales));
                break;
                
            case "Low Stock Alert":
                report.append("=== LOW STOCK ALERT ===\n");
                report.append("Date: ").append(LocalDate.now()).append("\n\n");
                boolean hasLowStock = false;
                for (Product product : model.getAllProducts()) {
                    if (product.getStock() <= product.getMinStock()) {
                        report.append(String.format("Product: %s | Current Stock: %d | Minimum: %d\n", 
                            product.getName(), product.getStock(), product.getMinStock()));
                        hasLowStock = true;
                    }
                }
                if (!hasLowStock) {
                    report.append("No products with low stock.");
                }
                break;
                
            case "Product Catalog":
                report.append("=== PRODUCT CATALOG ===\n");
                report.append("Date: ").append(LocalDate.now()).append("\n\n");
                for (Product product : model.getAllProducts()) {
                    report.append(String.format("Code: %s | Name: %s | Stock: %d | Price: $%.2f\n", 
                        product.getCode(), product.getName(), product.getStock(), product.getPrice()));
                }
                break;
        }
        
        view.reportsPanel.txtReport.setText(report.toString());
    }

    private void updateProductTable() {
        view.productPanel.tableModel.setRowCount(0);
        for (Product product : model.getAllProducts()) {
            Object[] rowData = {
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getStock(),
                String.format("$%.2f", product.getPrice()),
                product.getMinStock(),
                product.getStock() <= product.getMinStock() ? "LOW STOCK" : "OK"
            };
            view.productPanel.tableModel.addRow(rowData);
        }
    }

    private void updateSalesTable() {
        view.salesPanel.tableModel.setRowCount(0);
        for (Sale sale : model.getAllSales()) {
            Object[] rowData = {
                sale.getProductCode(),
                sale.getProductName(),
                sale.getQuantity(),
                String.format("$%.2f", sale.getUnitPrice()),
                String.format("$%.2f", sale.getTotal()),
                sale.getDate()
            };
            view.salesPanel.tableModel.addRow(rowData);
        }
    }

    private void loadProductsToComboBox() {
        view.salesPanel.cmbProducts.removeAllItems();
        view.salesPanel.cmbProducts.addItem("Select Product");
        for (Product product : model.getAllProducts()) {
            if (product.getStock() > 0) {
                view.salesPanel.cmbProducts.addItem(product.getCode());
            }
        }
    }

    private void clearProductForm() {
        view.productPanel.txtCode.setText("");
        view.productPanel.txtName.setText("");
        view.productPanel.txtDescription.setText("");
        view.productPanel.txtStock.setText("");
        view.productPanel.txtPrice.setText("");
        view.productPanel.txtMinStock.setText("");
        view.productPanel.productTable.clearSelection();
        view.productPanel.txtCode.setEditable(true);
    }

    private void clearSaleForm() {
        view.salesPanel.txtQuantity.setText("");
        view.salesPanel.cmbProducts.setSelectedIndex(0);
    }

    private void fillProductForm(Product product) {
        view.productPanel.txtCode.setText(product.getCode());
        view.productPanel.txtName.setText(product.getName());
        view.productPanel.txtDescription.setText(product.getDescription());
        view.productPanel.txtStock.setText(String.valueOf(product.getStock()));
        
        // Muestra el precio en el formulario con el símbolo de dólar
        view.productPanel.txtPrice.setText(String.format("$%.2f", product.getPrice())); 
        view.productPanel.txtMinStock.setText(String.valueOf(product.getMinStock()));
        view.productPanel.txtCode.setEditable(false);
    }

    private void filterProducts() {
        String query = view.productPanel.txtSearch.getText();
        view.productPanel.filterTable(query);
    }
}