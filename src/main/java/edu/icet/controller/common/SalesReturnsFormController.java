package edu.icet.controller.common;

import com.jfoenix.controls.JFXTextField;
import edu.icet.model.SalesReturns;
import edu.icet.model.SalesReturnsDetails;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.PlaceOrderService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

public class SalesReturnsFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<SalesReturns> tblSalesReturns;

    @FXML
    private Text txtDate;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtProductId;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private Text txtTime;

    @FXML
    private JFXTextField txtTotal;

    PlaceOrderService placeOrderService = ServiceFactory.getInstance().getServiceType(ServiceType.PlaceOrder);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderedQuantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        
        tblSalesReturns.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                setTextToValue(newValue);
            }
        });
        loadTable();
    }

    private void loadTable() {
        ObservableList<SalesReturns> recentOrdersObservableList = placeOrderService.getAllSoldOrders();
        tblSalesReturns.setItems(recentOrdersObservableList);
    }

    private void setTextToValue(SalesReturns newValue) {
        txtOrderId.setText(newValue.getOrderId());
        txtProductId.setText(newValue.getProductId());
        txtQuantity.setText(String.valueOf(newValue.getOrderedQuantity()));
        txtTotal.setText(String.valueOf(newValue.getAmount()));
        txtOrderId.setDisable(true);
        txtProductId.setDisable(true);
        txtTotal.setDisable(true);
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
    void btnClearOnAction() {
        txtOrderId.setText("");
        txtQuantity.setText("");
        txtTotal.setText("");
        tblSalesReturns.refresh();
        txtOrderId.setDisable(false);
        txtProductId.setDisable(false);
        txtTotal.setDisable(false);
    }

    @FXML
    void btnPlaceReturnsOnAction() {
        String orderId = txtOrderId.getText();
        String productId = txtProductId.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());

        try {
            if (placeOrderService.placeReturn(orderId, productId, quantity)) {
                new Alert(Alert.AlertType.INFORMATION, "Place return successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "place return unSuccessful :(").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error updating order: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction() {
        String orderId = txtOrderId.getText();
        String productId = txtProductId.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());

        SalesReturnsDetails salesReturnsDetails = placeOrderService.getOrderedQuantity(orderId, productId);
        Integer orderedQuantity = salesReturnsDetails.getOrderedQuantity();
        Double unitPrice = salesReturnsDetails.getPrice();
        Double amount = unitPrice*quantity;

        if (quantity > orderedQuantity) {
            int incrementOrderedQuantity = quantity - orderedQuantity;
            try {
                if (placeOrderService.updateOrder(orderId, productId, incrementOrderedQuantity, amount)) {
                    new Alert(Alert.AlertType.INFORMATION, "Order updated successful !").show();
                    tblSalesReturns.refresh();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Order not Updated :(").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error updating order: " + e.getMessage()).show();
            }
        } else {
            int decrementOrderedQuantity = orderedQuantity - quantity;
            try {
                if (placeOrderService.updateOrderDecrement(orderId, productId, decrementOrderedQuantity, amount)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order updated successful !").show();
                    tblSalesReturns.refresh();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Order not Updated :(").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error updating order: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSearchOnAction() {
        ObservableList<SalesReturns> recentOrdersObservableList = placeOrderService.searchSoldOrder(txtOrderId.getText());
        tblSalesReturns.setItems(recentOrdersObservableList);
    }
}
