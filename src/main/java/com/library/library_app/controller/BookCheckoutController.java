package com.library.library_app.controller;

import com.library.library_app.dto.model.BookCheckoutDto;
import com.library.library_app.dto.request.NewBookCheckoutRequest;
import com.library.library_app.service.BookCheckoutService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books/checkouts")
public class BookCheckoutController {

    private final BookCheckoutService bookCheckoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookCheckoutDto checkoutBook(
            @RequestBody NewBookCheckoutRequest request,
            UriComponentsBuilder ucb,
            HttpServletResponse response
    ){
        BookCheckoutDto checkoutDto = bookCheckoutService.checkoutBook(request);

        URI locationOfNewBookCheckout = ucb
                .path("/api/books/checkout/{id}")
                .buildAndExpand(checkoutDto.getId())
                .toUri();

        response.setHeader("Location", locationOfNewBookCheckout.toString());
        return checkoutDto;
    }

    @PutMapping("/{checkoutId}/return")
    @ResponseStatus(HttpStatus.OK)
    public BookCheckoutDto returnBook(@PathVariable String checkoutId) {
        return bookCheckoutService.returnBook(checkoutId);
    }

    @PutMapping("/{checkoutId}/extend")
    @ResponseStatus(HttpStatus.OK)
    public BookCheckoutDto extendBookCheckout(@PathVariable String checkoutId){
        return bookCheckoutService.extendBookCheckout(checkoutId);
    }

    @GetMapping("/{checkoutId}")
    @ResponseStatus(HttpStatus.OK)
    public BookCheckoutDto getCheckoutById(@PathVariable String checkoutId) {
        return bookCheckoutService.getCheckoutById(checkoutId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookCheckoutDto> getAllCheckouts() {
        return bookCheckoutService.getAllCheckouts();
    }

    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookCheckoutDto> getCheckoutsByStudent(@PathVariable String studentId){
        return bookCheckoutService.getCheckoutsByStudent(studentId);
    }

    @GetMapping("/book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookCheckoutDto> getCheckoutsByBook(@PathVariable String bookId){
        return bookCheckoutService.getCheckoutsByBook(bookId);
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<BookCheckoutDto> getActiveCheckouts(){
        return bookCheckoutService.getActiveCheckouts();
    }

    @GetMapping("/overdue")
    @ResponseStatus(HttpStatus.OK)
    public List<BookCheckoutDto> getOverdueCheckouts(){
        return bookCheckoutService.getOverdueCheckouts();
    }
}
