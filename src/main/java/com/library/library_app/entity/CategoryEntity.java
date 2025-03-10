package com.library.library_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//@Data
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class CategoryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title")
    private String title;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude  //Bu anotasyon, bir sınıfın toString() metoduna dahil edilmesini istemediğiniz alanlar (fields) için kullanılır.
    @EqualsAndHashCode.Exclude  //Bu anotasyon, bir alanın equals() ve hashCode() metotlarına dahil edilmemesi için kullanılır.
    private Set<BookEntity> books = new HashSet<>();


}
