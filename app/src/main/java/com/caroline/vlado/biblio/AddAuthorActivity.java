package com.caroline.vlado.biblio;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.caroline.vlado.biblio.database.Entites.AutorEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAuthorActivity extends AppCompatActivity {

    //Components
    FloatingActionButton add;
    EditText et_lastname;
    EditText et_fistname;
    EditText dp_date;
    EditText et_bio;

    AutorEntity newAuthor;
    Toast toast;
    Toast errorToast;

    //Date components
    private DatePickerDialog datePickerDialog;
    private int month;
    private int year;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);

        //instance of components
        add = findViewById(R.id.addAuthorButton);
        et_lastname = findViewById(R.id.et_add_author_lastname);
        et_fistname = findViewById(R.id.et_add_author_firstname);
        et_bio = findViewById(R.id.et_add_author_bio);
        dp_date = findViewById(R.id.sp_add_author_date);

        toast = Toast.makeText(this, getString(R.string.AuthorSaved), Toast.LENGTH_SHORT);
        errorToast = Toast.makeText(this, getString(R.string.FillAll), Toast.LENGTH_SHORT);

        newAuthor = new AutorEntity();

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
                datePickerDialog = new DatePickerDialog(AddAuthorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String courseDate = formatDateString(dayOfMonth + "." + month + "." + year);
                        dp_date.setText(courseDate);
                    }

                    //format the date in string CARO
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
                    ;
                }, year, month, day);
                datePickerDialog.show();
            }


        });

        //Add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all field are filled
                if (et_lastname.getText().toString().matches("")
                        || et_fistname.getText().toString().matches("")
                        || et_bio.getText().toString().matches("")) {
                    errorToast.show();
                    return;
                }

                //set the name in the object
                newAuthor.setLastName(et_lastname.getText().toString());
                newAuthor.setFirstName(et_fistname.getText().toString());
                newAuthor.setBiography(et_bio.getText().toString());
                newAuthor.setBirthday(dp_date.getText().toString());

                //insert in the database
                MainActivity.db.autorDao().insert(newAuthor);

                //info toast
                toast.show();

                //back to the list
                onBackPressed();
            }
        });

    }

}
