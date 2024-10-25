package edu.icet.service.custom.impl;

import edu.icet.model.OrderDetails;
import edu.icet.model.Product;
import edu.icet.model.ProductDetails;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.ProductDao;
import edu.icet.service.custom.ProductService;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductServiceImpl implements ProductService {
    ProductDao productDao = DaoFactory.getInstance().getDaoType(DaoType.Product);

    @Override
    public String genetareProductId() {
        String lastId = productDao.findLastID();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lastId);
        return (matcher.find()) ? matcher.group() : null;
    }

    @Override
    public boolean addProduct(Product product) {
        productDao.save(product);
        return true;
    }

    @Override
    public boolean deleteProduct(String productId) {
        productDao.delete(productId);
        return true;
    }

    @Override
    public ObservableList<Product> searchProduct(String value) {
        return productDao.searchProductByCategory(value);
    }

    @Override
    public boolean updateProduct(Product product) {
        productDao.update(product);
        return true;
    }

    @Override
    public ObservableList<Product> getAllProducts() {
        return productDao.getAll();
    }

    @Override
    public ObservableList<ProductDetails> searchProductById(String supplierId) {
        return productDao.getProductDetailsById(supplierId);
    }

    @Override
    public ObservableList<String> getProductsDescriptions() {
        ObservableList<String> productDescriptions = FXCollections.observableArrayList();
        ObservableList<Product> productObservableList = getAllProducts();
        productObservableList.forEach(product -> productDescriptions.add(product.getProductDescription()));
        return productDescriptions;
    }

    @Override
    public Product searchProductByDescription(String productDescription) {
        return productDao.getProductDetailsByDescription(productDescription);
    }

    @Override
    public boolean updateStock(List<OrderDetails> orderDetails) throws SQLException {
        return productDao.updateStock(orderDetails);
    }

    @Override
    public boolean updateQuantity(String productId, int incrementOrderedQuantity) throws SQLException {
        return productDao.updateQuantity(productId, incrementOrderedQuantity);
    }

    @Override
    public boolean updateQuantityIncrement(String productId, int decrementOrderedQuantity) throws SQLException {
        return productDao.updateQuantityIncrement(productId, decrementOrderedQuantity);
    }
}
