package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.EmailUtil;
import edu.icet.util.ServiceType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class PasswordResetFormController implements Initializable {

    @FXML
    private JFXButton btnConform;

    @FXML
    private JFXButton btnVerify;

    @FXML
    private JFXTextField txtConformPassword;

    @FXML
    private JFXTextField txtEMail;

    @FXML
    private JFXTextField txtNewPassword;

    @FXML
    private JFXTextField txtOTP;

    private Employee employee;
    private String generateOTP;
    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtOTP.setDisable(true);
        btnVerify.setDisable(true);
        txtNewPassword.setDisable(true);
        txtConformPassword.setDisable(true);
        btnConform.setDisable(true);
    }

    @FXML
    void btnSendOnAction() {
        String email = txtEMail.getText().trim();

        if (email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter email address !").show();
        }

        for (Employee employee1 : employeeService.getAllEmployees()) {
            if (employee1.getEmail().equals(email)) {
                employee = employee1;
                break;
            }
        }

        if (employee == null) {
            new Alert(Alert.AlertType.ERROR, "No account found with this email !").show();
        }

        generateOTP = generateOTP();
        if (EmailUtil.sendOTPEmail(email, generateOTP)) {
            new Alert(Alert.AlertType.CONFIRMATION, "OTP has been sent your email.").show();
            txtOTP.setDisable(false);
            btnVerify.setDisable(false);
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to send OTP :(").show();
        }
    }

    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    @FXML
    void btnVerifyOnAction() {
        String enteredOTP = txtOTP.getText().trim();
        if (enteredOTP.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter OTP !").show();
        }

        if (enteredOTP.equals(generateOTP)) {
            new Alert(Alert.AlertType.INFORMATION, "OTP verified successful !").show();
            txtNewPassword.setDisable(false);
            txtConformPassword.setDisable(false);
            btnConform.setDisable(false);
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid OTP :(").show();
        }
    }

    @FXML
    void btnConformOnAction() {
        String newPass = txtNewPassword.getText();
        String confirmPass = txtConformPassword.getText();

        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields.").show();
        }
        if (!newPass.equals(confirmPass)) {
            new  Alert(Alert.AlertType.ERROR, "Passwords don't match.").show();
            return;
        }

        employee.setLoginPassword(newPass);
        System.out.println(employee);
        if (employeeService.updateEmployee(employee)) {
            new Alert(Alert.AlertType.INFORMATION, "Password updated successfully !").show();
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login_form.fxml")))));
                stage.setTitle("Login page");
                stage.setResizable(false);
                stage.show();
                btnConform.getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update password").show();
        }
    }

    @FXML
    void btnCancelOnAction() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login_form.fxml")))));
            stage.setTitle("Login page");
            stage.setResizable(false);
            stage.show();
            btnConform.getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
