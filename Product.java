public class Product {
    private final int productId;
    private final String name;
    private final String category;
    private int quantity;
    private final double price;
    private final int reorderLevel;

    public Product(int productId, String name, String category, int quantity, double price, int reorderLevel) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.reorderLevel = reorderLevel;
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public int getReorderLevel() { return reorderLevel; }

    public void updateStock(int change) {
        this.quantity += change;
        if (this.quantity < 0) {
            this.quantity = 0; // Prevent negative stock
        }
    }

    public boolean isLowStock() {
        return this.quantity < reorderLevel;
    }
}

