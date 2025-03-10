package com.library.library_app.entity;

import com.library.library_app.exception.BookOutOfStockException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_availability")
public class BookAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "borrowed_count")
    private int borrowedCount;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    public void decrementQuantity() {
        if (quantity <= 0) {
            throw new BookOutOfStockException();
        }
        quantity--;
        borrowedCount++;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }
}
