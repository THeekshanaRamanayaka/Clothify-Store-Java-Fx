package edu.icet.repository.custom.impl;

import edu.icet.model.Employee;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public boolean save(Employee employee) {
        String SQL = "INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            return CrudUtil.execute(SQL,
                    employee.getEmployeeId(),
                    employee.getTitle(),
                    employee.getEmployeeRole(),
                    employee.getEmployeeName(),
                    employee.getAddress(),
                    employee.getEmail(),
                    employee.getMobileNumber(),
                    employee.getCompany(),
                    employee.getLoginPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Employee> getAll() {
        ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM employee";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()) {
                employeeObservableList.add(new Employee(
                        resultSet.getString("employeeId"),
                        resultSet.getString("title"),
                        resultSet.getString("employeeRole"),
                        resultSet.getString("employeeName"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("mobileNumber"),
                        resultSet.getString("company"),
                        resultSet.getString("loginPassword")
                ));
            }
            return employeeObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM employee WHERE employeeId = ?";
        try {
            return CrudUtil.execute(SQL, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee search(String employeeId) {
        String SQL = "SELECT * FROM employee WHERE employeeId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, employeeId);
            if (resultSet.next()) {
                return new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(Employee employee) {
        String SQL = "UPDATE employee SET title = ?, employeeRole = ?, employeeName = ?, address = ?, email = ?, mobileNumber = ?, company = ?, loginPassword = ? WHERE employeeId = ?";
        try {
            return CrudUtil.execute(SQL,
                    employee.getTitle(),
                    employee.getEmployeeRole(),
                    employee.getEmployeeName(),
                    employee.getAddress(),
                    employee.getEmail(),
                    employee.getMobileNumber(),
                    employee.getCompany(),
                    employee.getLoginPassword(),
                    employee.getEmployeeId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findLastID() {
        String SQL = "select MAX(employeeId) from employee";
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
    public int getTotalEmployees() {
        String SQL = "SELECT COUNT(*) AS total FROM employee";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
