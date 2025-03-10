package com.library.library_app.entity;

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
@Table(name = "book_checkout")
public class BookCheckoutEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "is_returned")
    private boolean isReturned = false;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @CreationTimestamp
    @Column(name = "checkout_date")
    private LocalDateTime checkoutDate;

    @PrePersist
    public void prePersist() {
        // İki haftalık ödünç alma süresi
        this.returnDate = LocalDateTime.now().plusWeeks(2);
    }

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;
}


