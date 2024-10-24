package edu.icet.repository.custom;

import edu.icet.model.OrderDetails;
import edu.icet.model.Orders;
import edu.icet.repository.SuperDao;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderDao extends SuperDao {
    String findLastId();
    boolean save(Orders orders) throws SQLException;
    boolean saveOrderDetails(List<OrderDetails> orderDetails) throws SQLException;
}
