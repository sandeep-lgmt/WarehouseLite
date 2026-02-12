import java.util.List;
import java.util.Scanner;
import model.Product;
import service.InventoryService;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryService service = new InventoryService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== WarehouseLite =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Quantity");
            System.out.println("4. Sell Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> updateQuantity();
                case 4 -> sellProduct();
                case 5 -> deleteProduct();
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        service.addProduct(new Product(0, name, quantity, price));
    }

    private static void viewProducts() {
        List<Product> products = service.getAllProducts();

        System.out.println("ID | Name | Qty | Price");
        for (Product p : products) {
            System.out.println(p.getId() + " | " + p.getName() + " | " +
                    p.getQuantity() + " | " + p.getPrice());
        }
    }

    private static void updateQuantity() {
        System.out.print("Product ID: ");
        int id = scanner.nextInt();

        System.out.print("New Quantity: ");
        int qty = scanner.nextInt();

        service.updateQuantity(id, qty);
    }

    private static void sellProduct() {
        System.out.print("Product ID: ");
        int id = scanner.nextInt();

        System.out.print("Quantity to sell: ");
        int qty = scanner.nextInt();

        service.sellProduct(id, qty);
    }

    private static void deleteProduct() {
        System.out.print("Product ID: ");
        int id = scanner.nextInt();

        service.deleteProduct(id);
    }
}
