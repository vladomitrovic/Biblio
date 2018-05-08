package com.caroline.vlado.biblio.database.Dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.sqlite.SQLiteConstraintException;

import com.caroline.vlado.biblio.database.Entites.AutorEntity;

import java.util.List;


import io.reactivex.Flowable;

/**
     * https://developer.android.com/topic/libraries/architecture/room.html#no-object-references
     */
    @Dao
    public interface AutorDao {



        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(AutorEntity authors);

        @Update
        void update(AutorEntity author);

        @Delete
        void delete(AutorEntity author);


        @Query("SELECT * FROM Authors")
        List<AutorEntity> getAll();

        @Query("SELECT * FROM Authors WHERE firstname = :search OR lastname = :search")
        List<AutorEntity> search(String search);


       @Query("SELECT * FROM Authors WHERE id=:id")
       AutorEntity getById(int id);


    }


