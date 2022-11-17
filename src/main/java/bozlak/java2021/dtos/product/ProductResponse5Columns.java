package bozlak.java2021.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * productId, productName, unitPrice, 
 * unitsInStock, quantityPerUnit
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse5Columns {
    
    private int productId;
    private String productName;
    private double unitPrice;
    private int unitsInStock;
    private String quantityPerUnit;

}
