package com.library.library_app.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookAvailabilityRequest {

    private String bookId;
    private int quantity;
}
