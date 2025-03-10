package com.library.library_app.dto.model;

import com.library.library_app.entity.BookAvailabilityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookAvailabilityDto {

    private String id;
    private String bookId;
    private int quantity;
    private int borrowedCount;
    private LocalDateTime createDate;

    public static BookAvailabilityDto from(BookAvailabilityEntity bookAvailabilityEntity) {
        return BookAvailabilityDto.builder()
                .id(bookAvailabilityEntity.getId())
                .bookId(bookAvailabilityEntity.getBook().getId())
                .quantity(bookAvailabilityEntity.getQuantity())
                .borrowedCount(bookAvailabilityEntity.getBorrowedCount())
                .createDate(bookAvailabilityEntity.getCreateDate())
                .build();
    }
}
