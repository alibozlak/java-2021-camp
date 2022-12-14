package bozlak.java2021.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bozlak.java2021.business.abstracts.ProductService;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.dtos.product.CreateProductRequest;
import bozlak.java2021.dtos.product.ProductResponseWithCategory;
import bozlak.java2021.dtos.product.ProductResponseWithCategoryId;
import bozlak.java2021.dtos.product.UpdateProductRequest;
import bozlak.java2021.entities.dtos.ProductWithCategoryDto;

@RestController
@RequestMapping(path = "/api/products")
@CrossOrigin
public class ProductController {
    
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public DataResult<List<ProductResponseWithCategory>> getAll() {
        return this.productService.getAll();
    }

    @GetMapping(path = "/get-by-id/{productId}")
    public Result getById(@PathVariable int productId) {
        return this.productService.getById(productId);
    }

    @PostMapping
    public Result add(@RequestBody CreateProductRequest createProductRequest) {
        return this.productService.add(createProductRequest);
    }

    @DeleteMapping(path = "/delete-by-id/{productId}")
    public Result deleteById(@PathVariable int productId) {
        return this.productService.deleteById(productId);
    }

    @PutMapping
    public Result update(@RequestBody UpdateProductRequest updateProductRequest) {
        return this.productService.update(updateProductRequest);
    }

    @GetMapping(path = "/get-by-product-name")
    public DataResult<List<ProductResponseWithCategoryId>> getByProductName(@RequestParam String productName) {
        return this.productService.getByProductName(productName);
    }

    @GetMapping(path = "/get-by-product-name-and-category-id")
    public DataResult<List<ProductResponseWithCategoryId>> getByProductNameAndCategoryId
        (@RequestParam(name = "productName") String productName,@RequestParam(name = "categoryId") int categoryId) {
        return this.productService.getByProductNameAndCategoryId(productName, categoryId);
    }

    @GetMapping(path = "/get-by-product-name-contains")
    public DataResult<List<ProductResponseWithCategory>> 
        getByProductNameContains(@RequestParam String productNameSearch) {
        return this.productService.getByProductNameContains(productNameSearch);
    }

    @GetMapping(path = "/get-by-category-id-in")
    public DataResult<List<ProductResponseWithCategory>> getByCategoryIdIn(@RequestParam List<Integer> categoryIds) {
        return this.productService.getByCategoryIdIn(categoryIds);
    }

    @GetMapping(path =  "/get-all-by-pageable")
    DataResult<List<ProductResponseWithCategory>> getAll
        (@RequestParam(name = "pageNo") int pageNo, @RequestParam(name = "pageSize") int pageSize) {
        return this.productService.getAll(pageNo, pageSize);
    }

    @GetMapping(path = "/get-all-by-product-name-sorted-desc")
    public DataResult<List<ProductResponseWithCategory>> getAllByProductNameSortedDesc() {
        return this.productService.getAllByProductNameSortedDesc();
    }

    /**
     * 2021 Java - 9. Lecture
     */
    @GetMapping(path = "/get-product-with-category-names")
    public DataResult<List<ProductWithCategoryDto>> getProductsWithCategoryNames() {
        return this.productService.getProductWithCategoryDetails();
    }
}
