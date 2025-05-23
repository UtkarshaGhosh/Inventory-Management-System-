import javax.swing.*;
import java.awt.*;

public class ProductForm {
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField categoryField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField reorderLevelField = new JTextField();
    private boolean confirmed = false;

    public ProductForm() {}

    public ProductForm(Product product) {
        idField.setText(String.valueOf(product.getProductId()));
        nameField.setText(product.getName());
        categoryField.setText(product.getCategory());
        quantityField.setText(String.valueOf(product.getQuantity()));
        priceField.setText(String.valueOf(product.getPrice()));
        reorderLevelField.setText(String.valueOf(product.getReorderLevel()));
        idField.setEditable(false);
    }

    public boolean showDialog() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("Product Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Reorder Level:"));
        panel.add(reorderLevelField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Product Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            confirmed = true;
        }
        return confirmed;
    }

    public Product getProduct() {
        if (!confirmed) return null;
        return new Product(
            Integer.parseInt(idField.getText()),
            nameField.getText(),
            categoryField.getText(),
            Integer.parseInt(quantityField.getText()),
            Double.parseDouble(priceField.getText()),
            Integer.parseInt(reorderLevelField.getText())
        );
    }
}
