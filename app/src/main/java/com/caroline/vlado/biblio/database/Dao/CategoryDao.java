package com.caroline.vlado.biblio.database.Dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.sqlite.SQLiteConstraintException;

import com.caroline.vlado.biblio.database.Entites.CategoryEntity;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Flowable;

/**
 * https://developer.android.com/topic/libraries/architecture/room.html#no-object-references
 */
@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategoryEntity category);

    @Update
    void update(CategoryEntity category);

    @Delete
    void delete(CategoryEntity category);

    @Query("SELECT * FROM Categories")
    List<CategoryEntity> getAllS();

    @Query("SELECT * FROM Categories WHERE id=:id")
    CategoryEntity getById(int id);



}
