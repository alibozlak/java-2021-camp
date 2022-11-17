package bozlak.java2021.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bozlak.java2021.business.abstracts.ProductService;
import bozlak.java2021.dtos.product.ProductResponse5Columns;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse5Columns> getAll() {
        return this.productService.getAll();
    }
}
