package com.library.library_app.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String id){
        super("Category not found with id: " + id);
    }
}
