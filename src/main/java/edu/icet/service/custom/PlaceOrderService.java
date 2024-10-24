package edu.icet.service.custom;

import edu.icet.model.Orders;
import edu.icet.service.SuperService;

import java.sql.SQLException;

public interface PlaceOrderService extends SuperService {
    String generateOrderId();
    boolean placeOrder(Orders orders) throws SQLException;
}
