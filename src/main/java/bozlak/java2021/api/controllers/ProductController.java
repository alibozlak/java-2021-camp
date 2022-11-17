package bozlak.java2021.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bozlak.java2021.business.abstracts.ProductService;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.dtos.product.CreateProductRequest;
import bozlak.java2021.dtos.product.ProductResponseWithCategoryId;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public DataResult<List<ProductResponseWithCategoryId>> getAll() {
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
}
