package bozlak.java2021.dtos.product;

import bozlak.java2021.dtos.category.CategoryResponseWithoutPicture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * productId, productName, unitPrice, 
 * unitsInStock, quantityPerUnit and categoryResponseWithoutPicture
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseWithCategory {
    private int productId;
    private String productName;
    private double unitPrice;
    private int unitsInStock;
    private String quantityPerUnit;
    private CategoryResponseWithoutPicture categoryResponseWithoutPicture;
}
