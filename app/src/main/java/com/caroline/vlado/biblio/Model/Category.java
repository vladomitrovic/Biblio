package com.caroline.vlado.biblio.Model;

import java.util.Map;

public interface Category {
    String getUid();
    String getCategoryName();

    Map<String, Boolean> getBooks();
}
