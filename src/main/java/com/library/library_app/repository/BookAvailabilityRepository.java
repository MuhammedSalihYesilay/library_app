package com.library.library_app.repository;

import com.library.library_app.entity.BookAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAvailabilityRepository extends JpaRepository<BookAvailabilityEntity, String> {
}
