package com.caroline.vlado.biblio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.caroline.vlado.biblio.database.Entites.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class showCategoriesActivity extends AppCompatActivity {


    //Components
    ListView listCategories;
    ArrayAdapter <CategoryEntity> adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories);


        //instance of components
        listCategories = findViewById(R.id.listCategories);
        searchView = findViewById(R.id.search_bar_categories);


        //add items in the listView
        updateListCategories();




        //Behavior of searchView
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(getText(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText.toString();
                adapter.getFilter().filter(newText);
                return  true;
            }
        });
    }

    //update the listView if we come back
    @Override
    public void onResume() {
        super.onResume();
        updateListCategories();
    }


    private void updateListCategories() {
        //add items in the listView
        //adapter = new ArrayAdapter<CategoryEntity>(showCategoriesActivity.this, android.R.layout.simple_list_item_1, getCategories());
        //adapter.notifyDataSetChanged();
        listCategories.setAdapter(adapter);

        //set listener on listView items
        listCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryId = ((CategoryEntity) listCategories.getItemAtPosition(position)).getIdCategory();

                Intent intent = new Intent(showCategoriesActivity.this, showBooksActivity.class);
                intent.putExtra("idCategory", categoryId);
                startActivity(intent);

            }
        });
    }


    //Access database to get the categories
    public List<CategoryEntity> getCategories(){

        List<CategoryEntity> categories = new ArrayList<>();
        

        return categories;
}


    //Open addCategory activity
    public void addCategory(View view) {
        Intent intent = new Intent(this, AddCategorieActivity.class);
        startActivity(intent);
    }
}
