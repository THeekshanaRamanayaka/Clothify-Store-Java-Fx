package edu.icet.controller.admin;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private BorderPane boarderPane;

    @FXML
    private JFXButton btnLogout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadForm("/view/admin/dash_details_form.fxml");
    }

    private void loadForm(String path) {
        System.out.println(path);
        try {
            BorderPane borderPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            boarderPane.setCenter(borderPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDashboardOnAction() {
        loadForm("/view/admin/dash_details_form.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction() {
        loadForm("/view/common/place_order_form.fxml");
    }

    @FXML
    void btnOrderHistoryOnAction() {
        loadForm("/view/common/order_history_form.fxml");
    }

    @FXML
    void btnProductOnAction() {
        loadForm("/view/common/product_form.fxml");
    }

    @FXML
    void btnCustomersOnAction() {
        loadForm("/view/common/customer_form.fxml");
    }

    @FXML
    void btnReportsOnAction() {
        loadForm("/view/admin/admin_report_form.fxml");
    }

    @FXML
    void btnSalesReturnsOnAction() {
        loadForm("/view/common/sales_returns_form.fxml");
    }

    @FXML
    void btnSuppliersOnAction() {
        loadForm("/view/common/suppliers_form.fxml");
    }

    @FXML
    void btnEmployeesOnAction() {
        loadForm("/view/admin/employee_form.fxml");
    }

    @FXML
    void btnProfileOnAction() {
        loadForm("/view/common/profile_form.fxml");
    }

    @FXML
    void btnLogoutOnAction() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login_form.fxml")))));
            stage.setTitle("login page");
            stage.setResizable(false);
            stage.show();
            btnLogout.getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
