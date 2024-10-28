package edu.icet.repository.custom;

import edu.icet.model.Employee;

public interface AuthDao {
    Employee getEmployeeByEmail(String email);
}
