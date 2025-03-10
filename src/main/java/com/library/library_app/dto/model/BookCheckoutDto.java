package com.library.library_app.dto.model;

import com.library.library_app.entity.BookCheckoutEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCheckoutDto {

    private String id;
    private LocalDateTime returnDate;
    private LocalDateTime checkoutDate;
    private boolean returned;
    private String bookId;
    private String bookTitle;
    private String studentId;

    public static BookCheckoutDto from(BookCheckoutEntity bookCheckoutEntity) {
        return BookCheckoutDto.builder()
                .id(bookCheckoutEntity.getId())
                .returnDate(bookCheckoutEntity.getReturnDate())
                .checkoutDate(bookCheckoutEntity.getCheckoutDate())
                .returned(bookCheckoutEntity.isReturned())
                .bookId(bookCheckoutEntity.getBook().getId())
                .bookTitle(bookCheckoutEntity.getBook().getTitle())
                .studentId(bookCheckoutEntity.getStudent().getId())
                .build();
    }
}
