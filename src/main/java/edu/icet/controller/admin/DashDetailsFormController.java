package edu.icet.controller.admin;

import edu.icet.controller.LoginFormController;
import edu.icet.model.Employee;
import edu.icet.model.RecentOrders;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
import edu.icet.service.custom.EmployeeService;
import edu.icet.service.custom.PlaceOrderService;
import edu.icet.service.custom.ProductService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DashDetailsFormController implements Initializable {

    @FXML
    private LineChart<String, Number> chart;

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
    private TableView<RecentOrders> tblRecentOrder;

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

    private final PlaceOrderService placeOrderService = ServiceFactory.getInstance().getServiceType(ServiceType.PlaceOrder);
    private final CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.Customer);
    private final ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);
    private final EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);
    private final Employee employee = LoginFormController.CURRENT_EMPLOYEE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        txtEmployeeName.setText(employee.getEmployeeName()+"!");

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        loadTable();
        loadCardDetails();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Admin Over View");

        series.getData().add(new XYChart.Data<>("Total Earning", Double.parseDouble(txtTotalEarning.getText())));
        series.getData().add(new XYChart.Data<>("No Of Customers", Integer.parseInt(txtNoOfCustomers.getText())));
        series.getData().add(new XYChart.Data<>("Total Stock", Integer.parseInt(txtTotalStock.getText())));
        series.getData().add(new XYChart.Data<>("Total Employees", Integer.parseInt(txtTotalEmployees.getText())));

        chart.getData().add(series);
    }

    private void loadCardDetails() {
        Double totalEarning = placeOrderService.getTotalEarning();
        txtTotalEarning.setText(String.valueOf(totalEarning));

        int noOfCustomers = customerService.getNoOfCustomers();
        txtNoOfCustomers.setText(String.valueOf(noOfCustomers));

        int totalStock = productService.getTotalStock();
        txtTotalStock.setText(String.valueOf(totalStock));

        int totalEmployees = employeeService.getTotalEmployees();
        txtTotalEmployees.setText(String.valueOf(totalEmployees));
    }

    private void loadTable() {
        ObservableList<RecentOrders> recentOrdersObservableList = placeOrderService.getAllOrders();
        tblRecentOrder.setItems(recentOrdersObservableList);
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


}
