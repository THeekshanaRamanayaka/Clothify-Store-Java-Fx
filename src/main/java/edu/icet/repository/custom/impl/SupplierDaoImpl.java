package edu.icet.repository.custom.impl;

import edu.icet.model.Supplier;
import edu.icet.repository.custom.SupplierDao;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public boolean save(Supplier supplier) {
        String SQL = "INSERT INTO supplier VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(SQL,
                    supplier.getSupplierId(),
                    supplier.getTitle(),
                    supplier.getSupplierName(),
                    supplier.getEmail(),
                    supplier.getMobileNumber(),
                    supplier.getCompany()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Supplier> getAll() {
        ObservableList<Supplier> supplierObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM supplier";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()) {
                supplierObservableList.add(new Supplier(
                        resultSet.getString("supplierId"),
                        resultSet.getString("title"),
                        resultSet.getString("supplierName"),
                        resultSet.getString("email"),
                        resultSet.getString("mobileNumber"),
                        resultSet.getString("company")
                ));
            }
            return supplierObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM supplier WHERE supplierId = ?";
        try {
            return CrudUtil.execute(SQL, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier search(String supplierId) {
        String SQL = "SELECT * FROM supplier WHERE supplierId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, supplierId);
            if (resultSet.next()) {
                return new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(Supplier supplier) {
        String SQL = "UPDATE supplier SET title = ?, supplierName = ?, email = ?, mobileNumber = ?, company = ? WHERE supplierId = ?";
        try {
            return CrudUtil.execute(SQL,
                    supplier.getTitle(),
                    supplier.getSupplierName(),
                    supplier.getEmail(),
                    supplier.getMobileNumber(),
                    supplier.getCompany(),
                    supplier.getSupplierId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findLastId() {
        String SQL = "SELECT MAX(supplierId) from supplier";
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
}
