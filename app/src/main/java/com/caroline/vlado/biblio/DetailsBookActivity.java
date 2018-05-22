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

        //add items in the listView category
        ArrayList<CategoryEntity> categories = null;
        ArrayAdapter<CategoryEntity> dataAdapterCategories = new ArrayAdapter<CategoryEntity>(DetailsBookActivity.this, android.R.layout.simple_spinner_item, categories);
        dataAdapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapterCategories);

        //add items in the listView author
        ArrayList<AutorEntity> authors = null;
        ArrayAdapter<AutorEntity> dataAdapterAuthor = new ArrayAdapter<AutorEntity>(DetailsBookActivity.this, android.R.layout.simple_spinner_item, authors);
        dataAdapterAuthor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        author.setAdapter(dataAdapterAuthor);

        //get some values to get the details of the book
        int idBook = getIntent().getIntExtra("idBook", -1);
        int idAuthor = getIntent().getIntExtra("idAuthor", -1);
        int idCategory = getIntent().getIntExtra("idCategory", -1);

        //get the details from the database
        thisBook = null;
        thisAuthor = null;
        thisCategory = null;

        // instance of toast
        errorToast = Toast.makeText(this, getString(R.string.FillAll), Toast.LENGTH_SHORT);
        doneToast = Toast.makeText(this, getString(R.string.UpdateSaved), Toast.LENGTH_SHORT);
        deleteToast = Toast.makeText(this, getString(R.string.itemDeleted), Toast.LENGTH_SHORT);


        //set the editText from details
        et_Title.setText(thisBook.getTitle());
        et_Summary.setText(thisBook.getSummary());
        dp_date.setText(thisBook.getDate());
        author.setSelection(getIndex(author, thisAuthor.toString()));
        category.setSelection(getIndex(category, thisCategory.toString()));

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

                deleteToast.show();
                onBackPressed();
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

                System.out.println(thisBook.getSummary()+"-----"+thisBook.getTitle()+"-------------------");



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
                Intent intent = new Intent(DetailsBookActivity.this, DetailsAuthorActivity.class);
                intent.putExtra("idAuthor", thisBook.getAuthor());
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
