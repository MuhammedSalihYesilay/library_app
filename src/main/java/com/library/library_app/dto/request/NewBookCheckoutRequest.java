package com.library.library_app.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NewBookCheckoutRequest {

    private String bookId;
    private String studentId;
}
