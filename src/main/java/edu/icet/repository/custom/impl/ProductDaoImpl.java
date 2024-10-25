package edu.icet.repository.custom.impl;

import edu.icet.model.OrderDetails;
import edu.icet.model.Product;
import edu.icet.model.ProductDetails;
import edu.icet.repository.custom.ProductDao;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(Product product) {
        String SQL = "INSERT INTO product VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(SQL,
                    product.getProductId(),
                    product.getProductDescription(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getDiscount(),
                    product.getQuantity(),
                    product.getSize(),
                    product.getSupplierId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Product> getAll() {
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM product";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()) {
                productObservableList.add(new Product(
                        resultSet.getString("productId"),
                        resultSet.getString("productDescription"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("discount"),
                        resultSet.getString("quantity"),
                        resultSet.getString("size"),
                        resultSet.getString("supplierId")
                ));
            }
            return productObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM product WHERE productId = ?";
        try {
            return CrudUtil.execute(SQL, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product search(String id) {
        return null;
    }

    @Override
    public boolean update(Product product) {
        String SQL = "UPDATE product SET productDescription = ?, category = ?, price = ?, discount = ?, quantity = ?, size = ?, supplierId = ? WHERE productId = ?";
        try {
            return CrudUtil.execute(SQL,
                    product.getProductDescription(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getDiscount(),
                    product.getQuantity(),
                    product.getSize(),
                    product.getSupplierId(),
                    product.getProductId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findLastID() {
        String SQL = "SELECT MAX(productId) from product";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<Product> searchProductByCategory(String value) {
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM product WHERE category = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, value);
            while (resultSet.next()) {
                productObservableList.add(new Product(
                        resultSet.getString("productId"),
                        resultSet.getString("productDescription"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("discount"),
                        resultSet.getString("quantity"),
                        resultSet.getString("size"),
                        resultSet.getString("supplierId")
                ));
            }
            return productObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<ProductDetails> getProductDetailsById(String supplierId) {
        ObservableList<ProductDetails> productObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT productId, productDescription, quantity FROM product WHERE supplierId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, supplierId);
            while (resultSet.next()) {
                productObservableList.add(new ProductDetails(
                        resultSet.getString("productId"),
                        resultSet.getString("productDescription"),
                        resultSet.getString("quantity")
                ));
            }
            return  productObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductDetailsByDescription(String productDescription) {
        String SQL = "SELECT * FROM product WHERE productDescription = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, productDescription);
            if (resultSet.next()) {
                return new Product(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean updateStock(List<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails details : orderDetails) {
            boolean updated = CrudUtil.execute("UPDATE product SET quantity = quantity - ? WHERE productId = ?",
                    details.getOrderedQuantity(),
                    details.getProductId()
            );
            if (!updated) return false;
        }
        return true;
    }

    @Override
    public boolean updateQuantity(String productId, int incrementOrderedQuantity) throws SQLException {
        return CrudUtil.execute("UPDATE product SET quantity = quantity - ? WHERE productId = ?",
                incrementOrderedQuantity,
                productId
        );
    }

    @Override
    public boolean updateQuantityIncrement(String productId, int decrementOrderedQuantity) throws SQLException {
        return CrudUtil.execute("UPDATE product SET quantity = quantity + ? WHERE productId = ?",
                decrementOrderedQuantity,
                productId
        );
    }

    @Override
    public int getTotalStock() {
        String SQL = "SELECT SUM(quantity) AS totalQuantity FROM product";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
