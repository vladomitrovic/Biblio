package com.caroline.vlado.biblio.database.Entites;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.caroline.vlado.biblio.Model.*;

/**
 * https://developer.android.com/reference/android/arch/persistence/room/Entity.html
 * <p>
 * interesting: owner column references a foreign key, that's why this column is indexed.
 * If not indexed, it might trigger full table scans whenever parent table is modified so you are
 * highly advised to create an index that covers this column.
 */



@Entity(tableName = "Books",
        foreignKeys ={
        @ForeignKey(

                entity = AutorEntity.class, parentColumns = "id", childColumns = "fk_author", onDelete = ForeignKey.CASCADE),
        @ForeignKey(

                entity = AutorEntity.class, parentColumns = "id", childColumns = "fk_author", onDelete = ForeignKey.CASCADE)},
        indices = {
                @Index(
                        value = {"fk_author"}
                )}
)
public class BookEntity implements Book {

    @NonNull
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    private int idBook;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "pages")
    private int pages;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "summary")
    private String summary;

    public int getFkAuthor() {
        return fkAuthor;
    }

    public void setFkAuthor(int fkAuthor) {
        this.fkAuthor = fkAuthor;
    }

    @ColumnInfo(name = "fk_author")
    private int fkAuthor;

    @ColumnInfo(name ="fk_category")
    private int fkCategory;

    public int getFkCategory() {
        return fkCategory;
    }

    public void setFkCategory(int fkCategory) {
        this.fkCategory = fkCategory;
    }

    public BookEntity() {
    }

    @Ignore
    public BookEntity(String title, int pages, String date, String summary, int fkAuthor) {
        this.title = title;
        this.pages = pages;
        this.date = date;
        this.summary = summary;
        this.fkAuthor=fkAuthor;
    }

    @Override
    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
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
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @Override
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
