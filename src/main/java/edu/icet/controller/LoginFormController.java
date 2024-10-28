package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Employee;
import edu.icet.service.custom.AuthService;
import edu.icet.service.custom.impl.AuthServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

public class LoginFormController {

    @Getter
    public static Employee CURRENT_EMPLOYEE;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private Text txtFogotPassword;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    void btnSignInOnAction() {
        AuthService authService = new AuthServiceImpl();
        Employee verifiedEmployee =  authService.authorizeLogin(txtUserName.getText(),txtPassword.getText());

        if(verifiedEmployee != null){
            CURRENT_EMPLOYEE = verifiedEmployee;
            if(verifiedEmployee.getEmployeeRole().equals("Admin")){
                Stage stage = new Stage();
                try {
                    stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/admin/dashboard_form.fxml")))));
                    stage.setTitle("Admin | Dashboard");
                    stage.setResizable(false);
                    stage.show();
                    btnSignIn.getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if(verifiedEmployee.getEmployeeRole().equals("Employee")){
                Stage stage = new Stage();
                try {
                    stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/employee/employee_dashboard_form.fxml")))));
                    stage.setTitle("Employee | Dashboard");
                    stage.setResizable(false);
                    stage.show();
                    btnSignIn.getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    void txtFogotPasswordOnMouseClick() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/password_reset_form.fxml")))));
            stage.setTitle("Password Reset");
            stage.setResizable(false);
            stage.show();
            txtFogotPassword.getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
