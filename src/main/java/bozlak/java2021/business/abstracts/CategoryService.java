package bozlak.java2021.business.abstracts;

import bozlak.java2021.dtos.category.CategoryResponseWithoutPicture;
import bozlak.java2021.dtos.category.CreateCategoryRequestWithoutPicture;
import bozlak.java2021.dtos.category.UpdateCategoryRequestWithoutPicture;

public interface CategoryService extends BaseService<
    CreateCategoryRequestWithoutPicture, UpdateCategoryRequestWithoutPicture, CategoryResponseWithoutPicture
    >{
    boolean existCategoryById(int categoryId);
}
