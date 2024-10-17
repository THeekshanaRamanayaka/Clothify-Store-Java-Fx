package edu.icet.service.custom;

import edu.icet.model.Product;
import edu.icet.model.ProductDetails;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface ProductService extends SuperService {
    String genetareProductId();
    boolean addProduct(Product product);
    boolean deleteProduct(String productId);
    ObservableList<Product> searchProduct(String value);
    boolean updateProduct(Product product);
    ObservableList<Product> getAllProducts();
    ObservableList<ProductDetails> searchProductById(String supplierId);
}
