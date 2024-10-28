package edu.icet.service.custom.impl;

import edu.icet.model.Employee;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.DaoType;
import edu.icet.util.Encryption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDao employeeDao = DaoFactory.getInstance().getDaoType(DaoType.Employee);

    @Override
    public boolean addEmployee(Employee employee) {
        String hashedPassword = Encryption.hashPassword(employee.getLoginPassword());
        employee.setLoginPassword(hashedPassword);
        employeeDao.save(employee);
        return true;
    }

    @Override
    public String generateEmployeeId() {
        String lastID = employeeDao.findLastID();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lastID);
        return (matcher.find()) ? matcher.group() : null;
    }

    @Override
    public ObservableList<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    @Override
    public boolean deleteEmployee(String employeeId) {
        employeeDao.delete(employeeId);
        return true;
    }

    @Override
    public Employee searchEmployee(String employeeId) {
        return employeeDao.search(employeeId);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        String hashedPassword = Encryption.hashPassword(employee.getLoginPassword());
        employee.setLoginPassword(hashedPassword);
        employeeDao.update(employee);
        return true;
    }

    @Override
    public ObservableList<String> getEmployeeIds() {
        ObservableList<String> employeeIds = FXCollections.observableArrayList();
        ObservableList<Employee> employeeObservableList = getAllEmployees();
        employeeObservableList.forEach(employee -> employeeIds.add(employee.getEmployeeId()));
        return employeeIds;
    }

    @Override
    public int getTotalEmployees() {
        return employeeDao.getTotalEmployees();
    }
}
