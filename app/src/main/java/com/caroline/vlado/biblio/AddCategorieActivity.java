package com.caroline.vlado.biblio;

import android.icu.util.ULocale;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caroline.vlado.biblio.database.Entites.CategoryEntity;

public class AddCategorieActivity extends AppCompatActivity {

    //Components
    FloatingActionButton add;
    EditText et_category;
    Toast errorToast;

    CategoryEntity newCategory;
    Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categorie);

        //instance of components
        et_category = findViewById(R.id.et_add_book_category);
        add = findViewById(R.id.addCategoyButton);

        errorToast = Toast.makeText(this, getString(R.string.FillAll), Toast.LENGTH_SHORT);
        toast= Toast.makeText(this, getString(R.string.CategorySaved), Toast.LENGTH_SHORT);

        newCategory = new CategoryEntity();

        //Add button
        add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //Check if all field are filled
                if(et_category.getText().toString().matches("")){
                    errorToast.show();
                    return;
                }


                //set the name in the object
               newCategory.setCategoryName(et_category.getText().toString());

                //insert in the database
               MainActivity.db.categoryDao().insert(newCategory);


                //info toast
                toast.show();

                //back to the list
                onBackPressed();
            }
        });



    }
}
