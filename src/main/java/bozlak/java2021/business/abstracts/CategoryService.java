package bozlak.java2021.business.abstracts;

import java.util.List;

import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.dtos.category.CategoryResponseWithoutPicture;

public interface CategoryService {
    DataResult<List<CategoryResponseWithoutPicture>> getAll();
    boolean existCategoryById(int categoryId);
}
