package edu.icet.repository.custom.impl;

import edu.icet.model.OrderDetails;
import edu.icet.model.Orders;
import edu.icet.repository.custom.PlaceOrderDao;
import edu.icet.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderDaoImpl implements PlaceOrderDao {
    @Override
    public String findLastId() {
        String SQL = "SELECT MAX(orderId) from orders";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean save(Orders orders) throws SQLException {
        return CrudUtil.execute("INSERT INTO orders VALUES (?,?,?,?,?,?,?,?)",
                orders.getOrderId(),
                orders.getOrderDate(),
                orders.getTotal(),
                orders.getCustomerName(),
                orders.getMobileNumber(),
                orders.getEmail(),
                orders.getCustomerId(),
                orders.getEmployeeId()
        );
    }

    @Override
    public boolean saveOrderDetails(List<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails details : orderDetails) {
            boolean saved = CrudUtil.execute("INSERT INTO orderDetails VALUES (?,?,?,?,?,?,?,?,?,?)",
                    details.getOrderId(),
                    details.getProductId(),
                    details.getProductDescription(),
                    details.getOrderedQuantity(),
                    details.getPrice(),
                    details.getOrderDate(),
                    details.getDiscount(),
                    details.getCategory(),
                    details.getSize(),
                    details.getAmount()
            );
            if (!saved) return false;
        }
        return true;
    }
}
