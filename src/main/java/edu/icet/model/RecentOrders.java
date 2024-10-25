package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecentOrders {
    private String orderId;
    private LocalDate orderDate;
    private Double total;
    private String customerName;
    private String mobileNumber;
    private String email;
    private String employeeName;
}
