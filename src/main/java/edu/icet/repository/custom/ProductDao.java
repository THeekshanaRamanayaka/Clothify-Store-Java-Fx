package edu.icet.repository.custom;

import edu.icet.model.OrderDetails;
import edu.icet.model.Product;
import edu.icet.model.ProductDetails;
import edu.icet.repository.CrudDao;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends CrudDao<Product> {
    String findLastID();
    ObservableList<Product> searchProductByCategory(String value);
    ObservableList<ProductDetails> getProductDetailsById(String supplierId);
    Product getProductDetailsByDescription(String productDescription);
    boolean updateStock(List<OrderDetails> orderDetails) throws SQLException;
}
