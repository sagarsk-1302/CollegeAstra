package com.collegeastra.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.collegeastra.R;
import com.collegeastra.models.Librarian;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.collegeastra.utils.Constants.APPNAME;
import static com.collegeastra.utils.Constants.ISLOGGEDIN;

public class LibrarianHomeActivity extends AppCompatActivity {
    Button btn_addstudent;
    Button btn_addbook;
    TextInputLayout searchBook;
    TextInputEditText searchedBook;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_home);
        btn_addstudent = (Button) findViewById(R.id.btn_addstudent);
        btn_addbook = (Button) findViewById(R.id.btn_addbook);
        searchBook = (TextInputLayout) findViewById(R.id.search_bar);
        searchedBook = (TextInputEditText) findViewById(R.id.et_search);
        btn_addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });
        btn_addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
        searchBook.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book = searchedBook.getText().toString().trim();
                if(book.length() >= 1) {
                    Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
                    intent.putExtra("searchedBook", book);
                    startActivity(intent);
                }else {
                    ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
                    Snackbar.make(constraintLayout, "Enter Book Name", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean(ISLOGGEDIN,false);
            edit.apply();
            Intent intent = new Intent(LibrarianHomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

