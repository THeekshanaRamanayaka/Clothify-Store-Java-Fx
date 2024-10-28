package edu.icet.controller.common;

import com.jfoenix.controls.JFXTextField;
import edu.icet.model.RecentOrderDetails;
import edu.icet.model.RecentOrders;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.PlaceOrderService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderHistoryFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colEMail;

    @FXML
    private TableColumn<?, ?> colEmployee;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colOrderedDate;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<RecentOrders> tblOrderHistory;

    @FXML
    private TableView<RecentOrderDetails> tblOrderedItems;

    @FXML
    private Text txtDate;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private Text txtTime;

    private final PlaceOrderService placeOrderService = ServiceFactory.getInstance().getServiceType(ServiceType.PlaceOrder);
    private ObservableList<RecentOrders> recentOrdersObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderedDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        colEMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderedQuantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tblOrderHistory.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                loadOrderDetails(newValue.getOrderId());
            }
        });
        loadRecentOrders();

        txtOrderId.textProperty().addListener((_, _, newValue) -> searchRecentOrders(newValue));
    }

    private void searchRecentOrders(String searchQuery) {
        ObservableList<RecentOrders> filteredEmployees = FXCollections.observableArrayList();

        if (searchQuery == null || searchQuery.isEmpty()) {
            loadRecentOrders();
        } else {
            for (RecentOrders recentOrders : recentOrdersObservableList) {
                if (recentOrders.getEmployeeName().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredEmployees.add(recentOrders);
                }
            }
            loadTable(filteredEmployees);
        }
    }

    private void loadRecentOrders() {
        recentOrdersObservableList = placeOrderService.getAllOrders();
        loadTable(recentOrdersObservableList);
    }

    private void loadTable(ObservableList<RecentOrders> filteredOrderList) {
        tblOrderHistory.setItems(filteredOrderList);
    }

    private void loadOrderDetails(String orderId) {
        ObservableList<RecentOrderDetails> recentOrderDetailsObservableList = placeOrderService.searchOrderDetailByOrderId(orderId);
        tblOrderedItems.setItems(recentOrderDetailsObservableList);
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
    void btnSearchOnAction() {
        ObservableList<RecentOrders> recentOrdersObservableList = placeOrderService.searchOrder(txtOrderId.getText());
        tblOrderHistory.setItems(recentOrdersObservableList);
    }
}
