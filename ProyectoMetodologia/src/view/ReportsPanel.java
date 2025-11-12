// View/ReportsPanel.java
package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ReportsPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public JComboBox<String> cmbReportType;
    public JButton btnGenerateReport;
    public JTextArea txtReport;

    public ReportsPanel() {
        setBackground(new Color(45, 45, 45));
        setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Reports ", 
                  TitledBorder.LEADING, TitledBorder.TOP, 
                  new Font(Font.SANS_SERIF, Font.BOLD, 14), new Color(220, 220, 220)));
        
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // ComboBox
        cmbReportType = new JComboBox<>(new String[]{"Sales Report", "Low Stock Alert", "Product Catalog"});
        styleComboBox(cmbReportType);

        // Button
        btnGenerateReport = createButton("📊 Generate Report", new Color(108, 117, 125));

        // Text Area
        txtReport = new JTextArea(20, 50);
        styleTextArea(txtReport);
        txtReport.setEditable(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Control panel
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Report panel
        JPanel reportPanel = createReportPanel();
        mainPanel.add(reportPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(55, 55, 55));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Report Configuration ", 
                    TitledBorder.LEADING, TitledBorder.TOP, 
                    new Font(Font.SANS_SERIF, Font.BOLD, 12), new Color(200, 200, 200)));
        
        panel.add(createLabel("Report Type:"));
        cmbReportType.setPreferredSize(new Dimension(150, 30));
        panel.add(cmbReportType);
        panel.add(btnGenerateReport);
        
        return panel;
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(55, 55, 55));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Generated Report ", 
                    TitledBorder.LEADING, TitledBorder.TOP, 
                    new Font(Font.SANS_SERIF, Font.BOLD, 12), new Color(200, 200, 200)));
        
        JScrollPane reportScroll = new JScrollPane(txtReport);
        reportScroll.setBorder(new LineBorder(new Color(90, 90, 90)));
        reportScroll.setPreferredSize(new Dimension(0, 400));
        
        panel.add(reportScroll, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        label.setForeground(new Color(200, 200, 200));
        return label;
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
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setBackground(new Color(60, 60, 60));
        textArea.setForeground(new Color(220, 220, 220));
        textArea.setCaretColor(Color.WHITE);
        textArea.setBorder(new LineBorder(new Color(90, 90, 90), 2));
    }
}