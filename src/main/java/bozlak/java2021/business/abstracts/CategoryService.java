package bozlak.java2021.business.abstracts;

import bozlak.java2021.dtos.category.CategoryResponseWithoutPicture;
import bozlak.java2021.dtos.category.CreateCategoryRequestWithoutPicture;
import bozlak.java2021.dtos.category.UpdateCategoryRequestWithoutPicture;
import bozlak.java2021.entities.concretes.Category;

public interface CategoryService extends BaseService<
    CreateCategoryRequestWithoutPicture, UpdateCategoryRequestWithoutPicture, CategoryResponseWithoutPicture
    >{
    boolean existCategoryById(int categoryId);
    Category getCategoryById(int categoryId);
}
