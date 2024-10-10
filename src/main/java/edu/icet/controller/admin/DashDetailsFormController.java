package edu.icet.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class DashDetailsFormController {

    @FXML
    private LineChart<?, ?> chart;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colEmployee;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableView<?> tblRecentOrder;

    @FXML
    private Text txtDate;

    @FXML
    private Text txtEmployeeName;

    @FXML
    private Text txtNoOfCustomers;

    @FXML
    private Text txtTime;

    @FXML
    private Text txtTotalEarning;

    @FXML
    private Text txtTotalEmployees;

    @FXML
    private Text txtTotalStock;

}
