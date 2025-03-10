package com.library.library_app.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class NewBookRequest {

    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private String publicationYear;
    private int page;
    private int edition;
    private Set<String> categoryIds;
}
