package com.caroline.vlado.biblio.database.Dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.caroline.vlado.biblio.database.Entites.AutorEntity;
import com.caroline.vlado.biblio.database.Entites.BookEntity;

import java.util.List;
import io.reactivex.Flowable;

/**
 * https://developer.android.com/topic/libraries/architecture/room.html#no-object-references
 */
@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookEntity bookEntity);

    @Update
    void update(BookEntity bookEntity);

    @Delete
    void delete(BookEntity bookEntity);


    @Query("SELECT * FROM Books")
    List<BookEntity> getAll();

    @Query("SELECT * FROM Books WHERE title = :search")
    List<BookEntity> search(String search);


    @Query("SELECT * FROM Books WHERE fk_author = :idAuthor")
    List<BookEntity> getBooksByAuthor(int idAuthor);

    @Query("SELECT * FROM Books WHERE fk_category = :idCategroy")
    List<BookEntity> getBooksByCategory(int idCategroy);


    @Query("SELECT * FROM Books WHERE id=:id")
    BookEntity getById(int id);



}
