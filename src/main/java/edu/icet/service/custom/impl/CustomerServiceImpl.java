package edu.icet.service.custom.impl;

import edu.icet.model.Customer;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.CustomerDao;
import edu.icet.service.custom.CustomerService;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.Customer);

    @Override
    public boolean addCustomer(Customer customer) {
        customerDao.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        customerDao.delete(customerId);
        return true;
    }

    @Override
    public Customer searchCustomer(String customerId) {
        return customerDao.search(customerId);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        customerDao.update(customer);
        return true;
    }

    @Override
    public String generateCustomerId() {
        String lastId = customerDao.findLastId();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lastId);
        return (matcher.find()) ? matcher.group() : null;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        return customerDao.getAll();
    }

    @Override
    public ObservableList<String> getCustomersContacts() {
        ObservableList<String> customersContacts = FXCollections.observableArrayList();
        ObservableList<Customer> customerObservableList = getAllCustomers();
        customerObservableList.forEach(customer -> customersContacts.add(customer.getMobileNumber()));
        return customersContacts;
    }

    @Override
    public Customer searchCustomerByMobileNumber(String customerMobileNumber) {
        return customerDao.searchByMobileNumber(customerMobileNumber);
    }
}
