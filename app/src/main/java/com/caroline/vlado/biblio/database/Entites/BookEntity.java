package com.caroline.vlado.biblio.database.Entites;


import android.support.annotation.NonNull;

import com.caroline.vlado.biblio.Model.Book;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class BookEntity implements Book {

    @NonNull
    private String uid;

    private String title;

    private String date;

    private String uidAuthor;

    private String uidCategory;

    private String summary;


    public BookEntity() {

    }

    public BookEntity(Book book) {
        this.title = book.getTitle();
        this.date = book.getDate();
        this.summary = book.getSummary();
        this.uidAuthor = book.getAuthor();
        this.uidCategory = book.getCategory();
    }

    @Exclude
    @Override
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uidBook) {
        this.uid = uidBook;
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
        return uidAuthor;
    }

    public void setUidAuthor(String idAuthor) {
        this.uidAuthor = idAuthor;
    }

    @Override
    public String getCategory() {
        return uidCategory;
    }

    public void setUidCategory(String idCategory) {
        this.uidCategory = idCategory;
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
        result.put("idAuthor", uidAuthor);
        result.put("idCategory", uidCategory);
        result.put("summary", summary);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof BookEntity)) return false;
        BookEntity o = (BookEntity) obj;
        return o.getUid().equals(this.getUid());
    }

    @Override
    public String toString() {
        return title;
    }


}
