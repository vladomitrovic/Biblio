package com.caroline.vlado.biblio.database.Entites;


import android.support.annotation.NonNull;

import com.caroline.vlado.biblio.Model.Category;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class CategoryEntity implements Category {


        @NonNull
        private String idCategory;

        private String categoryName;

    private Map<String, Boolean> books = new HashMap<String, Boolean>();


    public CategoryEntity() {

        }

    public CategoryEntity(Category category) {
        idCategory = category.getIdCategory();
        categoryName = category.getCategoryName();
        books = category.getBooks();
    }

    @Exclude
    @Override
    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(@NonNull String idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public Map<String, Boolean> getBooks() {
        return books;
    }

    public void setBooks(Map<String, Boolean> books) {
        this.books = books;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("categoryName", categoryName);
        result.put("books", books);
        return result;
    }



    @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            if (!(obj instanceof CategoryEntity)) return false;
            CategoryEntity o = (CategoryEntity) obj;
            return o.getIdCategory().equals(this.getIdCategory());
        }

    @Override
    public String toString() {
        return categoryName;
    }

}
