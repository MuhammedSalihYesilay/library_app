package com.library.library_app.service;

import com.library.library_app.dto.model.CategoryDto;
import com.library.library_app.dto.request.NewCategoryRequest;
import com.library.library_app.entity.BookEntity;
import com.library.library_app.entity.CategoryEntity;
import com.library.library_app.exception.BookNotFoundException;
import com.library.library_app.exception.CategoryNotFoundException;
import com.library.library_app.repository.BookRepository;
import com.library.library_app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto createCategory(NewCategoryRequest newCategoryRequest) {
        CategoryEntity categoryEntity = buildCategoryEntity(newCategoryRequest);
        Set<BookEntity> books = findBooksByIds(newCategoryRequest.getBookIds());
        categoryEntity.setBooks(books);

        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return CategoryDto.from(savedCategory);
    }

    private CategoryEntity buildCategoryEntity(NewCategoryRequest newCategoryRequest){
        return CategoryEntity.builder()
                .title(newCategoryRequest.getTitle())
                .build();
    }

    private Set<BookEntity> findBooksByIds(Set<String> bookIds){
        if (bookIds == null || bookIds.isEmpty()) {
            return Collections.emptySet(); // Boş set döndü.
        }

        return bookIds.stream()
                .map(bookId -> bookRepository.findById(bookId)
                        .orElseThrow(() -> new BookNotFoundException(bookId)))
                .collect(Collectors.toSet());
    }

    @Scheduled(cron = "0 0/15 * * * *")
    @Transactional
    public void autoUpdateCategory() {
        List<CategoryEntity> allCategories = categoryRepository.findAll();
        allCategories.forEach(this::updateCategoryDetails);
    }

    private void updateCategoryDetails(CategoryEntity category) {
        int totalBooks = category.getBooks().size();

        if (totalBooks > 0) {
            category.setTitle(category.getTitle() + "(" + totalBooks + " books)");
        }

        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(String id) {
        return CategoryDto.from(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id)));
    }

    @Transactional(readOnly = true)
   public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::from)
                .collect(Collectors.toList());
   }

    @Transactional
    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }

        categoryRepository.deleteBookCategoryRelations(id);
        categoryRepository.deleteById(id);
    }

}