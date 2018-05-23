package com.caroline.vlado.biblio;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.caroline.vlado.biblio.database.Entites.AutorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailsAuthorActivity extends AppCompatActivity {


    //Components
    FloatingActionButton done;
    FloatingActionButton edit;
    FloatingActionButton delete;
    FloatingActionButton seeBook;
    AutorEntity thisAuthor;
    EditText et_lastname;
    EditText et_fistname;
    EditText dp_date;
    EditText et_bio;

    Toast errorToast;
    Toast deleteToast;
    Toast doneToast;


    //Date components
    private DatePickerDialog datePickerDialog;
    private int month;
    private int year;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_author);


        //instance of components
        done = findViewById(R.id.addAuthorButton);
        edit = findViewById(R.id.editAuthorButton);
        delete = findViewById(R.id.deleteAuthorButton);
        seeBook = findViewById(R.id.seeBooks);
        et_lastname = findViewById(R.id.et_add_author_lastname);
        et_fistname = findViewById(R.id.et_add_author_firstname);
        et_bio = findViewById(R.id.et_add_author_bio);
        dp_date = findViewById(R.id.sp_add_author_date);


        // instance of toast
        doneToast = Toast.makeText(this, getString(R.string.AuthorSaved), Toast.LENGTH_SHORT);
        errorToast = Toast.makeText(this, getString(R.string.FillAll), Toast.LENGTH_SHORT);
        deleteToast = Toast.makeText(this, getString(R.string.itemDeleted), Toast.LENGTH_SHORT);

        //get idAuthor from previous activity
        final String uidAuthor = getIntent().getStringExtra("uidAuthor");
        System.out.println("-------------uidAuthor get ----------" + uidAuthor);

        //get the details from the database
        FirebaseDatabase.getInstance()
                .getReference("authors/" + uidAuthor)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            thisAuthor = dataSnapshot.getValue(AutorEntity.class);
                            thisAuthor.setUid(uidAuthor);
                            //set the editText from details
                            et_fistname.setText(thisAuthor.getFirstName());
                            et_lastname.setText(thisAuthor.getLastName());
                            et_bio.setText(thisAuthor.getBiography());
                            dp_date.setText(thisAuthor.getBirthday());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });




        //disable the unused button and editText to avoid modification
        done.setEnabled(false);
        delete.setEnabled(false);

        et_fistname.setEnabled(false);
        et_lastname.setEnabled(false);
        et_bio.setEnabled(false);
        dp_date.setEnabled(false);





        //set the listener to switch in edit mode
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                done.setEnabled(true);
                delete.setEnabled(true);

                edit.setEnabled(false);
                seeBook.setEnabled(false);

                et_fistname.setEnabled(true);
                et_lastname.setEnabled(true);
                dp_date.setEnabled(true);
                et_bio.setEnabled(true);
            }
        });

        //delete the author from the database
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update in database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("authors/" + uidAuthor)
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
                if(et_fistname.getText().toString().matches("")
                        || et_lastname.getText().toString().matches("")
                        || et_bio.getText().toString().matches("")
                        || dp_date.getText().toString().matches("")){
                    errorToast.show();
                    return;
                }

                //get the value form editView
                thisAuthor.setFirstName(et_fistname.getText().toString());
                thisAuthor.setLastName(et_lastname.getText().toString());
                thisAuthor.setBiography(et_bio.getText().toString());
                thisAuthor.setBirthday(dp_date.getText().toString());

                //update in database
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                database.getReference("authors/" + uidAuthor)
                        .setValue(thisAuthor, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    doneToast.show();
                                }
                            }
                        });

                //switch to view mode
                delete.setEnabled(false);
                done.setEnabled(false);
                seeBook.setEnabled(true);
                edit.setEnabled(true);
                et_fistname.setEnabled(false);
                et_lastname.setEnabled(false);
                dp_date.setEnabled(false);
                et_bio.setEnabled(false);

                //info toast
                doneToast.show();
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
                datePickerDialog = new DatePickerDialog(DetailsAuthorActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        //Open DetailsAuthorActivity with the idAuthor
        seeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsAuthorActivity.this, showBooksActivity.class);
                intent.putExtra("uidAuthor", thisAuthor.getUid());
                startActivity(intent);
            }
        });


    }
}
