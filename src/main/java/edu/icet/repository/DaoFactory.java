package edu.icet.repository;

import edu.icet.repository.custom.impl.*;
import edu.icet.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance == null ? instance = new DaoFactory() : instance;
    }

    public <T extends SuperDao> T getDaoType(DaoType type) {
        return switch (type) {
            case Employee -> (T) new EmployeeDaoImpl();
            case Product -> (T) new ProductDaoImpl();
            case Customer -> (T) new CustomerDaoImpl();
            case Supplier -> (T) new SupplierDaoImpl();
            case PlaceOrder -> (T) new PlaceOrderDaoImpl();
        };
    }
}
