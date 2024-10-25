package edu.icet.repository.custom.impl;

import edu.icet.model.*;
import edu.icet.repository.custom.PlaceOrderDao;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        return CrudUtil.execute("INSERT INTO orders VALUES (?,?,?,?,?)",
                orders.getOrderId(),
                orders.getOrderDate(),
                orders.getTotal(),
                orders.getCustomerId(),
                orders.getEmployeeId()
        );
    }

    @Override
    public boolean saveOrderDetails(List<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails details : orderDetails) {
            boolean saved = CrudUtil.execute("INSERT INTO orderDetails VALUES (?,?,?,?,?)",
                    details.getOrderId(),
                    details.getProductId(),
                    details.getOrderedQuantity(),
                    details.getOrderDate(),
                    details.getAmount()
            );
            if (!saved) return false;
        }
        return true;
    }

    @Override
    public ObservableList<RecentOrderDetails> getOrderDetailsByOrderId(String orderId) {
        ObservableList<RecentOrderDetails> recentOrderDetailsObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT T1.productId, T2.productDescription, T1.orderedQuantity, T2.price, T1.orderDate, T2.discount, T2.category, T2.size, T1.amount " +
                "FROM orderDetails T1 " +
                "INNER JOIN product T2 " +
                "ON T1.productId = T2.productId " +
                "WHERE orderId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, orderId);
            while (resultSet.next()) {
                recentOrderDetailsObservableList.add(new RecentOrderDetails(
                        resultSet.getString("productId"),
                        resultSet.getString("productDescription"),
                        resultSet.getInt("orderedQuantity"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("orderDate").toLocalDate(),
                        resultSet.getDouble("discount"),
                        resultSet.getString("category"),
                        resultSet.getString("size"),
                        resultSet.getDouble("amount")
                ));
            }
            return recentOrderDetailsObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<RecentOrders> getAllOrders() {
        ObservableList<RecentOrders> recentOrdersObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT T1.orderId, T1.orderDate, T1.total, T2.customerName, T2.mobileNumber, T2.email, T3.employeeName " +
                "FROM orders T1 " +
                "INNER JOIN customer T2 " +
                "ON T1.customerId = T2.customerId " +
                "INNER JOIN employee T3 " +
                "ON T1.employeeId = T3.employeeId";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()){
                recentOrdersObservableList.add(new RecentOrders(
                        resultSet.getString("orderId"),
                        resultSet.getDate("orderDate").toLocalDate(),
                        resultSet.getDouble("total"),
                        resultSet.getString("customerName"),
                        resultSet.getString("mobileNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("employeeName")
                ));
            }
            return recentOrdersObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<RecentOrders> searchOrderById(String orderId) {
        ObservableList<RecentOrders> recentOrdersObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT T1.orderId, T1.orderDate, T1.total, T2.customerName, T2.mobileNumber, T2.email, T3.employeeName " +
                "FROM orders T1 " +
                "INNER JOIN customer T2 " +
                "ON T1.customerId = T2.customerId " +
                "INNER JOIN employee T3 " +
                "ON T1.employeeId = T3.employeeId " +
                "WHERE T1.orderId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, orderId);
            while (resultSet.next()){
                recentOrdersObservableList.add(new RecentOrders(
                        resultSet.getString("orderId"),
                        resultSet.getDate("orderDate").toLocalDate(),
                        resultSet.getDouble("total"),
                        resultSet.getString("customerName"),
                        resultSet.getString("mobileNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("employeeName")
                ));
            }
            return recentOrdersObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<SalesReturns> getAllSoldOrders() {
        ObservableList<SalesReturns> salesReturnsObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT T1.productId, T2.productDescription, T1.orderedQuantity, T2.price, T1.orderDate, T2.discount, T2.category, T2.size, T1.amount, T1.orderId " +
                "FROM orderDetails T1 " +
                "INNER JOIN product T2 " +
                "ON T1.productId = T2.productId";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()) {
                salesReturnsObservableList.add(new SalesReturns(
                        resultSet.getString("productId"),
                        resultSet.getString("productDescription"),
                        resultSet.getInt("orderedQuantity"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("orderDate").toLocalDate(),
                        resultSet.getDouble("discount"),
                        resultSet.getString("category"),
                        resultSet.getString("size"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("orderId")
                ));
            }
            return salesReturnsObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<SalesReturns> searchSoldOrderById(String orderId) {
        ObservableList<SalesReturns> salesReturnsObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT T1.productId, T2.productDescription, T1.orderedQuantity, T2.price, T1.orderDate, T2.discount, T2.category, T2.size, T1.amount, T1.orderId " +
                "FROM orderDetails T1 " +
                "INNER JOIN product T2 " +
                "ON T1.productId = T2.productId " +
                "WHERE T1.orderId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, orderId);
            while (resultSet.next()) {
                salesReturnsObservableList.add(new SalesReturns(
                        resultSet.getString("productId"),
                        resultSet.getString("productDescription"),
                        resultSet.getInt("orderedQuantity"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("orderDate").toLocalDate(),
                        resultSet.getDouble("discount"),
                        resultSet.getString("category"),
                        resultSet.getString("size"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("orderId")
                ));
            }
            return salesReturnsObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SalesReturnsDetails getOrderedQuantity(String orderId, String productId) {
        String SQL = "SELECT T1.orderedQuantity, T2.price " +
                "FROM orderDetails T1 " +
                "INNER JOIN product T2 " +
                "ON T1.productId = T2.productId " +
                "WHERE T1.orderId = ? AND T1.productId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, orderId, productId);
            if (resultSet.next()) {
                return new SalesReturnsDetails(
                        resultSet.getInt("orderedQuantity"),
                        resultSet.getDouble("price")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean updateOrderDetail(String orderId, String productId, int incrementOrderedQuantity, Double amount) throws SQLException {
        String SQL = "UPDATE orderDetails SET orderedQuantity = orderedQuantity + ?, amount = ? WHERE orderId = ? and productId = ?";
        return CrudUtil.execute(SQL, incrementOrderedQuantity, amount, orderId, productId);
    }

    @Override
    public Double updateTotalAmount(String orderId) {
        String SQL = "SELECT SUM(amount) AS total FROM orderDetails WHERE orderId = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, orderId);
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    @Override
    public boolean updateTotal(String orderId, Double totalAmount) throws SQLException {
        String SQL = "UPDATE orders SET total = ? WHERE orderId = ?";
        return CrudUtil.execute(SQL, totalAmount, orderId);
    }

    @Override
    public boolean updateOrderDetailDecrement(String orderId, String productId, int decrementOrderedQuantity, Double amount) throws SQLException {
        String SQL = "UPDATE orderDetails SET orderedQuantity = orderedQuantity - ?, amount = ? WHERE orderId = ? and productId = ?";
        return CrudUtil.execute(SQL, decrementOrderedQuantity, amount, orderId, productId);
    }

    @Override
    public boolean deleteProductDetail(String orderId, String productId) throws SQLException {
        String SQL = "DELETE FROM orderDetails WHERE orderId = ? AND productId = ?";
        return CrudUtil.execute(SQL, orderId, productId);
    }

    @Override
    public Double getTotalEarning() {
        String SQL = "SELECT SUM(total) AS totalEarn FROM orders";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }
}
