package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private String productId;
    private String productDescription;
    private String category;
    private Double price;
    private Double discount;
    private String quantity;
    private String size;
    private String supplierId;
}
