package com.library.library_app.dto.model;

import com.library.library_app.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String id;
    private String title;
    private LocalDateTime createdAt;

    public static CategoryDto from(CategoryEntity categoryEntity) {
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .title(categoryEntity.getTitle())
                .createdAt(categoryEntity.getCreatedAt())
                .build();
    }
}
