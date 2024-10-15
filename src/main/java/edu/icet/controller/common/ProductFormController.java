package edu.icet.controller.common;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class ProductFormController {

    @FXML
    private JFXComboBox<?> cmbSelectCategory;

    @FXML
    private JFXComboBox<?> cmbSize;

    @FXML
    private JFXComboBox<?> cmbSupplierId;

    @FXML
    private JFXComboBox<?> cmbSupplierName;

    @FXML
    private JFXComboBox<?> cmbType;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Text lblProductId;

    @FXML
    private TableView<?> tblProduct;

    @FXML
    private Text txtDate;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private Text txtTime;

    @FXML
    void btnAddProductOnAction() {

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
