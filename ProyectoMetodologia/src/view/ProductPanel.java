// View/ProductPanel.java
package view;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;

public class ProductPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public JTextField txtCode, txtName, txtStock, txtPrice, txtMinStock, txtSearch;
    public JTextArea txtDescription;
    public JButton btnSaveProduct, btnNewProduct, btnDeleteProduct;
    public JTable productTable;
    public DefaultTableModel tableModel;
    public TableRowSorter<DefaultTableModel> sorter;

    public ProductPanel() {
        setBackground(new Color(45, 45, 45));
        setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Product Management ", 
                  TitledBorder.LEADING, TitledBorder.TOP, 
                  new Font(Font.SANS_SERIF, Font.BOLD, 14), new Color(220, 220, 220)));
        
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Text fields
        txtCode = createTextField();
        txtName = createTextField();
        txtStock = createTextField();
        txtPrice = createTextField();
        txtMinStock = createTextField();
        txtSearch = createTextField();

        // Text area
        txtDescription = new JTextArea(3, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        styleTextArea(txtDescription);

        // Buttons
        btnSaveProduct = createButton("💾 Save Product", new Color(0, 123, 255));
        btnNewProduct = createButton("✨ New Product", new Color(40, 167, 69));
        btnDeleteProduct = createButton("🗑️ Delete Product", new Color(220, 53, 69));

        // Table
        String[] columnNames = {"Code", "Name", "Description", "Stock", "Price", "Min Stock", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        productTable = new JTable(tableModel);
        styleTable(productTable);
        
        sorter = new TableRowSorter<>(tableModel);
        productTable.setRowSorter(sorter);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = createSearchPanel();
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        
        // Table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(55, 55, 55));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Product Form ", 
                    TitledBorder.LEADING, TitledBorder.TOP, 
                    new Font(Font.SANS_SERIF, Font.BOLD, 12), new Color(200, 200, 200)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 0: Code and Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createLabel("Product Code:"), gbc);
        
        gbc.gridx = 1;
        panel.add(txtCode, gbc);
        
        gbc.gridx = 2;
        panel.add(createLabel("Product Name:"), gbc);
        
        gbc.gridx = 3;
        panel.add(txtName, gbc);
        
        // Row 1: Description
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createLabel("Description:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 3;
        JScrollPane descScroll = new JScrollPane(txtDescription);
        descScroll.setPreferredSize(new Dimension(0, 60));
        panel.add(descScroll, gbc);
        gbc.gridwidth = 1;
        
        // Row 2: Stock, Price, Min Stock
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createLabel("Stock:"), gbc);
        
        gbc.gridx = 1;
        panel.add(txtStock, gbc);
        
        gbc.gridx = 2;
        panel.add(createLabel("Price:"), gbc);
        
        gbc.gridx = 3;
        panel.add(txtPrice, gbc);
        
        // Row 3: Min Stock and buttons
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(createLabel("Min Stock:"), gbc);
        
        gbc.gridx = 1;
        panel.add(txtMinStock, gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(new Color(55, 55, 55));
        buttonPanel.add(btnSaveProduct);
        buttonPanel.add(btnNewProduct);
        buttonPanel.add(btnDeleteProduct);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(55, 55, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        panel.add(createLabel("🔍 Search:"));
        panel.add(txtSearch);
        txtSearch.setPreferredSize(new Dimension(200, 30));
        
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(55, 55, 55));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Product List ", 
                    TitledBorder.LEADING, TitledBorder.TOP, 
                    new Font(Font.SANS_SERIF, Font.BOLD, 12), new Color(200, 200, 200)));
        
        JScrollPane tableScroll = new JScrollPane(productTable);
        tableScroll.setBorder(new LineBorder(new Color(90, 90, 90)));
        tableScroll.setPreferredSize(new Dimension(0, 250));
        
        panel.add(tableScroll, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        label.setForeground(new Color(200, 200, 200));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        textField.setBackground(new Color(60, 60, 60));
        textField.setForeground(new Color(220, 220, 220));
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(new LineBorder(new Color(90, 90, 90), 2));
        textField.setPreferredSize(new Dimension(150, 30));
        return textField;
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        textArea.setBackground(new Color(60, 60, 60));
        textArea.setForeground(new Color(220, 220, 220));
        textArea.setCaretColor(Color.WHITE);
        textArea.setBorder(new LineBorder(new Color(90, 90, 90), 2));
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(bgColor.darker(), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 35));
        return button;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setBackground(new Color(60, 60, 60));
        table.setForeground(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(0, 123, 255));
        table.setSelectionForeground(Color.WHITE);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        header.setBackground(new Color(30, 30, 30));
        header.setForeground(new Color(0, 123, 255));
        header.setReorderingAllowed(false);
    }

    public void filterTable(String query) {
        if (query.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }
}