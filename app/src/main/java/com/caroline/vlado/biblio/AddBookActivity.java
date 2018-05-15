package com.caroline.vlado.biblio;

import android.app.DatePickerDialog;
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

public class AddBookActivity extends AppCompatActivity {

    //Components
    FloatingActionButton add;
    EditText et_Title;
    EditText et_Summary;
    EditText dp_date;
    Spinner author;
    Spinner category;

    BookEntity newBook;
    Toast errorToast;
    Toast categorieORauthorNull;
    Toast toast;

    //Date components
    private DatePickerDialog datePickerDialog;
    private int month;
    private int year;
    private int day;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        //instance of components
        add = findViewById(R.id.addBookButton);
        et_Title = findViewById(R.id.et_add_book_title);
        et_Summary = findViewById(R.id.et_add_book_summary);
        dp_date = findViewById(R.id.sp_add_course_date);
        author = findViewById(R.id.sp_add_book_author);
        category = findViewById(R.id.sp_add_book_categorie);

        errorToast = Toast.makeText(this, getString(R.string.FillAll), Toast.LENGTH_SHORT);
        toast = Toast.makeText(this, getString(R.string.BookSaved), Toast.LENGTH_SHORT);

        newBook = new BookEntity();

        //add items in the spinner category
        ArrayList<CategoryEntity> categories = null;
        ArrayAdapter<CategoryEntity> dataAdapterCategories = new ArrayAdapter<CategoryEntity>(AddBookActivity.this, android.R.layout.simple_spinner_item, categories);
        dataAdapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapterCategories);

        //add items in the spinner author
        ArrayList<AutorEntity> authors = null;
        ArrayAdapter<AutorEntity> dataAdapterAuthor = new ArrayAdapter<AutorEntity>(AddBookActivity.this, android.R.layout.simple_spinner_item, authors);
        dataAdapterAuthor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        author.setAdapter(dataAdapterAuthor);




        //Open the datePickerDialog
        dp_date.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Get current time
                final Calendar c = Calendar.getInstance();
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                day = c.get(Calendar.DAY_OF_MONTH);

                //Launch date Picker Dialog
                datePickerDialog = new DatePickerDialog(AddBookActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String courseDate = formatDateString(dayOfMonth+"."+month+"."+year);
                        dp_date.setText(courseDate);
                    }

                    //format the date in string
                    private String formatDateString(String dateString) {SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        Date date;
                        try {
                            date = dateFormat.parse(dateString);
                            dateString = dateFormat.format(date);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return dateString;
                    }
                },year,month, day);
                datePickerDialog.show();
            }
        });

        //Add button
        add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                //Check if all field are filled
                    if(et_Title.getText().toString().matches("")
                            || et_Summary.getText().toString().matches("")
                            || dp_date.getText().toString().matches("")){
                        errorToast.show();
                        return;
                    }

                //set the name in the object
                newBook.setTitle(et_Title.getText().toString());
                newBook.setSummary(et_Summary.getText().toString());
                newBook.setDate(dp_date.getText().toString());
                    newBook.setIdAuthor(((AutorEntity) author.getSelectedItem()).getIdAutor());
                    newBook.setIdCategory(((Category) category.getSelectedItem()).getIdCategory());


                    //info toast
                toast.show();

                //back to the list
                onBackPressed();
            }
        });

    }


}

