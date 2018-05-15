package com.caroline.vlado.biblio.Model;

import java.util.Map;

public interface Category {
    String getIdCategory();
    String getCategoryName();

    Map<String, Boolean> getBooks();
}
