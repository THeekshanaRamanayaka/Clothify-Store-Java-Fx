package edu.icet.service.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.model.*;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.PlaceOrderDao;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.PlaceOrderService;
import edu.icet.service.custom.ProductService;
import edu.icet.util.DaoType;
import edu.icet.util.ServiceType;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderServiceImpl implements PlaceOrderService {
    PlaceOrderDao placeOrderDao = DaoFactory.getInstance().getDaoType(DaoType.PlaceOrder);
    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);

    @Override
    public String generateOrderId() {
        String lastId = placeOrderDao.findLastId();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lastId);
        return (matcher.find()) ? matcher.group() : null;
    }

    @Override
    public boolean placeOrder(Orders orders) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderSaved = placeOrderDao.save(orders);
            if (!isOrderSaved) {
                connection.rollback();
                return false;
            }

            boolean areOrderDetailsSaved = placeOrderDao.saveOrderDetails(orders.getOrderDetails());
            if (!areOrderDetailsSaved) {
                connection.rollback();
                return false;
            }

            boolean isStockUpdated = productService.updateStock(orders.getOrderDetails());
            if (!isStockUpdated) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<RecentOrderDetails> searchOrderDetailByOrderId(String orderId) {
        return placeOrderDao.getOrderDetailsByOrderId(orderId);
    }

    @Override
    public ObservableList<RecentOrders> getAllOrders() {
        return placeOrderDao.getAllOrders();
    }

    @Override
    public ObservableList<RecentOrders> searchOrder(String orderId) {
        return placeOrderDao.searchOrderById(orderId);
    }

    @Override
    public ObservableList<SalesReturns> getAllSoldOrders() {
        return placeOrderDao.getAllSoldOrders();
    }

    @Override
    public ObservableList<SalesReturns> searchSoldOrder(String orderId) {
        return placeOrderDao.searchSoldOrderById(orderId);
    }

    @Override
    public SalesReturnsDetails getOrderedQuantity(String orderId, String productId) {
        return placeOrderDao.getOrderedQuantity(orderId, productId);
    }

    @Override
    public boolean updateOrder(String orderId, String productId, int incrementOrderedQuantity, Double amount) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderDetailUpdate = placeOrderDao.updateOrderDetail(orderId, productId, incrementOrderedQuantity, amount);
            if (!isOrderDetailUpdate) {
                connection.rollback();
                return false;
            }

            Double totalAmount = placeOrderDao.updateTotalAmount(orderId);
            System.out.println(totalAmount);
            boolean isUpdateTotal = placeOrderDao.updateTotal(orderId, totalAmount);
            if (!isUpdateTotal) {
                connection.rollback();
                return false;
            }

            boolean isUpdateStock = productService.updateQuantity(productId, incrementOrderedQuantity);
            if (!isUpdateStock) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updateOrderDecrement(String orderId, String productId, int decrementOrderedQuantity, Double amount) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderDetailUpdate = placeOrderDao.updateOrderDetailDecrement(orderId, productId, decrementOrderedQuantity, amount);
            if (!isOrderDetailUpdate) {
                connection.rollback();
                return false;
            }

            Double totalAmount = placeOrderDao.updateTotalAmount(orderId);
            System.out.println(totalAmount);
            boolean isUpdateTotal = placeOrderDao.updateTotal(orderId, totalAmount);
            if (!isUpdateTotal) {
                connection.rollback();
                return false;
            }

            boolean isUpdateStock = productService.updateQuantityIncrement(productId, decrementOrderedQuantity);
            if (!isUpdateStock) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean placeReturn(String orderId, String productId, int quantity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderDeleted = placeOrderDao.deleteProductDetail(orderId, productId);
            if (!isOrderDeleted) {
                connection.rollback();
                return false;
            }

            Double totalAmount = placeOrderDao.updateTotalAmount(orderId);
            System.out.println(totalAmount);
            boolean isUpdateTotal = placeOrderDao.updateTotal(orderId, totalAmount);
            if (!isUpdateTotal) {
                connection.rollback();
                return false;
            }

            boolean isUpdateStock = productService.updateQuantityIncrement(productId, quantity);
            if (!isUpdateStock) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
