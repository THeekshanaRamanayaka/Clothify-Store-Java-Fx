package edu.icet.service.custom.impl;

import edu.icet.model.Product;
import edu.icet.model.ProductDetails;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.ProductDao;
import edu.icet.service.custom.ProductService;
import edu.icet.util.DaoType;
import javafx.collections.ObservableList;

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
}
