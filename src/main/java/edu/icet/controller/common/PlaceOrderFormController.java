package edu.icet.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class PlaceOrderFormController {

    @FXML
    private JFXButton btnOrderHistory;

    @FXML
    private JFXComboBox<?> cmbCustomerId;

    @FXML
    private JFXComboBox<?> cmbCustomerName;

    @FXML
    private JFXComboBox<?> cmbEmployeeId;

    @FXML
    private JFXComboBox<?> cmbEmployeeName;

    @FXML
    private JFXComboBox<?> cmbItemCode;

    @FXML
    private JFXComboBox<?> cmbItemDescription;

    @FXML
    private JFXComboBox<?> cmbSize;

    @FXML
    private JFXComboBox<?> cmbType;

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
    private TableView<?> tblPlaceOrder;

    @FXML
    private JFXTextField txtCustomerEMail;

    @FXML
    private JFXTextField txtCustomerMobileNumber;

    @FXML
    private Text txtDate;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private Label txtOrderId;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private JFXTextField txtQuantityOnHand;

    @FXML
    private JFXTextField txtSellingPrice;

    @FXML
    private Text txtTime;

    @FXML
    private Text txtTotal;

    @FXML
    private Text txtTotalDiscount;

    @FXML
    void btnAddToCartOnAction() {

    }

    @FXML
    void btnCancelOnAction() {

    }

    @FXML
    void btnClearOnAction() {

    }

    @FXML
    void btnPlaceOrderOnAction() {

    }

    @FXML
    void btnUpdateOnAction() {

    }

}
