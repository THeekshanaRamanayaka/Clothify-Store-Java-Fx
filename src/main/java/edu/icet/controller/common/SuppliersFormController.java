package edu.icet.controller.common;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class SuppliersFormController {

    @FXML
    private JFXComboBox<?> cmbTitle;

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colEMail;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private Text lblSupplierId;

    @FXML
    private TableView<?> tblItemDetails;

    @FXML
    private TableView<?> tblSupplier;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private Text txtDate;

    @FXML
    private JFXTextField txtEMail;

    @FXML
    private JFXTextField txtMobileNumber;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private Text txtTime;

    @FXML
    void btnAddSupplierOnAction() {

    }

    @FXML
    void btnDeleteOnAction() {

    }

    @FXML
    void btnSearchOnAction() {

    }

    @FXML
    void btnUpdateOnAction() {

    }

}
