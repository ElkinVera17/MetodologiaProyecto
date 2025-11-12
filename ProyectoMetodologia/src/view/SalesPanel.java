// View/SalesPanel.java
package view;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;

public class SalesPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public JComboBox<String> cmbProducts;
    public JTextField txtQuantity;
    public JButton btnProcessSale, btnNewSale;
    public JTable salesTable;
    public DefaultTableModel tableModel;

    public SalesPanel() {
        setBackground(new Color(45, 45, 45));
        setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Sales Management ", 
                  TitledBorder.LEADING, TitledBorder.TOP, 
                  new Font(Font.SANS_SERIF, Font.BOLD, 14), new Color(220, 220, 220)));
        
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // ComboBox
        cmbProducts = new JComboBox<>();
        styleComboBox(cmbProducts);

        // Text field
        txtQuantity = createTextField();

        // Buttons
        btnProcessSale = createButton("💰 Process Sale", new Color(40, 167, 69));
        btnNewSale = createButton("✨ New Sale", new Color(108, 117, 125));

        // Table
        String[] columnNames = {"Product Code", "Product Name", "Quantity", "Unit Price", "Total", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        salesTable = new JTable(tableModel);
        styleTable(salesTable);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        // Table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(55, 55, 55));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " New Sale ", 
                    TitledBorder.LEADING, TitledBorder.TOP, 
                    new Font(Font.SANS_SERIF, Font.BOLD, 12), new Color(200, 200, 200)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 0: Product selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createLabel("Select Product:"), gbc);
        
        gbc.gridx = 1;
        cmbProducts.setPreferredSize(new Dimension(200, 30));
        panel.add(cmbProducts, gbc);
        
        // Row 1: Quantity
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createLabel("Quantity:"), gbc);
        
        gbc.gridx = 1;
        panel.add(txtQuantity, gbc);
        
        // Row 2: Buttons
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(new Color(55, 55, 55));
        buttonPanel.add(btnProcessSale);
        buttonPanel.add(btnNewSale);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(55, 55, 55));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Sales History ", 
                    TitledBorder.LEADING, TitledBorder.TOP, 
                    new Font(Font.SANS_SERIF, Font.BOLD, 12), new Color(200, 200, 200)));
        
        JScrollPane tableScroll = new JScrollPane(salesTable);
        tableScroll.setBorder(new LineBorder(new Color(90, 90, 90)));
        tableScroll.setPreferredSize(new Dimension(0, 300));
        
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
        textField.setPreferredSize(new Dimension(100, 30));
        return textField;
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        comboBox.setBackground(new Color(60, 60, 60));
        comboBox.setForeground(new Color(220, 220, 220));
        comboBox.setBorder(new LineBorder(new Color(90, 90, 90), 2));
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
}