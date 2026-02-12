package service;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Product;

public class InventoryService {

    private List<Product> productCache = new ArrayList<>();

    public InventoryService() {
        loadProductsFromDB();
    }

    // Load products into memory
    private void loadProductsFromDB() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                productCache.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }

            Collections.sort(productCache);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add product
    public void addProduct(Product product) {
        String sql = "INSERT INTO products(name, quantity, price) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity());
            ps.setDouble(3, product.getPrice());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                productCache.add(new Product(id, product.getName(), product.getQuantity(), product.getPrice()));
                Collections.sort(productCache);
            }

            System.out.println("Product added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Binary Search
    private Product binarySearch(int id) {
        int left = 0;
        int right = productCache.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Product midProduct = productCache.get(mid);

            if (midProduct.getId() == id)
                return midProduct;

            if (midProduct.getId() < id)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return null;
    }

    // View all
    public List<Product> getAllProducts() {
        return productCache;
    }

    // Update quantity
    public void updateQuantity(int id, int quantity) {
        if (quantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }

        Product product = binarySearch(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, id);
            ps.executeUpdate();

            product.setQuantity(quantity);

            if (quantity <= 5)
                System.out.println("Low stock alert for: " + product.getName());

            System.out.println("Quantity updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sell product (data integrity)
    public void sellProduct(int id, int quantity) {

        Product product = binarySearch(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        if (product.getQuantity() < quantity) {
            System.out.println("Insufficient stock.");
            return;
        }

        int newQuantity = product.getQuantity() - quantity;

        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, id);
            ps.executeUpdate();

            product.setQuantity(newQuantity);

            if (newQuantity <= 5)
                System.out.println("Low stock alert for: " + product.getName());

            System.out.println("Product sold successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public void deleteProduct(int id) {

        Product product = binarySearch(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            productCache.remove(product);

            System.out.println("Product deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
