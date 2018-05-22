package com.caroline.vlado.biblio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.caroline.vlado.biblio.database.Entites.BookEntity;
import com.caroline.vlado.biblio.database.Entites.CategoryEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showBooksActivity extends AppCompatActivity {


    //Components
    ListView listBooks;
    SearchView searchView;
    ArrayAdapter<BookEntity> adapter;
    List<BookEntity> mBooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_books);

        //instance of components
        searchView = findViewById(R.id.search_bar_book);
        listBooks = findViewById(R.id.listBooks);

        mBooks = new ArrayList<>();

        //add items in the listView
        FirebaseDatabase.getInstance()
                .getReference("books")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mBooks.clear();
                            mBooks.addAll(toBook(dataSnapshot));
                            adapter = new ArrayAdapter<BookEntity>(showBooksActivity.this, android.R.layout.simple_list_item_1, mBooks);
                            listBooks.setAdapter(adapter);
                            //set listener on listView items
                            listBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String categoryId = ((CategoryEntity) listBooks.getItemAtPosition(position)).getUid();

                                    Intent intent = new Intent(showBooksActivity.this, DetailsBookActivity.class);
                                    intent.putExtra("idBook", ((BookEntity) listBooks.getItemAtPosition(position)).getUid());
                                    intent.putExtra("idCategory", ((BookEntity) listBooks.getItemAtPosition(position)).getCategory());
                                    intent.putExtra("idAuthor", ((BookEntity) listBooks.getItemAtPosition(position)).getAuthor());
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


    //Access database to get the Books
    private List<BookEntity> toBook(DataSnapshot snapshot) {
        List<BookEntity> books = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            BookEntity entity = childSnapshot.getValue(BookEntity.class);
            entity.setUid(childSnapshot.getKey());
            books.add(entity);
        }
        return books;
    }

    //Open addBookactivity
    public void addBook(View view) {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }




}
