package com.library.library_app.controller;

import com.library.library_app.dto.model.BookAvailabilityDto;
import com.library.library_app.dto.request.BookAvailabilityRequest;
import com.library.library_app.exception.BookNotFoundException;
import com.library.library_app.service.BookAvailabilityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books/availability")
public class BookAvailabilityController {

    private final BookAvailabilityService bookAvailabilityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookAvailabilityDto createAvailability(
            @RequestBody BookAvailabilityRequest request,
            UriComponentsBuilder ucb,
            HttpServletResponse response
    ) {

        BookAvailabilityDto bookAvailabilityDto = bookAvailabilityService.createAvailability(request);

        URI locationOfNewBookAvailability = ucb
                .path("/api/books/availability/{id}")
                .buildAndExpand(bookAvailabilityDto.getId())
                .toUri();

        response.setHeader("Location", locationOfNewBookAvailability.toString());
        return bookAvailabilityDto;
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookAvailabilityDto updateAvailability(
            @PathVariable String bookId,
            @RequestBody BookAvailabilityRequest request) {
        request.setBookId(bookId);
        return bookAvailabilityService.updateAvailability(request);
    }

    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookAvailabilityDto getAvailabilityByBookId(@PathVariable String bookId) {
        return bookAvailabilityService.getAvailabilityByBookId(bookId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookAvailabilityDto> getAllAvailabilities() {
        return bookAvailabilityService.getAllAvailabilities();
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvailability(@PathVariable String bookId) {
        bookAvailabilityService.deleteAvailability(bookId);
    }
}

