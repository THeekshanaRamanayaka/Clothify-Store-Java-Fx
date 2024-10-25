package edu.icet.service.custom;

import edu.icet.model.*;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface PlaceOrderService extends SuperService {
    String generateOrderId();
    boolean placeOrder(Orders orders) throws SQLException;
    ObservableList<RecentOrderDetails> searchOrderDetailByOrderId(String orderId);
    ObservableList<RecentOrders> getAllOrders();
    ObservableList<RecentOrders> searchOrder(String orderId);
    ObservableList<SalesReturns> getAllSoldOrders();
    ObservableList<SalesReturns> searchSoldOrder(String orderId);
    SalesReturnsDetails getOrderedQuantity(String orderId, String productId);
    boolean updateOrder(String orderId, String productId, int incrementOrderedQuantity, Double amount) throws SQLException;
    boolean updateOrderDecrement(String orderId, String productId, int decrementOrderedQuantity, Double amount) throws SQLException;
    boolean placeReturn(String orderId, String productId, int quantity) throws SQLException;
}
