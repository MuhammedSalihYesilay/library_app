package com.library.library_app.dto.model;

import com.library.library_app.entity.BookEntity;
import com.library.library_app.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private String publicationYear;
    private int page;
    private int edition;
    private Set<String> categoryIds;
    private LocalDateTime createDate;

    public static BookDto from(BookEntity book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publicationYear(book.getPublicationYear())
                .page(book.getPage())
                .edition(book.getEdition())
                .categoryIds(book.getCategories().stream().map(CategoryEntity::getId).collect(Collectors.toSet()))
                .createDate(book.getCreateDate())
                .build();
    }
}
