package edu.icet.service.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.model.Orders;
import edu.icet.model.RecentOrderDetails;
import edu.icet.model.RecentOrders;
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
}
