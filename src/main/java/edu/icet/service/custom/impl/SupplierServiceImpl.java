package edu.icet.service.custom.impl;

import edu.icet.model.Supplier;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.SupplierDao;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.DaoType;
import javafx.collections.ObservableList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierServiceImpl implements SupplierService {
    SupplierDao supplierDao = DaoFactory.getInstance().getDaoType(DaoType.Supplier);

    @Override
    public boolean addSupplier(Supplier supplier) {
        supplierDao.save(supplier);
        return true;
    }

    @Override
    public boolean deleteSupplier(String supplierId) {
        supplierDao.delete(supplierId);
        return true;
    }

    @Override
    public Supplier searchSupplier(String supplierId) {
        return supplierDao.search(supplierId);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        supplierDao.update(supplier);
        return true;
    }

    @Override
    public String generateEmployeeId() {
        String lastId = supplierDao.findLastId();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lastId);
        return (matcher.find()) ? matcher.group() : null;
    }

    @Override
    public ObservableList<Supplier> getAllSupplier() {
        return supplierDao.getAll();
    }
}
