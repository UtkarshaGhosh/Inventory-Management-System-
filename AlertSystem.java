import javax.swing.*;
import java.util.List;

public class AlertSystem {
    public void checkStockLevels(Inventory inventory) {
        List<Product> lowStockProducts = inventory.getAllProducts();
        StringBuilder alertMessage = new StringBuilder("Low Stock Items:\n");

        boolean hasLowStock = false;
        for (Product product : lowStockProducts) {
            if (product.isLowStock()) {
                alertMessage.append(product.getName()).append(" (Qty: ").append(product.getQuantity()).append(")\n");
                hasLowStock = true;
            }
        }

        if (hasLowStock) {
            JOptionPane.showMessageDialog(null, alertMessage.toString(), "Low Stock Alert", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No low stock items detected.", "Stock Check", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
