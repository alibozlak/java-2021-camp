package bozlak.java2021.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bozlak.java2021.business.abstracts.CategoryService;
import bozlak.java2021.business.abstracts.ProductService;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.ErrorResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.core.utilities.results.SuccessDataResult;
import bozlak.java2021.core.utilities.results.SuccessResult;
import bozlak.java2021.dtos.category.CategoryResponseWithoutPicture;
import bozlak.java2021.dtos.product.CreateProductRequest;
import bozlak.java2021.dtos.product.ProductResponseWithCategory;
import bozlak.java2021.dtos.product.ProductResponseWithCategoryId;
import bozlak.java2021.dtos.product.UpdateProductRequest;
import bozlak.java2021.entities.concretes.Category;
import bozlak.java2021.entities.concretes.Product;
import bozlak.java2021.entities.dtos.ProductWithCategoryDto;
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
    public DataResult<List<ProductResponseWithCategory>> getAll() {
        List<ProductResponseWithCategory> productResponseWithCategories = new ArrayList<>();
        List<Product> products = this.productRepository.findAll();
        for (Product product : products) {
            ProductResponseWithCategory productResponseWithCategory = new ProductResponseWithCategory();
            productResponseWithCategory.setProductId(product.getId());
            productResponseWithCategory.setProductName(product.getName());
            productResponseWithCategory.setUnitPrice(product.getUnitPrice());
            productResponseWithCategory.setUnitsInStock(product.getUnitsInStock());
            productResponseWithCategory.setQuantityPerUnit(product.getQuantityPerUnit());
            CategoryResponseWithoutPicture categoryResponseWithoutPicture = new CategoryResponseWithoutPicture();
            categoryResponseWithoutPicture.setCategoryId(product.getCategory().getId());
            categoryResponseWithoutPicture.setCategoryName(product.getCategory().getName());
            categoryResponseWithoutPicture.setDescription(product.getCategory().getDescription());
            productResponseWithCategory.setCategoryResponseWithoutPicture(categoryResponseWithoutPicture);

            productResponseWithCategories.add(productResponseWithCategory);
        }
        String message = "T??m ??r??nler kategoriyle birlikte getirildi.";
        return new SuccessDataResult<List<ProductResponseWithCategory>>(message, productResponseWithCategories);
    }

    @Override
    public Result add(CreateProductRequest createProductRequest) {
        int categoryId = createProductRequest.getCategoryId();
        String productName = createProductRequest.getProductName();
        double unitPrice = createProductRequest.getUnitPrice();
        int unitsInStock = createProductRequest.getUnitsInStock();

        if (!existsCategoryById(categoryId)) {
            return new ErrorResult("ID'si " + categoryId + " olan bir kategori bulunmamaktad??r!");
        }
        if (isProductNameEmpty(productName)) {
            return new ErrorResult("??r??n ismi bo?? ge??ilemez!");
        }
        if (!notNegativeUnitPrice(unitPrice)) {
            return new ErrorResult("??r??n fiyat?? 0'dan k??????k olamaz! Girilen fiyat : " + unitPrice);
        }
        if (!notNegativeInitsInStock(unitsInStock)) {
            String message = "Stoktaki ??r??n adedi 0'dan k??????k olamaz. Girilen adet : " + unitsInStock;
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
        return new SuccessResult(productName + " adl?? ??r??n eklendi!");
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
            return new ErrorResult("ID'si " + productId + " olan bir ??r??n yok!");
        }

        ProductResponseWithCategory productResponseWithCategory = new ProductResponseWithCategory();
        Product product = this.productRepository.getReferenceById(productId);
        productResponseWithCategory.setProductId(product.getId());
        productResponseWithCategory.setProductName(product.getName());
        productResponseWithCategory.setUnitPrice(product.getUnitPrice());
        productResponseWithCategory.setUnitsInStock(product.getUnitsInStock());
        productResponseWithCategory.setQuantityPerUnit(product.getQuantityPerUnit());
        CategoryResponseWithoutPicture categoryResponseWithoutPicture = new CategoryResponseWithoutPicture();
        categoryResponseWithoutPicture.setCategoryId(product.getCategory().getId());
        categoryResponseWithoutPicture.setCategoryName(product.getCategory().getName());
        categoryResponseWithoutPicture.setDescription(product.getCategory().getDescription());
        productResponseWithCategory.setCategoryResponseWithoutPicture(categoryResponseWithoutPicture);

        String message = "ID'si " + productId + " olan ??r??n getirildi.";
        return new SuccessDataResult<ProductResponseWithCategory>(message, productResponseWithCategory);
    }

    @Override
    public Result update(UpdateProductRequest updateEntityRequest) {
        int productId = updateEntityRequest.getProductId();
        int categoryId = updateEntityRequest.getCategoryId();
        String productName = updateEntityRequest.getProductName();
        double unitPrice = updateEntityRequest.getUnitPrice();
        int unitsInStock = updateEntityRequest.getUnitsInStock();

        if (!existsProductById(productId)) {
            return new ErrorResult("ID'si "+ productId + " olan bir ??r??n yok!");
        }
        if (!existsCategoryById(categoryId)) {
            return new ErrorResult("ID'si " + categoryId + " olan bir kategori yok!");
        }
        if (isProductNameEmpty(productName)) {
            return new ErrorResult("??r??n ismi bo?? ge??ilemez!");
        }
        if (!notNegativeUnitPrice(unitPrice)) {
            return new ErrorResult("??r??n fiyat?? 0'dan k??????k olamaz! Girilen fiyat : " + unitPrice);
        }
        if (!notNegativeInitsInStock(unitsInStock)) {
            String message = "Stoktaki ??r??n adedi 0'dan k??????k olamaz. Girilen adet : " + unitsInStock;
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
        return new SuccessResult(productName + " adl?? ??r??n g??ncellendi");
    }

    @Override
    public Result deleteById(int id) {
        if (!existsProductById(id)) {
            return new ErrorResult("ID'si " + id + " olan bir ??r??n yok!");
        }

        this.productRepository.deleteById(id);
        return new SuccessResult("ID'si "+ id + " olan ??r??n veritaban??ndan silindi");
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
        String message = "Ad?? '" + productName + "' olan ??r??nler getirildi.";
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
        String message = "Ad?? '" + productName + "' ve kategori ID'si " + categoryId + " olan ??r??nler getirildi.";
        return new SuccessDataResult<List<ProductResponseWithCategoryId>>(message, productResponseWithCategoryIds);
    }

    @Override
    public DataResult<List<ProductResponseWithCategory>> getByProductNameContains(String productNameSearch) {
        List<Product> products = this.productRepository.getByNameContains(productNameSearch);
        List<ProductResponseWithCategory> productResponseWithCategories = new ArrayList<>();
        for (Product product : products) {
            ProductResponseWithCategory productResponseWithCategory = new ProductResponseWithCategory();
            productResponseWithCategory.setProductId(product.getId());
            productResponseWithCategory.setProductName(product.getName());
            productResponseWithCategory.setUnitPrice(product.getUnitPrice());
            productResponseWithCategory.setUnitsInStock(product.getUnitsInStock());
            productResponseWithCategory.setQuantityPerUnit(product.getQuantityPerUnit());
            CategoryResponseWithoutPicture categoryResponseWithoutPicture = new CategoryResponseWithoutPicture();
            categoryResponseWithoutPicture.setCategoryId(product.getCategory().getId());
            categoryResponseWithoutPicture.setCategoryName(product.getCategory().getName());
            categoryResponseWithoutPicture.setDescription(product.getCategory().getDescription());
            productResponseWithCategory.setCategoryResponseWithoutPicture(categoryResponseWithoutPicture);

            productResponseWithCategories.add(productResponseWithCategory);
        }
        String message = "??sminde '" + productNameSearch + "' ge??en ??r??nler getirildi.";
        return new SuccessDataResult<List<ProductResponseWithCategory>>(message, productResponseWithCategories);
    }

    @Override
    public DataResult<List<ProductResponseWithCategory>> getByCategoryIdIn(List<Integer> categoryIds) {
        List<Product> products = this.productRepository.getByCategory_IdIn(categoryIds);
        List<ProductResponseWithCategory> productResponseWithCategories = new ArrayList<>();
        for (Product product : products) {
            ProductResponseWithCategory productResponseWithCategory = new ProductResponseWithCategory();
            productResponseWithCategory.setProductId(product.getId());
            productResponseWithCategory.setProductName(product.getName());
            productResponseWithCategory.setUnitPrice(product.getUnitPrice());
            productResponseWithCategory.setUnitsInStock(product.getUnitsInStock());
            productResponseWithCategory.setQuantityPerUnit(product.getQuantityPerUnit());
            CategoryResponseWithoutPicture categoryResponseWithoutPicture = new CategoryResponseWithoutPicture();
            categoryResponseWithoutPicture.setCategoryId(product.getCategory().getId());
            categoryResponseWithoutPicture.setCategoryName(product.getCategory().getName());
            categoryResponseWithoutPicture.setDescription(product.getCategory().getDescription());
            productResponseWithCategory.setCategoryResponseWithoutPicture(categoryResponseWithoutPicture);

            productResponseWithCategories.add(productResponseWithCategory);
        }
        String message = "Kategori ID'si ";
        for (Integer categoryId : categoryIds) {
            message += categoryId + ", ";
        }
        message = message.substring(0, message.length()-2);
        message += " olan ??r??nler getirildi.";
        return new SuccessDataResult<List<ProductResponseWithCategory>>(message, productResponseWithCategories);
    }

    @Override
    public DataResult<List<ProductResponseWithCategory>> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        List<Product> products = this.productRepository.findAll(pageable).getContent();

        List<ProductResponseWithCategory> productResponseWithCategories = new ArrayList<>();
        for (Product product : products) {
            ProductResponseWithCategory productResponseWithCategory = new ProductResponseWithCategory();
            productResponseWithCategory.setProductId(product.getId());
            productResponseWithCategory.setProductName(product.getName());
            productResponseWithCategory.setUnitPrice(product.getUnitPrice());
            productResponseWithCategory.setUnitsInStock(product.getUnitsInStock());
            productResponseWithCategory.setQuantityPerUnit(product.getQuantityPerUnit());
            CategoryResponseWithoutPicture categoryResponseWithoutPicture = new CategoryResponseWithoutPicture();
            categoryResponseWithoutPicture.setCategoryId(product.getCategory().getId());
            categoryResponseWithoutPicture.setCategoryName(product.getCategory().getName());
            categoryResponseWithoutPicture.setDescription(product.getCategory().getDescription());
            productResponseWithCategory.setCategoryResponseWithoutPicture(categoryResponseWithoutPicture);

            productResponseWithCategories.add(productResponseWithCategory);
        }
        String message = "Her bir sayfas??nda " + pageSize + " kadar ??r??n olan sayfalardan " + pageNo + ". sayfa getirildi.";
        return new SuccessDataResult<List<ProductResponseWithCategory>>(message, productResponseWithCategories);
    }

    @Override
    public DataResult<List<ProductResponseWithCategory>> getAllByProductNameSortedDesc() {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        List<Product> products = this.productRepository.findAll(sort);

        List<ProductResponseWithCategory> productResponseWithCategories = new ArrayList<>();
        for (Product product : products) {
            ProductResponseWithCategory productResponseWithCategory = new ProductResponseWithCategory();
            productResponseWithCategory.setProductId(product.getId());
            productResponseWithCategory.setProductName(product.getName());
            productResponseWithCategory.setUnitPrice(product.getUnitPrice());
            productResponseWithCategory.setUnitsInStock(product.getUnitsInStock());
            productResponseWithCategory.setQuantityPerUnit(product.getQuantityPerUnit());
            CategoryResponseWithoutPicture categoryResponseWithoutPicture = new CategoryResponseWithoutPicture();
            categoryResponseWithoutPicture.setCategoryId(product.getCategory().getId());
            categoryResponseWithoutPicture.setCategoryName(product.getCategory().getName());
            categoryResponseWithoutPicture.setDescription(product.getCategory().getDescription());
            productResponseWithCategory.setCategoryResponseWithoutPicture(categoryResponseWithoutPicture);

            productResponseWithCategories.add(productResponseWithCategory);
        }
        String message = "??r??nler ismine g??re sondan ba??a do??ru getirildi";
        return new SuccessDataResult<List<ProductResponseWithCategory>>(message, productResponseWithCategories);
    }

    /**
     * 2021 Java, 9. ders. DTO konusu
     */
    @Override
    public DataResult<List<ProductWithCategoryDto>> getProductWithCategoryDetails() {
        List<ProductWithCategoryDto> productWithCategoryDtos = this.productRepository.getProductWithCategoryDetails();
        String message = "??r??n ID'si, ??r??n ismi ve kategori ismi olan DTO'lar getirildi.";
        return new SuccessDataResult<List<ProductWithCategoryDto>>(message, productWithCategoryDtos);
    }
    
}
