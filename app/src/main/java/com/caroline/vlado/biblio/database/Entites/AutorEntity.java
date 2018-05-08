package com.caroline.vlado.biblio.database.Entites;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.caroline.vlado.biblio.Model.*;

import java.util.Date;



/**
 * https://developer.android.com/reference/android/arch/persistence/room/Entity.html
 */
@Entity(tableName = "Authors")
public class AutorEntity implements Author {

    @NonNull
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer IdAutor;

    @ColumnInfo(name = "firstname")
    private String firstName;

    @ColumnInfo(name = "lastname")
    private String lastName;

    @ColumnInfo(name = "biography")
    private String biography;

    @ColumnInfo(name = "birthday")
    private String birthday;


    public AutorEntity() {
    }

    public AutorEntity(String firstName, String lastName, String biography, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.birthday = birthday;
    }

    @Override
    public Integer getIdAutor() {
        return IdAutor;
    }

    public void setIdAutor(Integer idAutor) { this.IdAutor = idAutor; }

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
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }





    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof AutorEntity)) return false;
        AutorEntity o = (AutorEntity) obj;
        return o.getIdAutor().equals(this.getIdAutor());
    }

    @Override
    public String toString() {
        return lastName+" "+firstName ;
    }
}


