package com.caroline.vlado.biblio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.caroline.vlado.biblio.database.Entites.AutorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showAuthorsActivity extends AppCompatActivity {

    //Components
    ListView listAuthors;
    ArrayAdapter<AutorEntity> adapter;
    SearchView searchView;
    List<AutorEntity> mAuthors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_authors);

        //instance of components
        searchView = findViewById(R.id.search_bar_author);
        listAuthors = findViewById(R.id.listAuthors);

        mAuthors = new ArrayList<>();

        //add items in the listView
        FirebaseDatabase.getInstance()
                .getReference("authors")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mAuthors.clear();
                            mAuthors.addAll(toAuthors(dataSnapshot));
                            adapter = new ArrayAdapter<AutorEntity>(showAuthorsActivity.this, android.R.layout.simple_list_item_1, mAuthors);
                            listAuthors.setAdapter(adapter);
                            //set listener on listView items
                            listAuthors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String uidAuthor = ((AutorEntity) listAuthors.getItemAtPosition(position)).getUid();
                                    Intent intent = new Intent(showAuthorsActivity.this, DetailsAuthorActivity.class);
                                    intent.putExtra("uidAuthor", uidAuthor);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

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


    //Access database to get the Authors
    private List<AutorEntity> toAuthors(DataSnapshot snapshot) {
        List<AutorEntity> authors = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            AutorEntity entity = childSnapshot.getValue(AutorEntity.class);
            entity.setUid(childSnapshot.getKey());
            authors.add(entity);
        }
        return authors;
    }


    //Open addAuthorActivity
    public void addAuthor(View view) {
        Intent intent = new Intent(this, AddAuthorActivity.class);
        startActivity(intent);
    }
}
