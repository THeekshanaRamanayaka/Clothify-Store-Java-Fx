package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginFormController {

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

        if (txtUserName.getText().equals("admin") && txtPassword.getText().equals("123")) {
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
        } else if (txtUserName.getText().equals("employee") && txtPassword.getText().equals("456")) {
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
