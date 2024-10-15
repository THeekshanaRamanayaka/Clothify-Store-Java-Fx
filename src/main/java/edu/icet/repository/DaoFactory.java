package edu.icet.repository;

import edu.icet.repository.custom.impl.EmployeeDaoImpl;
import edu.icet.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance == null ? instance = new DaoFactory() : instance;
    }

    public <T extends SuperDao> T getDaoType(DaoType type) {
        switch (type) {
            case Employee: return (T) new EmployeeDaoImpl();
        }
        return null;
    }
}
