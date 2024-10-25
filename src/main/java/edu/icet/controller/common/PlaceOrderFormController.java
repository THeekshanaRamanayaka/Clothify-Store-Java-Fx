package edu.icet.controller.common;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.*;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
import edu.icet.service.custom.EmployeeService;
import edu.icet.service.custom.PlaceOrderService;
import edu.icet.service.custom.ProductService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerContactNo;

    @FXML
    private JFXComboBox<String> cmbEmployeeId;

    @FXML
    private JFXComboBox<String> cmbItemDescription;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemDescription;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTotalDiscount;

    @FXML
    private TableView<CartTM> tblPlaceOrder;

    @FXML
    private JFXTextField txtCategory;

    @FXML
    private JFXTextField txtCustomerEMail;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private Text txtDate;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtEmployeeName;

    @FXML
    private JFXTextField txtProductId;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private JFXTextField txtQuantityOnHand;

    @FXML
    private JFXTextField txtSellingPrice;

    @FXML
    private JFXTextField txtSize;

    @FXML
    private Text txtTime;

    PlaceOrderService placeOrderService = ServiceFactory.getInstance().getServiceType(ServiceType.PlaceOrder);
    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);
    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.Customer);
    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);
    ObservableList<CartTM> cartTMObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        loadEmployeeIds();
        loadCustomerContacts();
        loadProductDescriptions();
        lblOrderId.setText(generateOrderId());

        cmbEmployeeId.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                searchEmployeeName(newValue);
            }
        });

        cmbCustomerContactNo.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                searchCustomerDetails(newValue);
            }
        });

        cmbItemDescription.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                searchProductDetails(newValue);
            }
        });

        tblPlaceOrder.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                setTextToValue(newValue);
            }
        });
    }

    private void setTextToValue(CartTM newValue) {
        txtProductId.setText(newValue.getProductId());
        cmbItemDescription.setValue(newValue.getProductDescription());
        txtQuantity.setText(String.valueOf(newValue.getOrderedQuantity()));
        txtSellingPrice.setText(String.valueOf(newValue.getPrice()));
        txtDiscount.setText(String.valueOf(newValue.getDiscount()));
        txtCategory.setText(newValue.getCategory());
        txtSize.setText(newValue.getSize());
    }

    private void searchProductDetails(String productDescription) {
        Product product = productService.searchProductByDescription(productDescription);
        txtProductId.setText(product.getProductId());
        txtSellingPrice.setText(String.valueOf(product.getPrice()));
        txtCategory.setText(product.getCategory());
        txtDiscount.setText(String.valueOf(product.getDiscount()));
        txtQuantityOnHand.setText(product.getQuantity());
        txtSize.setText(product.getSize());
    }

    private void loadProductDescriptions() {
        cmbItemDescription.setItems(productService.getProductsDescriptions());
    }

    private void searchCustomerDetails(String customerMobileNumber) {
        Customer customer = customerService.searchCustomerByMobileNumber(customerMobileNumber);
        txtCustomerId.setText(customer.getCustomerId());
        txtCustomerName.setText(customer.getCustomerName());
        txtCustomerEMail.setText(customer.getEmail());
    }

    private void loadCustomerContacts() {
        cmbCustomerContactNo.setItems(customerService.getCustomersContacts());
    }

    private void searchEmployeeName(String employeeId) {
        Employee employee = employeeService.searchEmployee(employeeId);
        txtEmployeeName.setText(employee.getEmployeeName());
    }

    private void loadEmployeeIds() {
        cmbEmployeeId.setItems(employeeService.getEmployeeIds());
    }

    private String generateOrderId() {
        String base = "#OD";
        int integer = Integer.parseInt(placeOrderService.generateOrderId());
        if (integer < 10) {
            base += "0000";
        } else if (integer < 100) {
            base += "000";
        } else if (integer < 1000) {
            base += "00";
        } else if (integer < 10000) {
            base += "0";
        }
        return base+(integer+1);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = simpleDateFormat.format(date);

        txtDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, _ -> {
            LocalTime localTime = LocalTime.now();
            txtTime.setText(localTime.getHour() + " : " + localTime.getMinute() + " : " + localTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    void btnAddToCartOnAction() {
        initializeTableColumns();
        CartTM newOrder = extractOrderDetails();

        if (isQuantityValid(newOrder.getOrderedQuantity())) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity!").show();
            return;
        }

        cartTMObservableList.add(newOrder);
        updateNetTotalAndDiscount();
        tblPlaceOrder.setItems(cartTMObservableList);
    }

    private void updateNetTotalAndDiscount() {
        double total = 0.0;
        double discount = 0.0;

        for (CartTM cartTM : cartTMObservableList) {
            total += cartTM.getAmount();
            discount += cartTM.getDiscount();
        }

        total -= discount;
        lblTotal.setText(String.format("%.2f", total));
        lblTotalDiscount.setText(String.format("%.2f", discount));
    }

    private boolean isQuantityValid(int quantity) {
        return Integer.parseInt(txtQuantityOnHand.getText()) < quantity;
    }

    private CartTM extractOrderDetails() {
        String productId = txtProductId.getText();
        String productDescription = cmbItemDescription.getValue();
        Integer quantity = Integer.parseInt(txtQuantity.getText());
        Double unitPrice = Double.parseDouble(txtSellingPrice.getText());
        LocalDate date = LocalDate.now();
        Double discount = Double.parseDouble(txtDiscount.getText());
        String category = txtCategory.getText();
        String size = txtSize.getText();
        Double amount = unitPrice * quantity;

        return new CartTM(productId, productDescription, quantity, unitPrice, date, discount, category, size, amount);
    }

    private void initializeTableColumns() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderedQuantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    @FXML
    void btnClearOnAction() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        cmbCustomerContactNo.setValue(null);
        txtCustomerEMail.setText("");

        txtProductId.setText("");
        cmbItemDescription.setValue(null);
        txtSellingPrice.setText("");
        txtCategory.setText("");
        txtDiscount.setText("");
        txtQuantityOnHand.setText("");
        txtSize.setText("");
        txtQuantity.setText("");
    }

    @FXML
    void btnUpdateOnAction() {
        initializeTableColumns();
        CartTM updatedOrder = extractOrderDetails();

        if (isQuantityValid(updatedOrder.getOrderedQuantity())) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity!").show();
            return;
        }

        boolean isUpdated = false;
        for (CartTM orderDetail : cartTMObservableList) {
            if (orderDetail.getProductId().equals(updatedOrder.getProductId())) {
                orderDetail.setProductDescription(updatedOrder.getProductDescription());
                orderDetail.setOrderedQuantity(updatedOrder.getOrderedQuantity());
                orderDetail.setPrice(updatedOrder.getPrice());
                orderDetail.setOrderDate(updatedOrder.getOrderDate());
                orderDetail.setDiscount(updatedOrder.getDiscount());
                orderDetail.setCategory(updatedOrder.getCategory());
                orderDetail.setSize(updatedOrder.getSize());
                orderDetail.setAmount(updatedOrder.getAmount());
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            updateNetTotalAndDiscount();
            tblPlaceOrder.refresh();
            new Alert(Alert.AlertType.INFORMATION, "Order updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Product ID not found!").show();
        }
    }

    @FXML
    void btnCancelOnAction() {
        resetForm();
    }

    @FXML
    void btnPlaceOrderOnAction() {
        String orderId = lblOrderId.getText();
        LocalDate orderDate = LocalDate.now();
        Double total = Double.valueOf(lblTotal.getText());
        String customerId = txtCustomerId.getText();
        String employeeId = cmbEmployeeId.getValue();
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        cartTMObservableList.forEach(obj -> orderDetailsList.add(new OrderDetails(
                orderId,
                obj.getProductId(),
                obj.getOrderedQuantity(),
                obj.getOrderDate(),
                obj.getAmount()
        )));

        Orders orders = new Orders(
                orderId,
                orderDate,
                total,
                customerId,
                employeeId,
                orderDetailsList
        );
        placeOrder(orders);
    }

    private void placeOrder(Orders orders) {
        try {
            if (placeOrderService.placeOrder(orders)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed !").show();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order not placed :(").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error placing order: " + e.getMessage()).show();
        }
    }

    private void resetForm() {
        btnClearOnAction();

        cartTMObservableList.clear();
        tblPlaceOrder.refresh();

        lblTotal.setText("0.00");
        lblTotalDiscount.setText("0.00");

        lblOrderId.setText(generateOrderId());

        loadEmployeeIds();
        loadCustomerContacts();
        loadProductDescriptions();
    }
}
