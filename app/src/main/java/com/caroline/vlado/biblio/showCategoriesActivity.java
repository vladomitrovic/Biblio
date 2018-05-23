package com.caroline.vlado.biblio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.caroline.vlado.biblio.database.Entites.CategoryEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

                            listCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                                               final int index, long arg3) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(showCategoriesActivity.this)
                                            .setTitle(((CategoryEntity) listCategories.getItemAtPosition(index)).getCategoryName())
                                            .setMessage(R.string.questionLongclickCategory)
                                            .setPositiveButton(R.string.DeleteCategory, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    String uidCategory = ((CategoryEntity) listCategories.getItemAtPosition(index)).getUid();
                                                    //update in database
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    database.getReference("categories/" + uidCategory)
                                                            .setValue(null, new DatabaseReference.CompletionListener() {
                                                                @Override
                                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                }
                                                            });
                                                }
                                            })
                                            .setNegativeButton(R.string.modifyCategory, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    final String uidCategory = ((CategoryEntity) listCategories.getItemAtPosition(index)).getUid();
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(showCategoriesActivity.this);
                                                    builder.setTitle("Category name");

                                                    // Set up the input
                                                    final EditText input = new EditText(showCategoriesActivity.this);
                                                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                                                    builder.setView(input);

                                                    // Set up the buttons
                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //update in database
                                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                            database.getReference("categories/" + uidCategory + "/categoryName")
                                                                    .setValue(input.getText().toString(), new DatabaseReference.CompletionListener() {
                                                                        @Override
                                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                        }
                                                                    });
                                                        }
                                                    });
                                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();
                                                        }
                                                    });

                                                    builder.show();

                                                }
                                            });

                                    builder.show();
                                    return true;
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
