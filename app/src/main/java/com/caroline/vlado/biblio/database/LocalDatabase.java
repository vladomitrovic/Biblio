package com.caroline.vlado.biblio.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.caroline.vlado.biblio.database.Dao.AutorDao;
import com.caroline.vlado.biblio.database.Dao.BookDao;
import com.caroline.vlado.biblio.database.Dao.CategoryDao;
import com.caroline.vlado.biblio.database.Entites.AutorEntity;
import com.caroline.vlado.biblio.database.Entites.BookEntity;
import com.caroline.vlado.biblio.database.Entites.CategoryEntity;

/**
 * Created by Vlado on 21.04.2018.
 */

@Database(entities = {AutorEntity.class, BookEntity.class, CategoryEntity.class}, version = 4)
public abstract class LocalDatabase extends RoomDatabase {


    public abstract AutorDao autorDao();


    public abstract BookDao bookDao();

    public abstract CategoryDao categoryDao();




}
