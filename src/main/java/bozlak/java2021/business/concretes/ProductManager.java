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
import bozlak.java2021.entities.concretes.Category;
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
            productResponseWithCategoryId.setCategoryId(product.getCategory().getId());

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
        Category category = this.categoryService.getCategoryById(categoryId);
        product.setCategory(category);
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
        productResponseWithCategoryId.setCategoryId(product.getCategory().getId());
        productResponseWithCategoryId.setProductName(product.getName());
        productResponseWithCategoryId.setUnitPrice(product.getUnitPrice());
        productResponseWithCategoryId.setUnitsInStock(product.getUnitsInStock());
        productResponseWithCategoryId.setQuantityPerUnit(product.getQuantityPerUnit());

        String message = "ID'si " + productId + " olan ürün getirildi";
        return new SuccessDataResult<ProductResponseWithCategoryId>(message, productResponseWithCategoryId);
    }

    @Override
    public Result update(UpdateProductRequest updateEntityRequest) {
        int productId = updateEntityRequest.getProductId();
        int categoryId = updateEntityRequest.getCategoryId();
        String productName = updateEntityRequest.getProductName();
        double unitPrice = updateEntityRequest.getUnitPrice();
        int unitsInStock = updateEntityRequest.getUnitsInStock();

        if (!existsProductById(productId)) {
            return new ErrorResult("ID'si "+ productId + " olan bir ürün yok!");
        }
        if (!existsCategoryById(categoryId)) {
            return new ErrorResult("ID'si " + categoryId + " olan bir kategori yok!");
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
        product.setId(productId);
        Category category = this.categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        product.setName(productName);
        product.setUnitPrice(unitPrice);
        product.setUnitsInStock(unitsInStock);
        product.setQuantityPerUnit(updateEntityRequest.getQuantityPerUnit());

        this.productRepository.save(product);
        return new SuccessResult(productName + " adlı ürün güncellendi");
    }

    @Override
    public Result deleteById(int id) {
        if (!existsProductById(id)) {
            return new ErrorResult("ID'si " + id + " olan bir ürün yok!");
        }

        this.productRepository.deleteById(id);
        return new SuccessResult("ID'si "+ id + " olan ürün veritabanından silindi");
    }

    @Override
    public DataResult<List<ProductResponseWithCategoryId>> getByProductName(String productName) {
        List<Product> products = this.productRepository.getByName(productName);
        List<ProductResponseWithCategoryId> productResponseWithCategoryIds = new ArrayList<>();
        for (Product product : products) {
            ProductResponseWithCategoryId productResponseWithCategoryId 
            = new ProductResponseWithCategoryId();

            productResponseWithCategoryId.setProductId(product.getId());
            productResponseWithCategoryId.setCategoryId(product.getCategory().getId());
            productResponseWithCategoryId.setProductName(product.getName());
            productResponseWithCategoryId.setUnitPrice(product.getUnitPrice());
            productResponseWithCategoryId.setUnitsInStock(product.getUnitsInStock());
            productResponseWithCategoryId.setQuantityPerUnit(product.getQuantityPerUnit());

            productResponseWithCategoryIds.add(productResponseWithCategoryId);
        }
        String message = "Adı '" + productName + "' olan ürünler getirildi.";
        return new SuccessDataResult<List<ProductResponseWithCategoryId>>(message, productResponseWithCategoryIds);
    }

    @Override
    public DataResult<List<ProductResponseWithCategoryId>> getByProductNameAndCategoryId(String productName,
            int categoryId) {
        List<Product> products = this.productRepository.getByProductNameAndCategory(productName, categoryId);
        List<ProductResponseWithCategoryId> productResponseWithCategoryIds = new ArrayList<>();
        for (Product product : products) {
            ProductResponseWithCategoryId productResponseWithCategoryId = new ProductResponseWithCategoryId();
            productResponseWithCategoryId.setProductId(product.getId());
            productResponseWithCategoryId.setCategoryId(product.getCategory().getId());
            productResponseWithCategoryId.setProductName(product.getName());
            productResponseWithCategoryId.setUnitPrice(product.getUnitPrice());
            productResponseWithCategoryId.setUnitsInStock(product.getUnitsInStock());
            productResponseWithCategoryId.setQuantityPerUnit(product.getQuantityPerUnit());

            productResponseWithCategoryIds.add(productResponseWithCategoryId);
        }
        String message = "Adı '" + productName + "' ve kategori ID'si " + categoryId + " olan ürünler getirildi.";
        return new SuccessDataResult<List<ProductResponseWithCategoryId>>(message, productResponseWithCategoryIds);
    }
    
}
