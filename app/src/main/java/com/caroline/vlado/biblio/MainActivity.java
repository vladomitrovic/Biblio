package com.caroline.vlado.biblio;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    //Components
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView slider;
    private int backKey=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLastLanguage();


        //instance of components
        mDrawerLayout = findViewById(R.id.drawer);
        slider = findViewById(R.id.silder);

        //instance of navication drawer
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Navigation drawer listener
        slider.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.nav_about) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(R.string.about)
                                    .setMessage(R.string.aboutUs)
                                    .setPositiveButton(R.string.ok, null);
                            builder.show();
                            return onOptionsItemSelected(item);
                        }

                        if (id == R.id.french) {
                            changeLanguage("fr");
                        }
                        if (id == R.id.german) {
                            changeLanguage("de");
                        }
                        if (id == R.id.english) {
                            changeLanguage("en");
                        }
                        if (id == R.id.italian) {
                            changeLanguage("it");
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.Hello)
                                .setMessage(R.string.languageChanged)
                                .setPositiveButton(R.string.ok, null);
                        builder.show();

                        return onOptionsItemSelected(item);
                    }
                }
        );

    }


    //Behavior of navigation drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle != null && mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mToggle != null)
            mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mToggle != null)
            mToggle.onConfigurationChanged(newConfig);
    }


    //Open showBooksActivity
    public void showBooks(View view) {
        Intent intent = new Intent(this, showBooksActivity.class);
        startActivity(intent);
    }

    //Open showAuthorsActivity
    public void showAuthors(View view) {
        Intent intent = new Intent(this, showAuthorsActivity.class);
        startActivity(intent);
    }

    //Open showCategoriesActivity
    public void showCategories(View view) {
        Intent intent = new Intent(this, showCategoriesActivity.class);
        startActivity(intent);
    }


    //Asking if you want to leave the app
    public void onBackPressed() {

        if(backKey==0) {
            Toast toast = Toast.makeText(this, getString(R.string.rellayExit), Toast.LENGTH_LONG);
            toast.show();
            backKey++;
        }
        else {
            finish();
            System.exit(0);
        }

    }


    //Change the language
    public void changeLanguage (String toLoad) {
        Locale locale = new Locale(toLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale= locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANGUAGE", toLoad).commit();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    //load the last language when you run the app
    private void loadLastLanguage() {
        String language = PreferenceManager.getDefaultSharedPreferences(this).getString("LANGUAGE", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }







}
