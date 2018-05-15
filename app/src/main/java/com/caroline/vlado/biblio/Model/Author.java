package com.caroline.vlado.biblio.Model;

import java.util.Map;

public interface Author {
    String getIdAutor();
    String getFirstName();
    String getLastName();
    String getBirthday();

    String getBiography();

    Map<String, Boolean> getBooks();
}

