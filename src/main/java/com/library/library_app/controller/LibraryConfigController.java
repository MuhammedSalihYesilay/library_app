package com.library.library_app.controller;

import com.library.library_app.config.libraryConfig.LibraryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/library-info")
public class LibraryConfigController {

    private final LibraryProperties libraryProperties;

    @GetMapping
    public LibraryProperties getLibraryInfo() {
        return libraryProperties;
    }
}
