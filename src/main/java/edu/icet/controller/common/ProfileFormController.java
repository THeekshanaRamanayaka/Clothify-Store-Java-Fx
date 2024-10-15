package edu.icet.controller.common;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ProfileFormController {

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

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

    @FXML
    void btnCancelOnAction() {

    }

    @FXML
    void btnUpdateOnAction() {

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
