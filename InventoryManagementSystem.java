import java.util.*;

class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    public String toString() {
        return "[" + productId + "] " + productName + " - Qty: " + quantity + ", Price: ₹" + price;
    }
}

class Inventory {
    private Map<String, Product> inventory = new HashMap<>();

    public void addProduct(Product p) {
        inventory.put(p.getProductId(), p);
        System.out.println("✅ Product added: " + p.getProductName());
    }

    public void updateProduct(String productId, int newQty, double newPrice) {
        Product p = inventory.get(productId);
        if (p != null) {
            p.setQuantity(newQty);
            p.setPrice(newPrice);
            System.out.println("Product updated: " + p.getProductName());
        } else {
            System.out.println("Product not found.");
        }
    }

    public void deleteProduct(String productId) {
        if (inventory.containsKey(productId)) {
            Product removed = inventory.remove(productId);
            System.out.println("Product removed: " + removed.getProductName());
        } else {
            System.out.println("Product not found.");
        }
    }

    public void showInventory() {
        System.out.println("\n Current Inventory:");
        for (Product p : inventory.values()) {
            System.out.println(p);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addProduct(new Product("P101", "Laptop", 10, 65000));
        inventory.addProduct(new Product("P102", "Mouse", 50, 499));

        inventory.updateProduct("P101", 8, 62000);

        inventory.deleteProduct("P102");

        inventory.showInventory();
    }
}
