package com.caroline.vlado.biblio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.caroline.vlado.biblio.database.Entites.AutorEntity;
import com.caroline.vlado.biblio.database.Entites.BookEntity;
import com.caroline.vlado.biblio.database.Entites.CategoryEntity;

import java.util.List;

public class showAuthorsActivity extends AppCompatActivity {

    //Components
    ListView listAuthors;
    ArrayAdapter<AutorEntity> adapter;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_authors);

        //instance of components
        searchView = (SearchView)findViewById(R.id.search_bar_author);
        listAuthors = (ListView)findViewById(R.id.listAuthors);

        //add items in the listView
        updateListAuthor();


        //set listener on listView items
        listAuthors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int authorId = ((AutorEntity) listAuthors.getItemAtPosition(position)).getIdAutor();

                Intent intent = new Intent(showAuthorsActivity.this, DetailsAuthorActivity.class);
                intent.putExtra("idAuthor", authorId);
                startActivity(intent);
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
                return  true;
            }
        });
    }


    //Update the list onRestart
    @Override
    public void onResume() {
        super.onResume();
        updateListAuthor();
    }

    private void updateListAuthor() {
        adapter = new ArrayAdapter<AutorEntity>(showAuthorsActivity.this, android.R.layout.simple_list_item_1, getAuthors());
        adapter.notifyDataSetChanged();
        listAuthors.setAdapter(adapter);
    }

    //Access database to get all authors
    public List<AutorEntity> getAuthors(){
        return MainActivity.db.autorDao().getAll();
    }


    //Open addAuthorActivity
    public void addAuthor(View view) {
        Intent intent = new Intent(this, AddAuthorActivity.class);
        startActivity(intent);
    }
}
