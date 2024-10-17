package edu.icet.controller.admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
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

public class EmployeeFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colEMail;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private Text lblEmployeeId;

    @FXML
    private TableView<Employee> tblEmployee;

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
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private Text txtTime;

    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        lblEmployeeId.setText(generateEmployeeId());

        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("employeeRole"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Mrs");
        cmbTitle.setItems(titles);

        ObservableList<String> roles = FXCollections.observableArrayList();
        roles.add("Admin");
        roles.add("Employee");
        cmbRole.setItems(roles);

        tblEmployee.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValue(newValue);
            }
        }));
        loadTable();
    }

    private String generateEmployeeId() {
        String base = "#E";
        int integer = Integer.parseInt(employeeService.generateEmployeeId());
        if (integer < 10) {
            base += "00";
        } else if (integer < 100) {
            base += "0";
        }
        return base+(integer+1);
    }

    private void setTextToValue(Employee newValue) {
        lblEmployeeId.setText(newValue.getEmployeeId());
        cmbTitle.setValue(newValue.getTitle());
        cmbRole.setValue(newValue.getEmployeeRole());
        txtName.setText(newValue.getEmployeeName());
        txtAddress.setText(newValue.getAddress());
        txtEMail.setText(newValue.getEmail());
        txtMobileNumber.setText(newValue.getMobileNumber());
        txtCity.setText(newValue.getCompany());
        txtPassword.setText(newValue.getLoginPassword());
        txtPassword.setDisable(true);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = simpleDateFormat.format(date);

        txtDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e-> {
            LocalTime localTime = LocalTime.now();
            txtTime.setText(localTime.getHour() + " : " + localTime.getMinute() + " : " + localTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    void btnAddEmployeeOnAction() {
        Employee employee = new Employee(
                lblEmployeeId.getText(),
                cmbTitle.getValue(),
                cmbRole.getValue(),
                txtName.getText(),
                txtAddress.getText(),
                txtEMail.getText(),
                txtMobileNumber.getText(),
                txtCity.getText(),
                txtPassword.getText()
        );

        if (isValidEmployeeInputDetails(employee)) {
            System.out.println(employee);
            if (employeeService.addEmployee(employee)) {
                new Alert(Alert.AlertType.INFORMATION, "Employee added successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee not Added :(").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Please fill out all fields correctly !").show();
        }
        loadTable();
        clearInputFields();
    }

    private boolean isValidEmployeeInputDetails(Employee employee) {
        System.out.println(employee);
        if (employee.getEmployeeId().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Employee ID is required!").show();
            return false;
        }
        if (employee.getTitle() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a title.").show();
            return false;
        }
        if (employee.getEmployeeRole() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a role.").show();
            return false;
        }
        if (employee.getEmployeeName().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name is required.").show();
            return false;
        }
        if (employee.getAddress().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Address is required.").show();
            return false;
        }
        if (!isValidEmail(employee.getEmail())) {
            new Alert(Alert.AlertType.ERROR, "Address is required.").show();
            return false;
        }
        if (!employee.getMobileNumber().matches("\\d{10}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid mobile Number. It must be  10 digits.").show();
            return false;
        }
        if (employee.getCompany().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Company is required.").show();
            return false;
        }
        if (!isValidPassword(employee.getLoginPassword())) {
            new Alert(Alert.AlertType.ERROR, "Password must be at least 8 characters long and contain at least one special character.").show();
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[!@#$%^&*()].*");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    @FXML
    void btnDeleteOnAction() {
        if (employeeService.deleteEmployee(lblEmployeeId.getText())) {
            new Alert(Alert.AlertType.INFORMATION,"Employee remove successful !").show();
        } else {
            new Alert(Alert.AlertType.ERROR).show();
        }
        loadTable();
        clearInputFields();
    }

    @FXML
    void btnSearchOnAction() {
        setTextToValue(employeeService.searchEmployee(txtSearch.getText()));
    }

    @FXML
    void btnUpdateOnAction() {
        Employee employee = new Employee(
                lblEmployeeId.getText(),
                cmbTitle.getValue(),
                cmbRole.getValue(),
                txtName.getText(),
                txtAddress.getText(),
                txtEMail.getText(),
                txtMobileNumber.getText(),
                txtCity.getText(),
                txtPassword.getText()
        );

        if (isValidEmployeeInputDetails(employee)) {
            if (employeeService.updateEmployee(employee)) {
                new Alert(Alert.AlertType.INFORMATION, "Employee updated successful !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee not updated :(").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Please fill out all fields correctly !").show();
        }
        loadTable();
        clearInputFields();
    }

    @FXML
    void btnClearOnAction() {
        clearInputFields();
    }

    private void clearInputFields() {
        lblEmployeeId.setText(generateEmployeeId());
        cmbTitle.setValue(null);
        cmbRole.setValue(null);
        txtName.setText("");
        txtAddress.setText("");
        txtEMail.setText("");
        txtMobileNumber.setText("");
        txtCity.setText("");
        txtPassword.setDisable(false);
        txtPassword.setText("");
    }

    private void loadTable() {
        ObservableList<Employee> employeeObservableList = employeeService.getAllEmployees();
        tblEmployee.setItems(employeeObservableList);
    }
}
