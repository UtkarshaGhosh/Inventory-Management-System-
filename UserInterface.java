import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInterface {
    private final Inventory inventory;
    private final JFrame frame;
    private final JTextArea displayArea;
    private final JTextField searchField;
    private final JComboBox<String> filterComboBox;

    public UserInterface(Inventory inventory) {
        this.inventory = inventory;
        frame = new JFrame("Inventory Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        displayArea = new JTextArea(15, 50);
        displayArea.setEditable(false);
        panel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        searchField = new JTextField(15);
        filterComboBox = new JComboBox<>();
        updateCategoryFilter();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(_ -> updateDisplay());
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(filterComboBox);
        topPanel.add(searchButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        JButton editButton = new JButton("Edit Product");
        JButton removeButton = new JButton("Remove Product");
        JButton alertButton = new JButton("Check Low Stock");

        addButton.addActionListener(_ -> addProduct());
        editButton.addActionListener(_ -> editProduct());
        removeButton.addActionListener(_ -> removeProduct());
        alertButton.addActionListener(_ -> checkLowStock());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(alertButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.add(panel);
        frame.setVisible(true);
        updateDisplay();
    }
    
    private void addProduct() {
        ProductForm form = new ProductForm();
        if (form.showDialog()) {
            inventory.addProduct(form.getProduct());
            updateCategoryFilter();
            updateDisplay();
        }
    }

    private void editProduct() {
        String idStr = JOptionPane.showInputDialog("Enter Product ID to Edit:");
        if (idStr == null) return;
        try {
            int id = Integer.parseInt(idStr);
            Product product = inventory.getProductById(id);
            if (product != null) {
                ProductForm form = new ProductForm(product);
                if (form.showDialog()) {
                    inventory.updateProduct(id, form.getProduct().getQuantity());
                    updateDisplay();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removeProduct() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField idField = new JTextField();
        JTextField quantityField = new JTextField();
        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("Quantity to Remove:"));
        panel.add(quantityField);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Remove Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                inventory.updateProduct(id, -quantity);
                updateDisplay();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void checkLowStock() {
        AlertSystem alertSystem = new AlertSystem();
        alertSystem.checkStockLevels(inventory);
    }
    
    private void updateDisplay() {
        displayArea.setText("Product List:\n");
        List<Product> products = inventory.getAllProducts();
        for (Product product : products) {
            if (!filterComboBox.getSelectedItem().equals("All Categories") && !product.getCategory().equals(filterComboBox.getSelectedItem()))
                continue;
            if (!searchField.getText().isEmpty() && !product.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                continue;
            displayArea.append(product.getProductId() + " - " + product.getName() + " (Qty: " + product.getQuantity() + ")\n");
        }
    }
    
    private void updateCategoryFilter() {
        filterComboBox.removeAllItems();
        filterComboBox.addItem("All Categories");
        Set<String> categories = inventory.getAllProducts().stream().map(Product::getCategory).collect(Collectors.toSet());
        for (String category : categories) {
            filterComboBox.addItem(category);
        }
    }
}