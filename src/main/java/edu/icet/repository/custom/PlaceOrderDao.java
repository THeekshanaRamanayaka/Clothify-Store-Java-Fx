package edu.icet.repository.custom;

import edu.icet.model.*;
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
    ObservableList<SalesReturns> getAllSoldOrders();
    ObservableList<SalesReturns> searchSoldOrderById(String orderId);
    SalesReturnsDetails getOrderedQuantity(String orderId, String productId);
    boolean updateOrderDetail(String orderId, String productId, int incrementOrderedQuantity, Double amount) throws SQLException;
    Double updateTotalAmount(String orderId);
    boolean updateTotal(String orderId, Double totalAmount) throws SQLException;
    boolean updateOrderDetailDecrement(String orderId, String productId, int decrementOrderedQuantity, Double amount) throws SQLException;
    boolean deleteProductDetail(String orderId, String productId) throws SQLException;
    Double getTotalEarning();
}
