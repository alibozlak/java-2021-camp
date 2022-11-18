package bozlak.java2021.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import bozlak.java2021.business.abstracts.CategoryService;
import bozlak.java2021.business.abstracts.ProductService;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.ErrorResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.core.utilities.results.SuccessDataResult;
import bozlak.java2021.core.utilities.results.SuccessResult;
import bozlak.java2021.dtos.product.CreateProductRequest;
import bozlak.java2021.dtos.product.ProductResponseWithCategoryId;
import bozlak.java2021.dtos.product.UpdateProductRequest;
import bozlak.java2021.entities.concretes.Product;
import bozlak.java2021.repository.abstracts.ProductRepository;

@Service
public class ProductManager implements ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;

    public ProductManager(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public DataResult<List<ProductResponseWithCategoryId>> getAll() {
        List<Product> products = this.productRepository.findAll();
        List<ProductResponseWithCategoryId> productResponseWithCategoryIds = new ArrayList<>();
        for (Product product : products) {
            ProductResponseWithCategoryId productResponseWithCategoryId = new ProductResponseWithCategoryId();
            productResponseWithCategoryId.setProductId(product.getId());
            productResponseWithCategoryId.setProductName(product.getName());
            productResponseWithCategoryId.setUnitPrice(product.getUnitPrice());
            productResponseWithCategoryId.setUnitsInStock(product.getUnitsInStock());
            productResponseWithCategoryId.setQuantityPerUnit(product.getQuantityPerUnit());
            productResponseWithCategoryId.setCategoryId(product.getCategoryId());

            productResponseWithCategoryIds.add(productResponseWithCategoryId);
        }
        String message = "Tüm ürünler listelendi";
        return new SuccessDataResult<List<ProductResponseWithCategoryId>>(message,productResponseWithCategoryIds);
    }

    @Override
    public Result add(CreateProductRequest createProductRequest) {
        int categoryId = createProductRequest.getCategoryId();
        String productName = createProductRequest.getProductName();
        double unitPrice = createProductRequest.getUnitPrice();
        int unitsInStock = createProductRequest.getUnitsInStock();

        if (!existsCategoryById(categoryId)) {
            return new ErrorResult("ID'si " + categoryId + " olan bir kategori bulunmamaktadır!");
        }
        if (isProductNameEmpty(productName)) {
            return new ErrorResult("Ürün ismi boş geçilemez!");
        }
        if (!notNegativeUnitPrice(unitPrice)) {
            return new ErrorResult("Ürün fiyatı 0'dan küçük olamaz! Girilen fiyat : " + unitPrice);
        }
        if (!notNegativeInitsInStock(unitsInStock)) {
            String message = "Stoktaki ürün adedi 0'dan küçük olamaz. Girilen adet : " + unitsInStock;
            return new ErrorResult(message);
        }

        Product product = new Product();
        product.setCategoryId(categoryId);
        product.setName(productName);
        product.setUnitPrice(unitPrice);
        product.setUnitsInStock(unitsInStock);
        product.setQuantityPerUnit(createProductRequest.getQuantityPerUnit());

        this.productRepository.save(product);
        return new SuccessResult(productName + " adlı ürün eklendi!");
    }

    private boolean existsCategoryById(int categoryId) {
        return this.categoryService.existCategoryById(categoryId);
    }

    private boolean isProductNameEmpty(String productName) {
        if (productName.isBlank()) {
            return true;
        }
        return false;
    }

    private boolean notNegativeUnitPrice(double unitPrice) {
        if (unitPrice >= 0) {
            return true;
        }
        return false;
    }

    private boolean notNegativeInitsInStock(int unitsInStock) {
        if (unitsInStock >= 0) {
            return true;
        }
        return false;
    }

    private boolean existsProductById(int productId) {
        return this.productRepository.existsById(productId);
    }

    @Override
    public Result getById(int productId) {
        if (!existsProductById(productId)) {
            return new ErrorResult("ID'si " + productId + " olan bir ürün yok!");
        }

        ProductResponseWithCategoryId productResponseWithCategoryId = new ProductResponseWithCategoryId();
        Product product = this.productRepository.getReferenceById(productId);
        productResponseWithCategoryId.setProductId(productId);
        productResponseWithCategoryId.setCategoryId(product.getCategoryId());
        productResponseWithCategoryId.setProductName(product.getName());
        productResponseWithCategoryId.setUnitPrice(product.getUnitPrice());
        productResponseWithCategoryId.setUnitsInStock(product.getUnitsInStock());
        productResponseWithCategoryId.setQuantityPerUnit(product.getQuantityPerUnit());

        String message = "ID'si " + productId + " olan ürün getirildi";
        return new SuccessDataResult<ProductResponseWithCategoryId>(message, productResponseWithCategoryId);
    }

    @Override
    public Result update(UpdateProductRequest updateEntityRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result deleteById(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
