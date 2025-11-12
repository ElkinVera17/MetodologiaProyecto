package App;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import controller.InventoryController;
import model.Inventory;
import view.MainWindow;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                
                Inventory model = new Inventory();
                MainWindow view = new MainWindow();
                new InventoryController(view, model);
                view.setVisible(true);
            }
        });
    }
}