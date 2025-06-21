import java.util.Arrays;
import java.util.Comparator;

class Product {
    String productId;
    String productName;
    String category;

    public Product(String productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public String toString() {
        return "[" + productId + "] " + productName + " (" + category + ")";
    }
}

public class Main {

    public static Product linearSearch(Product[] products, String targetName) {
        for (Product p : products) {
            if (p.productName.equalsIgnoreCase(targetName)) {
                return p;
            }
        }
        return null;
    }

    public static Product binarySearch(Product[] products, String targetName) {
        int left = 0, right = products.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = targetName.compareToIgnoreCase(products[mid].productName);

            if (cmp == 0) return products[mid];
            if (cmp < 0) right = mid - 1;
            else left = mid + 1;
        }
        return null;
    }

    public static void main(String[] args) {
   
        Product[] products = {
            new Product("P001", "Laptop", "Electronics"),
            new Product("P002", "Shoes", "Fashion"),
            new Product("P003", "Mobile", "Electronics"),
            new Product("P004", "Watch", "Accessories"),
            new Product("P005", "T-shirt", "Fashion")
        };

     
        System.out.println("Linear Search for 'Mobile':");
        Product found = linearSearch(products, "Mobile");
        System.out.println(found != null ? "Found: " + found : "Not Found");
      
        Arrays.sort(products, Comparator.comparing(p -> p.productName.toLowerCase()));

        System.out.println("\nBinary Search for 'Mobile':");
        found = binarySearch(products, "Mobile");
        System.out.println(found != null ? "Found: " + found : "Not Found");
    }
}
