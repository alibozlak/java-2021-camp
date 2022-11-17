package bozlak.java2021.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import bozlak.java2021.business.abstracts.ProductService;
import bozlak.java2021.dtos.product.ProductResponse5Columns;
import bozlak.java2021.entities.concretes.Product;
import bozlak.java2021.repository.abstracts.ProductRepository;

@Service
public class ProductManager implements ProductService {

    private ProductRepository productRepository;

    public ProductManager(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse5Columns> getAll() {
        List<Product> products = this.productRepository.findAll();
        List<ProductResponse5Columns> productResponse5Columns_s = new ArrayList<>();
        for (Product product : products) {
            ProductResponse5Columns productResponse5Columns = new ProductResponse5Columns();
            productResponse5Columns.setProductId(product.getId());
            productResponse5Columns.setProductName(product.getName());
            productResponse5Columns.setUnitPrice(product.getUnitPrice());
            productResponse5Columns.setUnitsInStock(product.getUnitsInStock());
            productResponse5Columns.setQuantityPerUnit(product.getQuantityPerUnit());

            productResponse5Columns_s.add(productResponse5Columns);
        }
        return productResponse5Columns_s;
    }
    
}
