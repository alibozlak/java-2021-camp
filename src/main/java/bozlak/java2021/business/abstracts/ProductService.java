package bozlak.java2021.business.abstracts;

import java.util.List;

import bozlak.java2021.dtos.product.ProductResponse5Columns;

public interface ProductService {
    List<ProductResponse5Columns> getAll();
}
