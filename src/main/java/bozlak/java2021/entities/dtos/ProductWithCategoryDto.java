package bozlak.java2021.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithCategoryDto {
    private int productId;
    private String productName;
    private String categoryName;
}
