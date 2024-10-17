package edu.icet.service.custom;

import edu.icet.model.Supplier;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface SupplierService extends SuperService {
    boolean addSupplier(Supplier supplier);
    boolean deleteSupplier(String supplierId);
    Supplier searchSupplier(String supplierId);
    boolean updateSupplier(Supplier supplier);
    String generateEmployeeId();
    ObservableList<Supplier> getAllSupplier();
}
