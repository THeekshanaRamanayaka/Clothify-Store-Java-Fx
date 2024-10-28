package edu.icet.service.custom;

import edu.icet.model.Employee;

public interface AuthService {
    Employee authorizeLogin(String email, String password);
}
