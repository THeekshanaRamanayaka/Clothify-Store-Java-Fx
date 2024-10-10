package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PasswordResetFormController {

    @FXML
    private JFXButton btnConform;

    @FXML
    private JFXTextField txtConformPassword;

    @FXML
    private JFXTextField txtEMail;

    @FXML
    private JFXTextField txtNewPassword;

    @FXML
    private JFXTextField txtOTP;

    @FXML
    void btnCancelOnAction() {

    }

    @FXML
    void btnConformOnAction() {
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

    @FXML
    void btnSendOnAction() {

    }

    @FXML
    void btnVerifyOnAction() {

    }

}
