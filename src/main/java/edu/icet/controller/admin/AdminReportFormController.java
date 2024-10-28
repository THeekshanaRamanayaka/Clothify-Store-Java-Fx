package edu.icet.controller.admin;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminReportFormController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();


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
    void btnDailyReturns() {

    }

    @FXML
    void btnGetAnnualReports() {

    }

    @FXML
    void btnGetDailyReport() {

    }

    @FXML
    void btnGetMonthlyReports() {

    }
}
