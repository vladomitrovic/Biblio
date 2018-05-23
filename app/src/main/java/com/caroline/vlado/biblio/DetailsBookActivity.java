package com.caroline.vlado.biblio;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.caroline.vlado.biblio.Model.Category;
import com.caroline.vlado.biblio.database.Entites.AutorEntity;
import com.caroline.vlado.biblio.database.Entites.BookEntity;
import com.caroline.vlado.biblio.database.Entites.CategoryEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DetailsBookActivity extends AppCompatActivity {

    //Components
    EditText et_Title;
    EditText et_Summary;
    EditText dp_date;
    Spinner author;
    Spinner category;
    FloatingActionButton done;
    FloatingActionButton edit;
    FloatingActionButton delete;
    FloatingActionButton seeAuthor;

    Toast errorToast;
    Toast doneToast;
    Toast deleteToast;

    BookEntity thisBook;
    AutorEntity thisAuthor;
    CategoryEntity thisCategory;
    ArrayList<CategoryEntity> mCategories;
    ArrayAdapter<CategoryEntity> dataAdapterCategories;
    ArrayList<AutorEntity> mAuthors;
    ArrayAdapter<AutorEntity> dataAdapterAuthor;
    //Date components
    private DatePickerDialog datePickerDialog;
    private int month;
    private int year;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_book);

        //instance of components
        done = findViewById(R.id.addBookButton);
        edit = findViewById(R.id.editBookButton);
        delete = findViewById(R.id.deleteBookButton);
        et_Title = findViewById(R.id.et_add_book_title);
        et_Summary = findViewById(R.id.et_add_book_summary);
        dp_date = findViewById(R.id.sp_add_course_date);
        author = findViewById(R.id.sp_add_book_author);
        category = findViewById(R.id.sp_add_book_categorie);
        seeAuthor = findViewById(R.id.seeAuthors);


        //get some values to get the details of the book
        final String uidBook = getIntent().getStringExtra("uidBook");
        String uidAuthor = getIntent().getStringExtra("uidAuthor");
        String uidCategory = getIntent().getStringExtra("uidCategory");


        mAuthors = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("authors")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mAuthors.clear();
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                AutorEntity entity = childSnapshot.getValue(AutorEntity.class);
                                entity.setUid(childSnapshot.getKey());
                                mAuthors.add(entity);
                                dataAdapterAuthor = new ArrayAdapter<AutorEntity>(DetailsBookActivity.this, android.R.layout.simple_spinner_item, mAuthors);
                                dataAdapterAuthor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                author.setAdapter(dataAdapterAuthor);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        mCategories = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mCategories.clear();
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                CategoryEntity entity = childSnapshot.getValue(CategoryEntity.class);
                                entity.setUid(childSnapshot.getKey());
                                mCategories.add(entity);
                                dataAdapterCategories = new ArrayAdapter<CategoryEntity>(DetailsBookActivity.this, android.R.layout.simple_spinner_item, mCategories);
                                dataAdapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(dataAdapterCategories);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        //get the author from the database
        thisAuthor = new AutorEntity();
        FirebaseDatabase.getInstance()
                .getReference("authors/" + uidAuthor)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            thisAuthor = dataSnapshot.getValue(AutorEntity.class);
                            thisBook.setUid(uidBook);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        //get the category from the database
        thisCategory = new CategoryEntity();
        FirebaseDatabase.getInstance()
                .getReference("categories/" + uidCategory)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            thisCategory = dataSnapshot.getValue(CategoryEntity.class);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        //get the details from the database
        FirebaseDatabase.getInstance()
                .getReference("books/" + uidBook)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            thisBook = dataSnapshot.getValue(BookEntity.class);
                            //set the editText from details
                            et_Title.setText(thisBook.getTitle());
                            et_Summary.setText(thisBook.getSummary());
                            dp_date.setText(thisBook.getDate());
                            author.setSelection(getIndex(author, thisAuthor.toString()));
                            category.setSelection(getIndex(category, thisCategory.toString()));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        // instance of toast
        errorToast = Toast.makeText(this, getString(R.string.FillAll), Toast.LENGTH_SHORT);
        doneToast = Toast.makeText(this, getString(R.string.UpdateSaved), Toast.LENGTH_SHORT);
        deleteToast = Toast.makeText(this, getString(R.string.itemDeleted), Toast.LENGTH_SHORT);




        //disable the unused button and editText to avoid modification
        done.setEnabled(false);
        delete.setEnabled(false);

        et_Title.setEnabled(false);
        et_Summary.setEnabled(false);
        dp_date.setEnabled(false);
        author.setClickable(false);
        category.setClickable(false);


        //set the listener to switch in edit mode
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                done.setEnabled(true);
                delete.setEnabled(true);

                seeAuthor.setEnabled(false);
                edit.setEnabled(false);

                et_Title.setEnabled(true);
                et_Summary.setEnabled(true);
                dp_date.setEnabled(true);
                author.setClickable(true);
                category.setClickable(true);

            }
        });

        //delete the book from the database
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update in database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("books/" + uidBook)
                        .setValue(null, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    deleteToast.show();
                                    onBackPressed();
                                }
                            }
                        });
            }
        });

        //update the database and switch in view mode
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all field are filled
                if(et_Title.getText().toString().matches("")
                        || et_Summary.getText().toString().matches("")
                        || dp_date.getText().toString().matches("")){
                    errorToast.show();
                    return;
                }

                //get the value form editView
                thisBook.setTitle(et_Title.getText().toString());
                thisBook.setSummary(et_Summary.getText().toString());
                thisBook.setDate(dp_date.getText().toString());
                thisBook.setUidAuthor(((AutorEntity) author.getSelectedItem()).getUid());
                thisBook.setUidCategory(((Category) category.getSelectedItem()).getUid());

                //update in database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("books/" + uidBook)
                        .setValue(thisBook, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    doneToast.show();
                                }
                            }
                        });


                //update in database

                //switch to view mode
                delete.setEnabled(false);
                done.setEnabled(false);
                seeAuthor.setEnabled(true);
                edit.setEnabled(true);
                et_Title.setEnabled(false);
                et_Summary.setEnabled(false);
                dp_date.setEnabled(false);
                author.setClickable(false);
                category.setClickable(false);

                //info toast
                doneToast.show();
            }
        });


        //Open showBookactivity and pass the idAuthor to get only his books
        seeAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-------------uidAuthor send ----------" + ((AutorEntity) author.getSelectedItem()).getUid());
                Intent intent = new Intent(DetailsBookActivity.this, DetailsAuthorActivity.class);
                intent.putExtra("uidAuthor", ((AutorEntity) author.getSelectedItem()).getUid());
                startActivity(intent);
            }
        });


        //Open the datePickerDialog
        dp_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Get current time
                final Calendar c = Calendar.getInstance();
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                day = c.get(Calendar.DAY_OF_MONTH);



                //Launch date Picker Dialog
                datePickerDialog = new DatePickerDialog(DetailsBookActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String courseDate = formatDateString(dayOfMonth + "." + month + "." + year);
                        dp_date.setText(courseDate);
                    }

                    //format the date in string
                    private String formatDateString(String dateString) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        Date date;
                        try {
                            date = dateFormat.parse(dateString);
                            dateString = dateFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return dateString;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }



    //return the index of a value in the spinner
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}
