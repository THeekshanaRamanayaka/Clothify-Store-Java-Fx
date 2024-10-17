package edu.icet.service;

import edu.icet.service.custom.impl.CustomerServiceImpl;
import edu.icet.service.custom.impl.EmployeeServiceImpl;
import edu.icet.service.custom.impl.ProductServiceImpl;
import edu.icet.util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;
    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        return instance == null ? instance = new ServiceFactory() : instance;
    }

    public <T extends SuperService> T getServiceType(ServiceType type) {
        return switch (type) {
            case Employee -> (T) new EmployeeServiceImpl();
            case Product -> (T) new ProductServiceImpl();
            case Customer -> (T) new CustomerServiceImpl();
        };
    }
}
