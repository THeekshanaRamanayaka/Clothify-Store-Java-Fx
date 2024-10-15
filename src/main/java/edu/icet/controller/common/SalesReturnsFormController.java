package edu.icet.controller.common;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class SalesReturnsFormController {

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
    private TableView<?> tblSalesReturns;

    @FXML
    private Text txtDate;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private Text txtTime;

    @FXML
    private JFXTextField txtTotal;

    @FXML
    void btnClearOnAction() {

    }

    @FXML
    void btnPlaceReturnsOnAction() {

    }

    @FXML
    void btnSaveOnAction() {

    }

    @FXML
    void btnSearchOnAction() {

    }
}
