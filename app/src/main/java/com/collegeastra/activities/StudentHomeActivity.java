package com.collegeastra.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.collegeastra.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.collegeastra.utils.Constants.APPNAME;
import static com.collegeastra.utils.Constants.ISLOGGEDIN;

public class StudentHomeActivity extends AppCompatActivity {
    TextInputLayout searchBook;
    TextInputEditText searchedBook;
    Button btn_cse,btn_ise,btn_ece,btn_civ,btn_me,btn_bs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        searchBook = (TextInputLayout) findViewById(R.id.search_bar);
        searchedBook = (TextInputEditText) findViewById(R.id.et_search);
        btn_cse = (Button)findViewById(R.id.btn_cs);
        btn_ise = (Button)findViewById(R.id.btn_is);
        btn_ece = (Button)findViewById(R.id.btn_ec);
        btn_civ = (Button)findViewById(R.id.btn_civ);
        btn_me = (Button)findViewById(R.id.btn_me);
        btn_bs = (Button)findViewById(R.id.btn_basicsci);

        btn_cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "CSE");
                startActivity(intent);
            }
        });
        btn_ise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "ISE");
                startActivity(intent);
            }
        });
        btn_ece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "ECE");
                startActivity(intent);
            }
        });
        btn_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "ME");
                startActivity(intent);
            }
        });
        btn_civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "CIV");
                startActivity(intent);
            }
        });
        btn_bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "BS");
                startActivity(intent);
            }
        });

        searchBook.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book = searchedBook.getText().toString().trim();
                if(book.length() >= 1) {
                    Intent intent = new Intent(StudentHomeActivity.this, SearchActivity.class);
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
            Intent intent = new Intent(StudentHomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
