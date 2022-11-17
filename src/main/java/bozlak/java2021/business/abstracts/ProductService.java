package bozlak.java2021.business.abstracts;

import java.util.List;

import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.dtos.product.CreateProductRequest;
import bozlak.java2021.dtos.product.ProductResponseWithCategoryId;

public interface ProductService {
    DataResult<List<ProductResponseWithCategoryId>> getAll();
    Result add(CreateProductRequest createProductRequest);
}
