package edu.icet.repository.custom;

import edu.icet.model.Product;
import edu.icet.repository.CrudDao;
import javafx.collections.ObservableList;

public interface ProductDao extends CrudDao<Product> {
    String findLastID();
    ObservableList<Product> searchProductByCategory(String value);
}
