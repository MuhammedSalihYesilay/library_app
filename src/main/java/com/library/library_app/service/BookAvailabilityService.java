package com.library.library_app.service;

import com.library.library_app.dto.model.BookAvailabilityDto;
import com.library.library_app.dto.request.BookAvailabilityRequest;
import com.library.library_app.entity.BookAvailabilityEntity;
import com.library.library_app.entity.BookEntity;
import com.library.library_app.exception.BookAvailabilityNotFoundException;
import com.library.library_app.exception.BookNotFoundException;
import com.library.library_app.exception.BookOutOfStockException;
import com.library.library_app.repository.BookAvailabilityRepository;
import com.library.library_app.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookAvailabilityService {

    private final BookRepository bookRepository;
    private final BookAvailabilityRepository bookAvailabilityRepository;

    @Transactional
    public BookAvailabilityDto createAvailability(BookAvailabilityRequest request) {
        BookEntity bookEntity = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BookNotFoundException(request.getBookId()));

        if(bookEntity.getAvailability() != null){
            return updateAvailability(request);
        }

        BookAvailabilityEntity availability = BookAvailabilityEntity.builder()
                .book(bookEntity)
                .quantity(request.getQuantity())
                .borrowedCount(0)
                .build();

        bookEntity.setAvailability(availability);

        BookAvailabilityEntity savedAvailability = bookAvailabilityRepository.save(availability);
        return BookAvailabilityDto.from(savedAvailability);
    }

    @Transactional
    public BookAvailabilityDto updateAvailability(BookAvailabilityRequest request){
        BookEntity bookEntity = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BookNotFoundException(request.getBookId()));

        BookAvailabilityEntity availability = bookEntity.getAvailability();
        if(availability == null){
            return createAvailability(request);
        }

        availability.setQuantity(request.getQuantity());
        BookAvailabilityEntity savedAvailability = bookAvailabilityRepository.save(availability);
        return BookAvailabilityDto.from(savedAvailability);
    }

    public BookAvailabilityDto getAvailabilityByBookId(String bookId){
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        BookAvailabilityEntity availability = bookEntity.getAvailability();
        if(availability == null){
            throw new BookAvailabilityNotFoundException(bookId);
        }

        return BookAvailabilityDto.from(availability);
    }

    public List<BookAvailabilityDto> getAllAvailabilities() {
        return bookAvailabilityRepository.findAll().stream()
                .map(BookAvailabilityDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAvailability(String bookId){
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if(bookEntity.getAvailability() != null) {
            bookAvailabilityRepository.delete(bookEntity.getAvailability());
            bookEntity.setAvailability(null);
            bookRepository.save(bookEntity);
        }
    }

    @Transactional
    public BookAvailabilityEntity checkAndGetAvailability(String bookId){
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if(bookEntity.getAvailability() == null){
            throw new BookAvailabilityNotFoundException(bookId);
        }

        return bookEntity.getAvailability();
    }
}
