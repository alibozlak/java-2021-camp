package bozlak.java2021.business.abstracts;

import bozlak.java2021.dtos.product.CreateProductRequest;
import bozlak.java2021.dtos.product.ProductResponseWithCategoryId;
import bozlak.java2021.dtos.product.UpdateProductRequest;

public interface ProductService extends BaseService<
    CreateProductRequest, UpdateProductRequest, ProductResponseWithCategoryId
    >{
    // DataResult<List<ProductResponseWithCategoryId>> getAll();
    // Result getById(int productId);
    // Result add(CreateProductRequest createProductRequest);
}
