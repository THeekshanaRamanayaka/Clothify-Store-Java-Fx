package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private String employeeId;
    private String title;
    private String employeeRole;
    private String employeeName;
    private String address;
    private String email;
    private String mobileNumber;
    private String company;
    private String loginPassword;
}
