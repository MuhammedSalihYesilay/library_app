package com.library.library_app.repository;


import com.library.library_app.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

    // Kategori ile ilişkili book_category kayıtlarını silmek için
    @Modifying
    @Query(value = "DELETE FROM book_category WHERE category_id = :categoryId", nativeQuery = true)
    void deleteBookCategoryRelations(@Param("categoryId") String categoryId);
}


/*@Modifying anotasyonu:

Bu anotasyon, sorgunun veri değiştiren bir işlem (INSERT, UPDATE, DELETE) olduğunu belirtir.
Spring Data JPA'ya bu sorgunun sadece veri çekmek değil, veriyi değiştirmek için olduğunu söyler.
Bu anotasyon olmadan, veri değiştiren sorgular çalıştırılamaz.*/

