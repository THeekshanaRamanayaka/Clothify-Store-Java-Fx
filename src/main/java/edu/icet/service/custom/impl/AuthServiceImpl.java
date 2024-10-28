package edu.icet.service.custom.impl;

import edu.icet.model.Employee;
import edu.icet.repository.custom.AuthDao;
import edu.icet.repository.custom.impl.AuthDaoImpl;
import edu.icet.service.custom.AuthService;
import edu.icet.util.Encryption;

public class AuthServiceImpl implements AuthService {
    private final AuthDao authDao = new AuthDaoImpl();

    @Override
    public Employee authorizeLogin(String email, String password) {
        Employee employee = authDao.getEmployeeByEmail(email);

        //Check password
        boolean isValidPassword = Encryption.verifyPassword(password, employee.getLoginPassword());
        if(isValidPassword){
            return employee;
        }
        return null;
    }
}
