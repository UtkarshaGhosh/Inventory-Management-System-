import java.util.List;
import java.util.ArrayList;

public class Inventory {
    private final List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(int productId) {
        productList.removeIf(product -> product.getProductId() == productId);
    }

    public void updateProduct(int productId, int changeInQuantity) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                product.updateStock(changeInQuantity);
                break;
            }
        }
    }

    public Product getProductById(int productId) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return productList;
    }
}

