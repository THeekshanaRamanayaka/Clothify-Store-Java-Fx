package edu.icet.repository.custom;

import edu.icet.model.OrderDetails;
import edu.icet.model.Orders;
import edu.icet.model.RecentOrderDetails;
import edu.icet.model.RecentOrders;
import edu.icet.repository.SuperDao;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderDao extends SuperDao {
    String findLastId();
    boolean save(Orders orders) throws SQLException;
    boolean saveOrderDetails(List<OrderDetails> orderDetails) throws SQLException;
    ObservableList<RecentOrderDetails> getOrderDetailsByOrderId(String orderId);
    ObservableList<RecentOrders> getAllOrders();
    ObservableList<RecentOrders> searchOrderById(String orderId);
}
