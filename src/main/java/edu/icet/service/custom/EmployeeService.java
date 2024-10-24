package edu.icet.service.custom;

import edu.icet.model.Employee;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface EmployeeService extends SuperService {
    boolean addEmployee(Employee employee);
    String generateEmployeeId();
    ObservableList<Employee> getAllEmployees();
    boolean deleteEmployee(String employeeId);
    Employee searchEmployee(String employeeId);
    boolean updateEmployee(Employee employee);
    ObservableList<String> getEmployeeIds();
}

