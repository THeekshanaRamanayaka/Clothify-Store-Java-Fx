package edu.icet.controller.common;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Product;
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

public class ProductFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbSelectCategory;

    @FXML
    private JFXComboBox<String> cmbSize;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private JFXComboBox<String> cmbType;

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
    private TableView<Product> tblProduct;

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
    private JFXTextField txtSupplierName;

    @FXML
    private Text txtTime;

    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);
    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.Supplier);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        lblProductId.setText(generateProductId());

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        colType.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        ObservableList<String> selectCategory = FXCollections.observableArrayList();
        selectCategory.add("Kids");
        selectCategory.add("Gents");
        selectCategory.add("Ladies");
        cmbSelectCategory.setItems(selectCategory);

        ObservableList<String> category = FXCollections.observableArrayList();
        category.add("Kids");
        category.add("Gents");
        category.add("Ladies");
        cmbType.setItems(category);

        ObservableList<String> sizes = FXCollections.observableArrayList();
        sizes.add("XS");
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("XXL");
        cmbSize.setItems(sizes);

        tblProduct.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                setTextToValue(newValue);
            }
        });
        loadTable();

        loadSupplierIds();
        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                searchSupplierNames(newValue);
            }
        });
    }

    private void searchSupplierNames(String supplierId) {
        Supplier supplier = supplierService.searchSupplier(supplierId);
        txtSupplierName.setText(supplier.getSupplierName());
    }

    private void setTextToValue(Product newValue) {
        lblProductId.setText(newValue.getProductId());
        txtDescription.setText(newValue.getProductDescription());
        txtPrice.setText(String.valueOf(newValue.getPrice()));
        txtDiscount.setText(String.valueOf(newValue.getDiscount()));
        txtQuantity.setText(newValue.getQuantity());
        cmbType.setValue(newValue.getCategory());
        cmbSize.setValue(newValue.getSize());
        cmbSupplierId.setValue(newValue.getSupplierId());
    }

    private void loadSupplierIds() {
        cmbSupplierId.setItems(supplierService.getSupplierIds());
    }

    private String generateProductId() {
        String base = "#P";
        int integer = Integer.parseInt(productService.genetareProductId());
        if (integer < 10) {
            base += "000";
        } else if (integer < 100) {
            base += "00";
        } else if (integer < 1000) {
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
    void btnAddProductOnAction() {
        Product product = new Product(
                lblProductId.getId(),
                txtDescription.getText(),
                cmbType.getValue(),
                Double.parseDouble(txtPrice.getText()),
                Double.parseDouble(txtDiscount.getText()),
                txtQuantity.getText(),
                cmbSize.getValue(),
                cmbSupplierId.getValue()
        );

        if (isValidProductInputDetails(product)) {
            System.out.println(product);
            if (productService.addProduct(product)) {
                new Alert(Alert.AlertType.INFORMATION, "Product added successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Product not added :(").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please fill out all fields correctly !").show();
        }
        loadTable();
        clearInputFields();
    }

    private boolean isValidProductInputDetails(Product product) {
        return product.getProductId().isEmpty() &&
                product.getProductDescription().isEmpty() &&
                product.getPrice() == null &&
                product.getDiscount() == null &&
                product.getQuantity().isEmpty() &&
                product.getCategory().isEmpty() &&
                product.getSize().isEmpty() &&
                product.getSupplierId().isEmpty();
    }

    @FXML
    void btnDeleteOnAction() {
        if (productService.deleteProduct(lblProductId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Product remove successful !").show();
        } else {
            new Alert(Alert.AlertType.ERROR).show();
        }
        loadTable();
        clearInputFields();
    }

    @FXML
    void btnSearchOnAction() {
        ObservableList<Product> productObservableList = productService.searchProduct(cmbSelectCategory.getValue());
        tblProduct.setItems(productObservableList);
    }

    @FXML
    void btnUpdateOnAction() {
        Product product = new Product(
                lblProductId.getId(),
                txtDescription.getText(),
                cmbType.getValue(),
                Double.parseDouble(txtPrice.getText()),
                Double.parseDouble(txtDiscount.getText()),
                txtQuantity.getText(),
                cmbSize.getValue(),
                cmbSupplierId.getValue()
        );

        if (isValidProductInputDetails(product)) {
            if (productService.updateProduct(product)) {
                new Alert(Alert.AlertType.INFORMATION, "Product update successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Product not updated :(").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Please fill out all fields correctly !").show();
        }
        loadTable();
        clearInputFields();
    }

    private void clearInputFields() {
        lblProductId.setText(generateProductId());
        txtDescription.setText("");
        cmbType.setValue(null);
        txtPrice.setText("");
        txtDiscount.setText("");
        txtQuantity.setText("");
        cmbSize.setValue(null);
        cmbSupplierId.setValue(null);
    }

    private void loadTable() {
        ObservableList<Product> productObservableList = productService.getAllProducts();
        tblProduct.setItems(productObservableList);
    }
}
