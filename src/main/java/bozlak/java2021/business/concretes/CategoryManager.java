package bozlak.java2021.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import bozlak.java2021.business.abstracts.CategoryService;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.core.utilities.results.SuccessDataResult;
import bozlak.java2021.dtos.category.CategoryResponseWithoutPicture;
import bozlak.java2021.dtos.category.CreateCategoryRequestWithoutPicture;
import bozlak.java2021.dtos.category.UpdateCategoryRequestWithoutPicture;
import bozlak.java2021.entities.concretes.Category;
import bozlak.java2021.repository.abstracts.CategoryRepository;

@Service
public class CategoryManager implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryManager(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result add(CreateCategoryRequestWithoutPicture createEntityRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result update(UpdateCategoryRequestWithoutPicture updateEntityRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result deleteById(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
