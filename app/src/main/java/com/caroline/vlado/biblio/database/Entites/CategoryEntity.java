package com.caroline.vlado.biblio.database.Entites;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.caroline.vlado.biblio.Model.*;

@Entity(tableName = "Categories")
public class CategoryEntity implements Category {


        @NonNull
        @PrimaryKey (autoGenerate = true)
        @ColumnInfo(name = "id")
        private int IdCategory;

        @ColumnInfo(name = "category_name")
        private String categoryName;


        public CategoryEntity() {
        }


        @Override
        public Integer getIdCategory() {
            return IdCategory;
        }

        public void setIdCategory(Integer idCategory) { this.IdCategory = idCategory; }

        @Override
        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }


        @Ignore
        public CategoryEntity(String categoryName) {
        this.categoryName = categoryName;
     }

    @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            if (!(obj instanceof CategoryEntity)) return false;
            CategoryEntity o = (CategoryEntity) obj;
            return o.getIdCategory().equals(this.getIdCategory());
        }

    @Override
    public String toString() {
        return categoryName;
    }
}
