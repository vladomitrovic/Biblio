package com.caroline.vlado.biblio.database.Entites;


import android.support.annotation.NonNull;

import com.caroline.vlado.biblio.Model.Book;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class BookEntity implements Book {

    @NonNull
    private String idBook;

    private String title;

    private String date;

    private String idAuthor;

    private String idCategory;

    private String summary;


    public BookEntity() {

    }

    public BookEntity(Book book) {
        this.title = book.getTitle();
        this.date = book.getDate();
        this.summary = book.getSummary();
        this.idAuthor = book.getAuthor();
        this.idCategory = book.getCategory();
    }

    @Exclude
    @Override
    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(@NonNull String idBook) {
        this.idBook = idBook;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    @Override
    public String getCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("idAuthor", idAuthor);
        result.put("idCategory", idCategory);
        result.put("summary", summary);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof BookEntity)) return false;
        BookEntity o = (BookEntity) obj;
        return o.getIdBook().equals(this.getIdBook());
    }

    @Override
    public String toString() {
        return title;
    }


}
