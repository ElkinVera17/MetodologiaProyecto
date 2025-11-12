package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public ProductPanel productPanel;
    public SalesPanel salesPanel;
    public ReportsPanel reportsPanel;

    public MainWindow() {
        setTitle("🔧 Ferretería Carlín - Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(45, 45, 45));
        tabbedPane.setForeground(Color.WHITE);

        // Initialize panels
        productPanel = new ProductPanel();
        salesPanel = new SalesPanel();
        reportsPanel = new ReportsPanel();

        // Add tabs
        tabbedPane.addTab("📦 Product Management", productPanel);
        tabbedPane.addTab("💰 Sales", salesPanel);
        tabbedPane.addTab("📊 Reports", reportsPanel);

        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }
}