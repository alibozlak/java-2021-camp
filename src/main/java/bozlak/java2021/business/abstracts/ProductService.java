package bozlak.java2021.business.abstracts;

import java.util.List;

import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.dtos.product.CreateProductRequest;
import bozlak.java2021.dtos.product.ProductResponseWithCategory;
import bozlak.java2021.dtos.product.ProductResponseWithCategoryId;
import bozlak.java2021.dtos.product.UpdateProductRequest;
import bozlak.java2021.entities.dtos.ProductWithCategoryDto;

public interface ProductService extends BaseService<
    CreateProductRequest, UpdateProductRequest, ProductResponseWithCategory
    >{
    
    DataResult<List<ProductResponseWithCategoryId>> getByProductName(String productName);
    DataResult<List<ProductResponseWithCategoryId>> getByProductNameAndCategoryId(String productName, int categoryId);

    DataResult<List<ProductResponseWithCategory>> getByProductNameContains(String productNameSearch);
    DataResult<List<ProductResponseWithCategory>> getByCategoryIdIn(List<Integer> categoryIds);

    DataResult<List<ProductResponseWithCategory>> getAll(int pageNo, int pageSize);
    DataResult<List<ProductResponseWithCategory>> getAllByProductNameSortedDesc();

    /**
     * 2021 Java, 9. ders. DTO konusu
     */
    DataResult<List<ProductWithCategoryDto>> getProductWithCategoryDetails();
}
