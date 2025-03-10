package com.library.library_app.controller;

import com.library.library_app.dto.model.CategoryDto;
import com.library.library_app.dto.request.NewCategoryRequest;
import com.library.library_app.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody NewCategoryRequest newCategoryRequest,
                                      UriComponentsBuilder ucb,
                                      HttpServletResponse response
    ) {
        CategoryDto categoryDto = categoryService.createCategory(newCategoryRequest);

        URI locationOfNewCategory = ucb
                .path("/api/category/{id}")
                .buildAndExpand(categoryDto.getId())
                .toUri();

        response.setHeader("Location", locationOfNewCategory.toString());
        return categoryDto;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK) //200
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteCategoryById(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }

    // Kategorileri otomatik güncelleme
    @PostMapping("/auto-update")
    @ResponseStatus(HttpStatus.OK)
    public void autoUpdateCategories() {
        categoryService.autoUpdateCategory();
    }
}
