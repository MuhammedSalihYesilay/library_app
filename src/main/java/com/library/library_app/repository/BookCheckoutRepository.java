package com.library.library_app.repository;

import com.library.library_app.entity.BookCheckoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookCheckoutRepository extends JpaRepository<BookCheckoutEntity, String> {

    List<BookCheckoutEntity> findByIsReturnedFalse();

    List<BookCheckoutEntity> findByIsReturnedFalseAndReturnDateBefore(LocalDateTime date);

    long countByStudentIdAndIsReturnedFalse(String studentId);
}

