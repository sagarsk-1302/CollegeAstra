package com.collegeastra.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.collegeastra.R;
import com.collegeastra.models.Book;
import com.collegeastra.models.Librarian;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.collegeastra.utils.Constants.APPNAME;
import static com.collegeastra.utils.Constants.EMAIL;
import static com.collegeastra.utils.Constants.ISLOGGEDIN;
import static com.collegeastra.utils.Constants.USERNAME;

public class LibrarianHomeActivity extends AppCompatActivity {

    Book book;
    Context context;
    TextInputLayout searchBook;
    TextInputEditText searchedBook;
    Chip chip;
    ChipGroup chipGroup;
    Button btn_addbook, btn_addstudent, btn_cse, btn_ise, btn_ece, btn_civ, btn_me, btn_bs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_home);
        getSupportActionBar().setTitle("CollegeAstra");
        getSupportActionBar().setSubtitle("Librarian");
        btn_addstudent = (Button) findViewById(R.id.btn_addstudent);
        btn_addbook = (Button) findViewById(R.id.btn_addbook);
        searchBook = (TextInputLayout) findViewById(R.id.search_bar);
        searchedBook = (TextInputEditText) findViewById(R.id.et_search);
        chipGroup = (ChipGroup) findViewById(R.id.sort_chip);
        btn_cse = (Button) findViewById(R.id.btn_cs);
        btn_ise = (Button) findViewById(R.id.btn_is);
        btn_ece = (Button) findViewById(R.id.btn_ec);
        btn_civ = (Button) findViewById(R.id.btn_civ);
        btn_me = (Button) findViewById(R.id.btn_me);
        btn_bs = (Button) findViewById(R.id.btn_basicsci);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

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
                String query = searchedBook.getText().toString().trim();
                if (query.length() >= 1) {
                    if (chipGroup.getCheckedChipIds().size() != 0) {
                        switch (chipGroup.getCheckedChipId()) {
                            case R.id.chip_book:
                                Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
                                intent.putExtra("searchedBook", query);
                                startActivity(intent);
                                break;
                            case R.id.chip_bookid:
                                Intent intent1 = new Intent(LibrarianHomeActivity.this, BookIdActivity.class);
                                intent1.putExtra("bookId", query);
                                startActivity(intent1);
                                break;
                            case R.id.chip_usn:
                                Intent intent2 = new Intent(LibrarianHomeActivity.this, UsnActivity.class);
                                intent2.putExtra("usn", query);
                                startActivity(intent2);
                                break;
                        }

                    } else {
                        Snackbar.make(constraintLayout, "Please select a chip", Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Snackbar.make(constraintLayout, "Field is Empty", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        btn_cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "CSE");
                startActivity(intent);
            }
        });
        btn_ise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "ISE");
                startActivity(intent);
            }
        });
        btn_ece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "ECE");
                startActivity(intent);
            }
        });
        btn_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "ME");
                startActivity(intent);
            }
        });
        btn_civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "CIV");
                startActivity(intent);
            }
        });
        btn_bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
                intent.putExtra("searchedBook", "BS");
                startActivity(intent);
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
        SharedPreferences sharedPreferences = getSharedPreferences(APPNAME, MODE_PRIVATE);
        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear();
            edit.apply();
            Intent intent = new Intent(LibrarianHomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else if(id == R.id.details){
            String details = "Name : "+sharedPreferences.getString(USERNAME,"")+"\nEmail : "+sharedPreferences.getString(EMAIL,"");
            new AlertDialog.Builder(LibrarianHomeActivity.this)
                    .setTitle(sharedPreferences.getString(USERNAME,""))
                    .setMessage(details)
                    .setPositiveButton("Close", null)
                    .show();
        }else if(id == R.id.aboutus){
            String details = "Adithya Sunder (1VI18CS002)\nSahana (1VI18CS091)\nSagar S K (1VI18CS090)";
            new AlertDialog.Builder(LibrarianHomeActivity.this)
                    .setTitle("Team Details")
                    .setMessage(details)
                    .setPositiveButton("Close", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}

