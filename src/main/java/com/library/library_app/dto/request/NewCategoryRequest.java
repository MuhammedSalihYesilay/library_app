package com.library.library_app.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class NewCategoryRequest {

    private String title;
    private Set<String> bookIds;
}
