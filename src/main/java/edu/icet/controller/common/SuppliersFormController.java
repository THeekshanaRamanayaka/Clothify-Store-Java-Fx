package edu.icet.controller.common;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.ProductDetails;
import edu.icet.model.Supplier;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ProductService;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
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

public class SuppliersFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbTitle;

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
    private TableView<ProductDetails> tblItemDetails;

    @FXML
    private TableView<Supplier> tblSupplier;

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

    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.Supplier);
    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        lblSupplierId.setText(generateEmployeeId());

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colEMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Mrs");
        cmbTitle.setItems(titles);

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                setTextToValueSupplier(newValue);
                loadProductDetails(newValue.getSupplierId());
            }
        });
        loadTable();
    }

    private void loadProductDetails(String supplierId) {
        ObservableList<ProductDetails> productObservableList = productService.searchProductById(supplierId);
        tblItemDetails.setItems(productObservableList);
    }

    private String generateEmployeeId() {
        String base = "#S";
        int integer = Integer.parseInt(supplierService.generateEmployeeId());
        if (integer < 10) {
            base += "00";
        } else if (integer < 100) {
            base += "0";
        }
        return base+(integer+1);
    }

    private void setTextToValueSupplier(Supplier newValue) {
        lblSupplierId.setText(newValue.getSupplierId());
        cmbTitle.setValue(newValue.getTitle());
        txtName.setText(newValue.getSupplierName());
        txtEMail.setText(newValue.getEmail());
        txtMobileNumber.setText(newValue.getMobileNumber());
        txtCompany.setText(newValue.getCompany());
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
    void btnAddSupplierOnAction() {
        Supplier supplier = new Supplier(
                lblSupplierId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                txtEMail.getText(),
                txtMobileNumber.getText(),
                txtCompany.getText()
        );

        if (isValidSupplierInputDetails(supplier)) {
            if (supplierService.addSupplier(supplier)) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier added successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier not added :(").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields correctly !").show();
        }
        loadTable();
        clearInputFields();
    }

    private boolean isValidSupplierInputDetails(Supplier supplier) {
        if (supplier.getSupplierId().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Supplier ID required !").show();
            return false;
        }
        if (supplier.getTitle() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a title.").show();
            return false;
        }
        if (supplier.getSupplierName().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name is required.").show();
            return false;
        }
        if (!isValidEmail(supplier.getEmail())) {
            new Alert(Alert.AlertType.ERROR, "E-Mail Address is required.").show();
            return false;
        }
        if (!supplier.getMobileNumber().matches("\\d{10}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid mobile Number. It must be  10 digits.").show();
            return false;
        }
        if (supplier.getCompany().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Company is required.").show();
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    @FXML
    void btnDeleteOnAction() {
        if (supplierService.deleteSupplier(lblSupplierId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Employee remove successful !").show();
        } else {
            new Alert(Alert.AlertType.ERROR).show();
        }
        loadTable();
        clearInputFields();
    }

    @FXML
    void btnSearchOnAction() {
        setTextToValueSupplier(supplierService.searchSupplier(txtSearch.getText()));
    }

    @FXML
    void btnUpdateOnAction() {
        Supplier supplier = new Supplier(
                lblSupplierId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                txtEMail.getText(),
                txtMobileNumber.getText(),
                txtCompany.getText()
        );

        if (isValidSupplierInputDetails(supplier)) {
            if (supplierService.updateSupplier(supplier)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier not updated :(").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields correctly !").show();
        }
        loadTable();
        clearInputFields();
    }

    private void clearInputFields() {
        lblSupplierId.setText(generateEmployeeId());
        cmbTitle.setValue(null);
        txtName.setText("");
        txtEMail.setText("");
        txtMobileNumber.setText("");
        txtCompany.setText("");
    }

    private void loadTable() {
        ObservableList<Supplier> supplierObservableList = supplierService.getAllSupplier();
        tblSupplier.setItems(supplierObservableList);
    }
}
