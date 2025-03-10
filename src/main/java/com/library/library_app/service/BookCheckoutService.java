package com.library.library_app.service;

import com.library.library_app.dto.model.BookCheckoutDto;
import com.library.library_app.dto.request.NewBookCheckoutRequest;
import com.library.library_app.entity.BookAvailabilityEntity;
import com.library.library_app.entity.BookCheckoutEntity;
import com.library.library_app.entity.BookEntity;
import com.library.library_app.entity.StudentEntity;
import com.library.library_app.exception.*;
import com.library.library_app.repository.BookCheckoutRepository;
import com.library.library_app.repository.BookRepository;
import com.library.library_app.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCheckoutService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final BookCheckoutRepository bookCheckoutRepository;
    private final BookAvailabilityService bookAvailabilityService;

    private static final int MAX_BOOKS_PER_STUDENT = 3;

    @Transactional
    public BookCheckoutDto checkoutBook(NewBookCheckoutRequest request){
        BookEntity bookEntity = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BookNotFoundException(request.getBookId()));

        StudentEntity studentEntity = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException(request.getStudentId()));

        // Öğrencinin mevcut ödünç aldığı ve iade etmediği kitap sayısını kontrol et
        long activeCheckoutsCount = bookCheckoutRepository.countByStudentIdAndIsReturnedFalse(request.getStudentId());

        if (activeCheckoutsCount >= MAX_BOOKS_PER_STUDENT) {
            throw new MaxBooksExceededException();
        }

        BookAvailabilityEntity availability = bookAvailabilityService.checkAndGetAvailability(request.getBookId());

        if(!availability.isAvailable()){
            throw new BookOutOfStockException();
        }

        availability.decrementQuantity();

        BookCheckoutEntity checkout = BookCheckoutEntity.builder()
                .book(bookEntity)
                .student(studentEntity)
                .checkoutDate(LocalDateTime.now())
                .isReturned(false)
                .build();

        BookCheckoutEntity savedCheckout = bookCheckoutRepository.save(checkout);
        return BookCheckoutDto.from(savedCheckout);
    }

    public BookCheckoutDto returnBook(String checkoutId) {
        BookCheckoutEntity checkout = bookCheckoutRepository.findById(checkoutId)
                .orElseThrow(() -> new BookNotFoundException(checkoutId));

        if (checkout.isReturned()) {
            throw new AlreadyBookReturnedException(checkoutId);
        }

        BookAvailabilityEntity availabilityEntity = bookAvailabilityService.checkAndGetAvailability(checkout.getBook().getId());
        availabilityEntity.incrementQuantity();

        checkout.setReturned(true);
        checkout.setReturnDate(LocalDateTime.now());

        BookCheckoutEntity savedCheckout = bookCheckoutRepository.save(checkout);
        return BookCheckoutDto.from(savedCheckout);
    }

    // kitap bitmemiş ise 15 gün daha uzatıalbilir
    @Transactional
    public BookCheckoutDto extendBookCheckout(String checkoutId) {
        BookCheckoutEntity checkout = bookCheckoutRepository.findById(checkoutId)
                .orElseThrow(() -> new BookNotFoundException(checkoutId));

        if (checkout.isReturned()) {
            throw new AlreadyBookReturnedException(checkoutId);
        }

        // Mevcut iade tarihine 15 gün ekle
        if (checkout.getReturnDate() == null) {
            checkout.setReturnDate(checkout.getCheckoutDate().plusDays(15));
        } else {
            // Zaten bir kez uzatılmış, mevcut iade tarihine 15 gün daha ekle
            checkout.setReturnDate(checkout.getReturnDate().plusDays(15));
        }

        BookCheckoutEntity savedCheckout = bookCheckoutRepository.save(checkout);
        return BookCheckoutDto.from(savedCheckout);
    }

    public BookCheckoutDto getCheckoutById(String checkoutId) {
        BookCheckoutEntity checkout = bookCheckoutRepository.findById(checkoutId)
                .orElseThrow(() -> new BookNotFoundException(checkoutId));

        return BookCheckoutDto.from(checkout);
    }

    public List<BookCheckoutDto> getAllCheckouts() {
        return bookCheckoutRepository.findAll().stream()
                .map(BookCheckoutDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookCheckoutDto> getCheckoutsByStudent(String studentId){
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        return studentEntity.getCheckoutEntity().stream()
                .map(BookCheckoutDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookCheckoutDto> getCheckoutsByBook(String bookId){
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        return bookEntity.getCheckouts().stream()
                .map(BookCheckoutDto::from)
                .collect(Collectors.toList());
    }

    public List<BookCheckoutDto> getActiveCheckouts(){
        return bookCheckoutRepository.findByIsReturnedFalse().stream()
                .map(BookCheckoutDto::from)
                .collect(Collectors.toList());
    }

    public List<BookCheckoutDto> getOverdueCheckouts() {
        LocalDateTime now = LocalDateTime.now();
        return bookCheckoutRepository.findByIsReturnedFalseAndReturnDateBefore(now).stream()
                .map(BookCheckoutDto::from)
                .collect(Collectors.toList());
    }
}


/*Harika! @Transactional(readOnly = true) kullanmak bu tür lazy loading hatalarını çözmek için etkili ve kolay bir yöntemdir.
 Bu annotation, metot çalıştığı sürece Hibernate oturumunun açık kalmasını sağlar, böylece lazy loaded koleksiyonlar
 gerektiğinde yüklenebilir.

readOnly = true parametresi de iyi bir seçim, çünkü:

Sadece veri okuduğunuz için daha iyi performans sağlar
Veritabanı üzerinde yanlışlıkla değişiklik yapma riskinizi azaltır
Bazı veritabanları için optimizasyonlar sağlayabilir
Bu yaklaşım özellikle Spring Boot uygulamalarında yaygın olarak kullanılır ve JPA/Hibernate ile çalışırken karşılaşılan
en yaygın sorunlardan birini çözer.*/