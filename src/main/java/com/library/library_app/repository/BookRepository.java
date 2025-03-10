package com.library.library_app.repository;

import com.library.library_app.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<BookEntity, String> {

    List<BookEntity> findByTitleIgnoreCaseOrAuthorIgnoreCase(String title, String author);

    List<BookEntity> findByCategoriesId(String categoryId);

    Optional<BookEntity> findByIsbn(String isbn);

}
 /*Spring Data JPA, findBy sorgularını otomatik olarak oluştururken belirli bir söz dizimi (naming convention)
 kullanır. Ancak TitleCase ve AuthorCase gibi ifadeler, JPA tarafından otomatik olarak tanınan ifadeler değildir. */

//Bu durumda, "IgnoreCase" ifadesi büyük/küçük harf duyarlılığını göz ardı eder ve sorgu başarılı olur.*/