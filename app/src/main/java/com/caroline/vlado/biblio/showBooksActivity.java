package com.caroline.vlado.biblio;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.caroline.vlado.biblio.database.Entites.AutorEntity;
import com.caroline.vlado.biblio.database.Entites.BookEntity;

import java.util.List;

public class showBooksActivity extends AppCompatActivity {


    //Components
    ListView listBooks;
    SearchView searchView;
    ArrayAdapter<BookEntity> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_books);

        //instance of components
        searchView = (SearchView)findViewById(R.id.search_bar_book);
        listBooks = (ListView)findViewById(R.id.listBooks);

        //add items in the listView
        updateListBook();

        //set listener on listView items
        listBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Open DetailsBookActivity - Pass some values to get the details of the book
                Intent intent = new Intent(showBooksActivity.this, DetailsBookActivity.class);
                intent.putExtra("idBook", ((BookEntity) listBooks.getItemAtPosition(position)).getIdBook());
                intent.putExtra("idCategory", ((BookEntity) listBooks.getItemAtPosition(position)).getFkCategory());
                intent.putExtra("idAuthor", ((BookEntity) listBooks.getItemAtPosition(position)).getFkAuthor());
                startActivity(intent);
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

    //update the listView if we come back
    @Override
    public void onResume() {
        super.onResume();
        updateListBook();
    }

    //Update the listView depending of where we come from (Books from a specific category or specific author)
    private void updateListBook(){
        // get the fkAuthor or fkCategory if we access from Author or Category
        int fkAuthor = getIntent().getIntExtra("idAuthor", -1);
        int fkCategory = getIntent().getIntExtra("idCategory", -1);

        //No intExtra --> allbooks
        if(fkAuthor+fkCategory==-2) {
            searchView.setQueryHint(getText(R.string.search));
            adapter = new ArrayAdapter<BookEntity>(showBooksActivity.this, android.R.layout.simple_list_item_1, getBooks());
        }
        //No fkAuthor --> category
        else if(fkAuthor==-1) {
            searchView.setQueryHint(MainActivity.db.categoryDao().getById(fkCategory).toString());
            adapter = new ArrayAdapter<BookEntity>(showBooksActivity.this, android.R.layout.simple_list_item_1, getFromCategory(fkCategory));
        }
        //Other --> author
        else {
            searchView.setQueryHint(MainActivity.db.autorDao().getById(fkAuthor).toString());
            adapter = new ArrayAdapter<BookEntity>(showBooksActivity.this, android.R.layout.simple_list_item_1, getFromAuthor(fkAuthor));
        }
        adapter.notifyDataSetChanged();
        listBooks.setAdapter(adapter);
    }


    //get all books from database
    public List<BookEntity> getBooks(){
        return MainActivity.db.bookDao().getAll();
    }

    //get all books from a specific author
    public List<BookEntity> getFromAuthor(int idAuthor){return MainActivity.db.bookDao().getBooksByAuthor(idAuthor);}

    //get all books from a specific category
    public List<BookEntity> getFromCategory(int idCategory){return MainActivity.db.bookDao().getBooksByCategory(idCategory);}




    //Open addBookactivity
    public void addBook(View view) {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }




}
