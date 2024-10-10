package edu.icet.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class AdminReportFormController {

    @FXML
    private LineChart<?, ?> chart;

    @FXML
    private DatePicker date;

    @FXML
    private Label lblSales;

    @FXML
    private Label lblSalesCount;

    @FXML
    private Text txtDate;

    @FXML
    private Text txtTime;

    @FXML
    void btnDailyReturns(ActionEvent event) {

    }

    @FXML
    void btnGetAnnualReports(ActionEvent event) {

    }

    @FXML
    void btnGetDailyReport(ActionEvent event) {

    }

    @FXML
    void btnGetMonthlyReports(ActionEvent event) {

    }

}
