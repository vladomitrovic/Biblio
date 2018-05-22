package com.caroline.vlado.biblio.database.Entites;


import android.support.annotation.NonNull;

import com.caroline.vlado.biblio.Model.Author;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class AutorEntity implements Author {

    @Exclude
    @NonNull
    private String uid;


    private String firstName;


    private String lastName;


    private String biography;


    private String birthday;




    public AutorEntity() {
    }

    public AutorEntity(Author author) {
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.biography = author.getBiography();
        this.birthday = author.getBirthday();
    }


    @Override
    public String getUid() {
        return uid;
    }

    public void setUid(String idAuthor) {
        this.uid = idAuthor;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }




    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("birthday", birthday);
        result.put("biography", biography);
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof AutorEntity)) return false;
        AutorEntity o = (AutorEntity) obj;
        return o.getUid().equals(this.getUid());
    }

    @Override
    public String toString() {
        return lastName+" "+firstName ;
    }


}


