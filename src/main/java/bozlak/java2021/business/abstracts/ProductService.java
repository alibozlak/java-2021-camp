package bozlak.java2021.business.abstracts;

import java.util.List;

import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.dtos.product.CreateProductRequest;
import bozlak.java2021.dtos.product.ProductResponseWithCategory;
import bozlak.java2021.dtos.product.ProductResponseWithCategoryId;
import bozlak.java2021.dtos.product.UpdateProductRequest;

public interface ProductService extends BaseService<
    CreateProductRequest, UpdateProductRequest, ProductResponseWithCategory
    >{
    
    DataResult<List<ProductResponseWithCategoryId>> getByProductName(String productName);
    DataResult<List<ProductResponseWithCategoryId>> getByProductNameAndCategoryId(String productName, int categoryId);

    DataResult<List<ProductResponseWithCategory>> getByProductNameContains(String productNameSearch);
    DataResult<List<ProductResponseWithCategory>> getByCategoryIdIn(List<Integer> categoryIds);
}
