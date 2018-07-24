package com.example.a16022895.booksilove;

import java.io.Serializable;

public class BookCategory implements Serializable {

    private int categoryId;
    private String bookCategory;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public BookCategory(int categoryId, String bookCategory) {
        this.categoryId = categoryId;

        this.bookCategory = bookCategory;
    }

    @Override
    public String toString() {
        return bookCategory;
    }
}
