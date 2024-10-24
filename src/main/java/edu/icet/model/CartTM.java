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
public class CartTM {
    private String productId;
    private String productDescription;
    private Integer orderedQuantity;
    private Double price;
    private LocalDate orderDate;
    private Double discount;
    private String category;
    private String size;
    private Double amount;
}
