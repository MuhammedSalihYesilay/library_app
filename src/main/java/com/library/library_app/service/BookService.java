package com.library.library_app.service;

import com.library.library_app.dto.model.BookDto;
import com.library.library_app.dto.request.NewBookRequest;
import com.library.library_app.entity.BookEntity;
import com.library.library_app.entity.CategoryEntity;
import com.library.library_app.exception.BookNotFoundException;
import com.library.library_app.repository.BookRepository;
import com.library.library_app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BookDto createBook(NewBookRequest newBookRequest) {
        BookEntity bookEntity = buildBookEntity(newBookRequest);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        if (newBookRequest.getCategoryIds() != null && !newBookRequest.getCategoryIds().isEmpty()) {
            Set<CategoryEntity> categories = categoryRepository.findAllById(newBookRequest.getCategoryIds())
                    .stream().collect(Collectors.toSet());
            savedBookEntity.setCategories(categories);
            savedBookEntity = bookRepository.save(savedBookEntity);
        }

        return BookDto.from(savedBookEntity);
    }

    private BookEntity buildBookEntity(NewBookRequest newBookRequest){
        return BookEntity.builder()
                .title(newBookRequest.getTitle())
                .author(newBookRequest.getAuthor())
                .isbn(newBookRequest.getIsbn())
                .publisher(newBookRequest.getPublisher())
                .publicationYear(newBookRequest.getPublicationYear())
                .page(newBookRequest.getPage())
                .edition(newBookRequest.getEdition())
                .categories(new HashSet<>())
                .build();
    }

    @Transactional
    public BookDto updateBook(String bookId, NewBookRequest updateRequest) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        bookEntity.setTitle(updateRequest.getTitle());
        bookEntity.setAuthor(updateRequest.getAuthor());
        bookEntity.setIsbn(updateRequest.getIsbn());
        bookEntity.setPublisher(updateRequest.getPublisher());
        bookEntity.setPublicationYear(updateRequest.getPublicationYear());
        bookEntity.setPage(updateRequest.getPage());
        bookEntity.setEdition(updateRequest.getEdition());

        if (updateRequest.getCategoryIds() != null) {
            Set<CategoryEntity> newCategories = new HashSet<>(
                    categoryRepository.findAllById(updateRequest.getCategoryIds()));
            bookEntity.setCategories(newCategories);
        }

        bookEntity = bookRepository.save(bookEntity);
        return BookDto.from(bookEntity);
    }

    @Transactional(readOnly = true)
    public BookDto getBook(String bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        return BookDto.from(bookEntity);
    }

    //Kitaplar, hem başlığa hem de yazara göre arama yapılabilir ve büyük/küçük harf fark etmeden sonuçlar bulunur.
    @Transactional(readOnly = true)
    public List<BookDto> searchBooks(String title, String author) {
        return bookRepository.findByTitleIgnoreCaseOrAuthorIgnoreCase(title, author)
                .stream()
                .map(BookDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDto getBookByIsbn(String isbn) {
        BookEntity bookEntity = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("ISBN: " + isbn));
        return BookDto.from(bookEntity);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByCategory(String categoryId) {
        return bookRepository.findByCategoriesId(categoryId)
                .stream()
                .map(BookDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteBook(String BookId) {
        if (!bookRepository.existsById(BookId)) {
            throw new BookNotFoundException(BookId);
        }
        bookRepository.deleteById(BookId);
    }
}
