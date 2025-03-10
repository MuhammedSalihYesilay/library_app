package com.library.library_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Data   sonsuz döngüye sokuyor
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class BookEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_year")
    private String publicationYear;

    @Column(name = "page")
    private int page;

    @Column(name = "edition")
    private int edition;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToMany
    @JoinTable(
            name = "book_category", // Ara tablo adı
            joinColumns = @JoinColumn(name = "book_id"), // book tablosu referansı
            inverseJoinColumns = @JoinColumn (name = "category_id")) // category tabloreferansı
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CategoryEntity> categories = new HashSet<>();

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BookAvailabilityEntity availability;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BookCheckoutEntity> checkouts = new HashSet<>();
}