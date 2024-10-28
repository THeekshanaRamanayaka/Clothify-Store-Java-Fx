package edu.icet.controller.common;

import com.jfoenix.controls.JFXTextField;
import edu.icet.controller.LoginFormController;
import edu.icet.model.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileFormController implements Initializable {

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private Text txtDate;

    @FXML
    private JFXTextField txtEMail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private Text txtResetPassword;

    @FXML
    private Text txtTime;

    private final EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);
    private final Employee currentEmployee = LoginFormController.CURRENT_EMPLOYEE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();

        txtName.setText(currentEmployee.getEmployeeName());
        txtAddress.setText(currentEmployee.getAddress());
        txtEMail.setText(currentEmployee.getEmail());
        txtContactNo.setText(currentEmployee.getMobileNumber());
        txtCompany.setText(currentEmployee.getCompany());
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
    void btnCancelOnAction() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/admin/dash_details_form.fxml")))));
            stage.setTitle("Dashboard");
            stage.setResizable(false);
            stage.show();
            txtResetPassword.getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction() {
        Employee employee = new Employee(
                currentEmployee.getEmployeeId(),
                currentEmployee.getTitle(),
                currentEmployee.getEmployeeRole(),
                txtName.getText(),
                txtAddress.getText(),
                txtEMail.getText(),
                txtContactNo.getText(),
                currentEmployee.getCompany(),
                currentEmployee.getLoginPassword()
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
            new Alert(Alert.AlertType.ERROR, "E-Mail Address is required.").show();
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
    void txtResetPasswordOnMouseClicked() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/password_reset_form.fxml")))));
            stage.setTitle("Reset Password");
            stage.setResizable(false);
            stage.show();
            txtResetPassword.getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
