package edu.icet.service.custom;

import edu.icet.model.Orders;
import edu.icet.model.RecentOrderDetails;
import edu.icet.model.RecentOrders;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface PlaceOrderService extends SuperService {
    String generateOrderId();
    boolean placeOrder(Orders orders) throws SQLException;
    ObservableList<RecentOrderDetails> searchOrderDetailByOrderId(String orderId);
    ObservableList<RecentOrders> getAllOrders();
    ObservableList<RecentOrders> searchOrder(String orderId);
}
