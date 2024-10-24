package edu.icet.service.custom;

import edu.icet.model.OrderDetails;
import edu.icet.model.Product;
import edu.icet.model.ProductDetails;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ProductService extends SuperService {
    String genetareProductId();
    boolean addProduct(Product product);
    boolean deleteProduct(String productId);
    ObservableList<Product> searchProduct(String value);
    boolean updateProduct(Product product);
    ObservableList<Product> getAllProducts();
    ObservableList<ProductDetails> searchProductById(String supplierId);
    ObservableList<String> getProductsDescriptions();
    Product searchProductByDescription(String productDescription);
    boolean updateStock(List<OrderDetails> orderDetails) throws SQLException;
}
