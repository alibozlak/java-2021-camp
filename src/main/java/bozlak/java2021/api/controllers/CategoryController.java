package bozlak.java2021.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bozlak.java2021.business.abstracts.CategoryService;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.dtos.category.CategoryResponseWithoutPicture;
import bozlak.java2021.dtos.category.CreateCategoryRequestWithoutPicture;
import bozlak.java2021.dtos.category.UpdateCategoryRequestWithoutPicture;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public DataResult<List<CategoryResponseWithoutPicture>> getAll() {
        return this.categoryService.getAll();
    }

    @GetMapping(path = "/get-by-id/{categoryId}")
    public Result getById(@PathVariable int categoryId) {
        return this.categoryService.getById(categoryId);
    }

    @DeleteMapping(path = "/delete-by-id/{categoryId}")
    public Result deleteById(@PathVariable int categoryId) {
        return this.categoryService.deleteById(categoryId);
    }

    @PostMapping
    public Result add(@RequestBody CreateCategoryRequestWithoutPicture createCategoryRequestWithoutPicture) {
        return this.categoryService.add(createCategoryRequestWithoutPicture);
    }

    @PutMapping
    public Result update(@RequestBody UpdateCategoryRequestWithoutPicture updateCategoryRequestWithoutPicture) {
        return this.categoryService.update(updateCategoryRequestWithoutPicture);
    }
    
}
