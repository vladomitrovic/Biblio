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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showCategoriesActivity extends AppCompatActivity {


    //Components
    ListView listCategories;
    ArrayAdapter<CategoryEntity> adapter;
    SearchView searchView;
    List<CategoryEntity> mCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories);


        //instance of components
        listCategories = findViewById(R.id.listCategories);
        searchView = findViewById(R.id.search_bar_categories);

        mCategories = new ArrayList<>();

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
                return true;
            }
        });
    }

    //update the listView if we come back
    @Override
    public void onRestart() {
        super.onRestart();
        updateListCategories();
    }


    private void updateListCategories() {
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mCategories.clear();
                            mCategories.addAll(toCategories(dataSnapshot));
                            adapter = new ArrayAdapter<CategoryEntity>(showCategoriesActivity.this, android.R.layout.simple_list_item_1, mCategories);
                            listCategories.setAdapter(adapter);
                            //set listener on listView items
                            listCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String uidCategory = ((CategoryEntity) listCategories.getItemAtPosition(position)).getUid();
                                    System.out.println("--------------------Uid catergory-------------------------------------------" + uidCategory);
                                    Intent intent = new Intent(showCategoriesActivity.this, showBooksActivity.class);
                                    intent.putExtra("uidCategory", uidCategory);
                                    startActivity(intent);

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    //Access database to get the categories
    private List<CategoryEntity> toCategories(DataSnapshot snapshot) {
        List<CategoryEntity> categories = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CategoryEntity entity = childSnapshot.getValue(CategoryEntity.class);
            entity.setUid(childSnapshot.getKey());
            categories.add(entity);
        }
        return categories;
    }


    //Open addCategory activity
    public void addCategory(View view) {
        Intent intent = new Intent(this, AddCategorieActivity.class);
        startActivity(intent);
    }
}
