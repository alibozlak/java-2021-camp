package bozlak.java2021.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import bozlak.java2021.business.abstracts.CategoryService;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.ErrorResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.core.utilities.results.SuccessDataResult;
import bozlak.java2021.core.utilities.results.SuccessResult;
import bozlak.java2021.dtos.category.CategoryResponseWithoutPicture;
import bozlak.java2021.dtos.category.CreateCategoryRequestWithoutPicture;
import bozlak.java2021.dtos.category.UpdateCategoryRequestWithoutPicture;
import bozlak.java2021.entities.concretes.Category;
import bozlak.java2021.repository.abstracts.CategoryRepository;

@Service
public class CategoryManager implements CategoryService {

    private CategoryRepository categoryRepository;
    // private ProductService productService;

    public CategoryManager(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // public CategoryManager(CategoryRepository categoryRepository, ProductService productService) {
    //     this.categoryRepository = categoryRepository;
    //     this.productService = productService;
    // }

    @Override
    public DataResult<List<CategoryResponseWithoutPicture>> getAll() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryResponseWithoutPicture> categoryResponseWithoutPictures = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponseWithoutPicture categoryResponseWithoutPicture = new CategoryResponseWithoutPicture();
            categoryResponseWithoutPicture.setCategoryId(category.getId());
            categoryResponseWithoutPicture.setCategoryName(category.getName());
            categoryResponseWithoutPicture.setDescription(category.getDescription());

            categoryResponseWithoutPictures.add(categoryResponseWithoutPicture);
        }
        String message = "Tüm kategoriler listelendi";
        return new SuccessDataResult<List<CategoryResponseWithoutPicture>>(message, categoryResponseWithoutPictures);
    }

    @Override
    public boolean existCategoryById(int categoryId) {
        return this.categoryRepository.existsById(categoryId);
    }

    @Override
    public Result getById(int id) {
        if (!existCategoryById(id)) {
            return new ErrorResult("ID'si " + id + " olan bir kategori yok!");
        }
        Category category = this.categoryRepository.getReferenceById(id);
        CategoryResponseWithoutPicture categoryResponseWithoutPicture = new CategoryResponseWithoutPicture();
        categoryResponseWithoutPicture.setCategoryId(id);
        categoryResponseWithoutPicture.setCategoryName(category.getName());
        categoryResponseWithoutPicture.setDescription(category.getDescription());

        String message = "ID'si " + id + " olan kategori getirildi";
        return new SuccessDataResult<CategoryResponseWithoutPicture>(message, categoryResponseWithoutPicture);
    }

    @Override
    public Result add(CreateCategoryRequestWithoutPicture createEntityRequest) {
        String categoryName = createEntityRequest.getCategoryName();
        if (isNameEmpty(categoryName)) {
            return new ErrorResult("Kategori ismi boş geçilemez!");
        }
        if (repeatsCategoryName(categoryName)) {
            return new ErrorResult(categoryName + " isminde bir kategori zaten mevcut!");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setDescription(createEntityRequest.getDescription());
        this.categoryRepository.save(category);
        return new SuccessResult(categoryName + " adlı kategori eklendi");
    }

    @Override
    public Result update(UpdateCategoryRequestWithoutPicture updateEntityRequest) {
        int categoryId = updateEntityRequest.getCategoryId();
        String categoryName = updateEntityRequest.getCategoryName();

        if (!existCategoryById(categoryId)) {
            return new ErrorResult("ID'si " + categoryId + " olan bir kategori bulunmamakta!");
        }
        if (isNameEmpty(categoryName)) {
            return new ErrorResult("Kategori adı boş olamaz!");
        }
        if (repeatsCategoryName(categoryName)) {
            return new ErrorResult(categoryName + " isminde bir kategori zaten mevcuttur!");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        category.setDescription(updateEntityRequest.getDescription());
        this.categoryRepository.save(category);

        return new SuccessResult(categoryName + " adlı kategori güncellendi");
    }

    @Override
    public Result deleteById(int id) {
        if (!existCategoryById(id)) {
            return new ErrorResult("ID'si " + id + " olan kategori yok!");
        }
        // if (isCategoryUseProductsTable(id)) {
        //     return new ErrorResult("ID'si " + id + " olan kategori products tablosunda kullanılıyor!");
        // }

        this.categoryRepository.deleteById(id);
        return new SuccessResult("ID'si " + id + " olan kategori silindi.");
    }

    private boolean repeatsCategoryName(String categoryName) {
        List<Category> categories = this.categoryRepository.findAll();
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNameEmpty(String categoryName) {
        if (categoryName.isBlank()) {
            return true;
        }
        return false;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return this.categoryRepository.getReferenceById(categoryId);
    }

    // private boolean isCategoryUseProductsTable(int categoryId) {
    //     List<ProductResponseWithCategoryId> productResponseWithCategoryIds
    //     = this.productService.getAll().getData();

    //     for (ProductResponseWithCategoryId productResponseWithCategoryId : productResponseWithCategoryIds) {
    //         if (productResponseWithCategoryId.getCategoryId() == categoryId) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }
    
}
