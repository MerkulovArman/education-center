package ru.neoflex.dao;

import ru.neoflex.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final String URL = "jdbc:derby://localhost:1527/Product";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println("get all product");
        for (Product product : dao.getAll()) {
            System.out.println(product);
        }
        System.out.println("--------------------------------------");

        System.out.println("get products where price is less 1000");
        for (Product product : dao.getAllByFilter(new BigDecimal("1000"))) {
            System.out.println(product);
        }
        System.out.println("--------------------------------------");


        Product newProduct = new Product("Tester", new BigDecimal("500.55"), null);
        if (dao.create(newProduct)) {
            System.out.println("Created successfully!");
        }
        System.out.println("--------------------------------------");

        System.out.println("get all product");
        for (Product product : dao.getAll()) {
            System.out.println(product);
        }
        System.out.println("--------------------------------------");

        System.out.println("delete product where count is null or count == 0");
        if (dao.deleteIsNull()) {
            System.out.println("Deleted successfully!");
        }
        System.out.println("--------------------------------------");

        System.out.println("get all product");
        for (Product product : dao.getAll()) {
            System.out.println(product);
        }
        System.out.println("--------------------------------------");

        System.out.println("delete product where count = ?");
        if (dao.delete(3L)) {
            System.out.println("Deleted successfully!");
        }
        System.out.println("--------------------------------------");

        System.out.println("get all product");
        for (Product product : dao.getAll()) {
            System.out.println(product);
        }
        System.out.println("--------------------------------------");

    }

    private boolean create(Product product) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO products (name, price, count) VALUES (?, ?, ?)")) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setObject(3, product.getCount());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("select * from PRODUCTS");
             ResultSet resultSet = statement.executeQuery()) {
            getProduct(products, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private List<Product> getAllByFilter(BigDecimal price) {
        List<Product> filteredProducts = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("select * from PRODUCTS where price > ?")) {
            statement.setBigDecimal(1, price);
            try (ResultSet resultSet = statement.executeQuery()) {
                getProduct(filteredProducts, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredProducts;
    }

    private boolean deleteIsNull() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("delete from products where count is null or count = 0")) {
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean delete(Long count) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("delete from products where count = ?")) {
            statement.setObject(1, count);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void getProduct(List<Product> products, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getBigDecimal("price"));
            product.setCount((Long) resultSet.getObject("count"));
            product.setCreated(resultSet.getTimestamp("created"));
            products.add(product);
        }
    }
}
