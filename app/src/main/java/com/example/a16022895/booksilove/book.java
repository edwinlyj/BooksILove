package com.example.a16022895.booksilove;

public class book {
    private int book_id;
    private int book_category_id;
    private String book_name;
    private String book_author;
    private String book_summary;

    public book(int book_id, int book_category_id, String book_name, String book_author, String book_summary) {
        this.book_id = book_id;
        this.book_category_id = book_category_id;
        this.book_name = book_name;
        this.book_author = book_author;
        this.book_summary = book_summary;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getBook_category_id() {
        return book_category_id;
    }

    public void setBook_category_id(int book_category_id) {
        this.book_category_id = book_category_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_summary() {
        return book_summary;
    }

    public void setBook_summary(String book_summary) {
        this.book_summary = book_summary;
    }

    @Override
    public String toString() {
        return book_name;
    }

}
