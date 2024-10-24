package edu.icet.controller.common;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Customer;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
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

public class CustomerFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colEMail;

    @FXML
    private TableColumn<?, ?> colMobileNumber;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private Text lblCustomerId;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

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

    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.Customer);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        lblCustomerId.setText(generateCustomerId());

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));

        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Mrs");
        cmbTitle.setItems(titles);

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                setTextToValue(newValue);
            }
        });
        loadTable();
    }

    private String generateCustomerId() {
        String base = "#C";
        int integer = Integer.parseInt(customerService.generateCustomerId());
        if (integer < 10) {
            base += "000";
        } else if (integer < 100) {
            base += "00";
        } else if (integer < 1000) {
            base += "0";
        }
        return base+(integer + 1);
    }

    private void setTextToValue(Customer newValue) {
        lblCustomerId.setText(newValue.getCustomerId());
        cmbTitle.setValue(newValue.getTitle());
        txtName.setText(newValue.getCustomerName());
        txtAddress.setText(newValue.getAddress());
        txtEMail.setText(newValue.getEmail());
        txtMobileNumber.setText(newValue.getMobileNumber());
        txtCity.setText(newValue.getCity());
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
    void btnAddCustomerOnAction() {
        Customer customer = new Customer(
                lblCustomerId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                txtAddress.getText(),
                txtEMail.getText(),
                txtMobileNumber.getText(),
                txtCity.getText()
        );

        if (!isValidCustomerInputDetails(customer)) {
            if (customerService.addCustomer(customer)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer added successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer not added :(").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Please fill out all fields correctly !").show();
        }
        loadTable();
        clearInputFields();
    }

    private boolean isValidCustomerInputDetails(Customer customer) {
        if (customer.getCustomerId().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "CustomerId is required !").show();
            return false;
        }
        if (customer.getTitle() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a title.").show();
            return false;
        }
        if (customer.getCustomerName().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name is required.").show();
            return false;
        }
        if (customer.getAddress().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Address is required.").show();
            return false;
        }
        if (!isValidEmail(customer.getEmail())) {
            new Alert(Alert.AlertType.ERROR, "E-Mail Address is required.").show();
            return false;
        }
        if (!customer.getMobileNumber().matches("\\d{10}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid mobile Number. It must be  10 digits.").show();
            return false;
        }
        if (customer.getCity().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "City is required.").show();
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
        if (customerService.deleteCustomer(lblCustomerId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Customer remove successful !").show();
        } else {
            new Alert(Alert.AlertType.ERROR).show();
        }
        loadTable();
        clearInputFields();
    }

    @FXML
    void btnSearchOnAction() {
        setTextToValue(customerService.searchCustomer(txtSearch.getText()));
    }

    @FXML
    void btnUpdateOnAction() {
        Customer customer = new Customer(
                lblCustomerId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                txtAddress.getText(),
                txtEMail.getText(),
                txtMobileNumber.getText(),
                txtCity.getText()
        );

        if (isValidCustomerInputDetails(customer)) {
            if (customerService.updateCustomer(customer)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer not updated :(").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields correctly !").show();
        }
        loadTable();
        clearInputFields();
    }

    private void clearInputFields() {
        lblCustomerId.setText(generateCustomerId());
        cmbTitle.setValue(null);
        txtName.setText("");
        txtAddress.setText("");
        txtEMail.setText("");
        txtMobileNumber.setText("");
        txtCity.setText("");
    }

    private void loadTable() {
        ObservableList<Customer> customerObservableList = customerService.getAllCustomers();
        tblCustomers.setItems(customerObservableList);
    }
}
