package edu.icet.repository.custom.impl;

import edu.icet.model.Employee;
import edu.icet.repository.custom.AuthDao;
import edu.icet.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDaoImpl implements AuthDao {
    @Override
    public Employee getEmployeeByEmail(String email) {
        String SQL = "SELECT * FROM employee where email= ?";

        try {
            ResultSet resultSet = CrudUtil.execute(SQL, email);

            while (resultSet.next()){
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
}
