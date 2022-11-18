package bozlak.java2021.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    private int productId;
    private int categoryId;
    private String productName;
    private double unitPrice;
    private int unitsInStock;
    private String quantityPerUnit;
}
