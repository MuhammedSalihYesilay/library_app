package com.library.library_app.controller;

import com.library.library_app.dto.model.BookDto;
import com.library.library_app.dto.request.NewBookRequest;
import com.library.library_app.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(
            @RequestBody NewBookRequest newBookRequest,
            UriComponentsBuilder ucb,
            HttpServletResponse response
    ){
        BookDto bookDto = bookService.createBook(newBookRequest);

        URI locationOfNewBook = ucb
                .path("/api/book/{id}")
                .buildAndExpand(bookDto.getId())
                .toUri();

        response.setHeader("Location", locationOfNewBook.toString());
        return bookDto;
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@PathVariable String bookId, @RequestBody NewBookRequest updateRequest){
        return bookService.updateBook(bookId, updateRequest);
    }

    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook(@PathVariable String bookId){
        return bookService.getBook(bookId);
    }

    //böylece kullanıcı isterse başlık veya yazara göre arama yapabilir.
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ){
        return bookService.searchBooks(title, author);
    }

    @GetMapping("/isbn/{isbn}")
    public BookDto getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByCategory(@PathVariable String categoryId){
        return bookService.getBooksByCategory(categoryId);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String bookId){
        bookService.deleteBook(bookId);
    }
}
