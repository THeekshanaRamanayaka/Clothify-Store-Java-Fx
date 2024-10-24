package edu.icet.repository.custom.impl;

import edu.icet.model.Customer;
import edu.icet.repository.custom.CustomerDao;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer customer) {
        String SQL = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(SQL,
                    customer.getCustomerId(),
                    customer.getTitle(),
                    customer.getCustomerName(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getMobileNumber(),
                    customer.getCity()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM customer";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()) {
                customerObservableList.add(new Customer(
                        resultSet.getString("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("customerName"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("mobileNumber"),
                        resultSet.getString("city")
                ));
            }
            return customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM customer WHERE customerId = ?";
        try {
            return CrudUtil.execute(SQL,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer search(String customerId) {
        String SQL = "SELECT * FROM customer WHERE customerId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL,customerId);
            if (resultSet.next()) {
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(Customer customer) {
        String SQL = "UPDATE customer SET title = ?, customerName = ?, address = ?, email = ?, mobileNumber = ?, city = ? WHERE customerId = ?";
        try {
            return CrudUtil.execute(SQL,
                    customer.getTitle(),
                    customer.getCustomerName(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getMobileNumber(),
                    customer.getCity(),
                    customer.getCustomerId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findLastId() {
        String SQL = "SELECT MAX(customerId) FROM customer";
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
    public Customer searchByMobileNumber(String customerMobileNumber) {
        String SQL = "SELECT * FROM customer WHERE mobileNumber = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, customerMobileNumber);
            if (resultSet.next()) {
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
