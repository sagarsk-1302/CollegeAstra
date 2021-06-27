package com.collegeastra.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
import static com.collegeastra.utils.Constants.ISLOGGEDIN;

public class LibrarianHomeActivity extends AppCompatActivity {

    Book book;
    Context context;
    TextInputLayout searchBook;
    TextInputEditText searchedBook;
    Chip chip;
    ChipGroup chipGroup;
    Button   btn_addbook,btn_addstudent,btn_cse,btn_ise,btn_ece,btn_civ,btn_me,btn_bs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_home);
        btn_addstudent = (Button) findViewById(R.id.btn_addstudent);
        btn_addbook = (Button) findViewById(R.id.btn_addbook);
        searchBook = (TextInputLayout) findViewById(R.id.search_bar);
        searchedBook = (TextInputEditText) findViewById(R.id.et_search);
        chipGroup = (ChipGroup) findViewById(R.id.sort_chip);
        btn_cse = (Button)findViewById(R.id.btn_cs);
        btn_ise = (Button)findViewById(R.id.btn_is);
        btn_ece = (Button)findViewById(R.id.btn_ec);
        btn_civ = (Button)findViewById(R.id.btn_civ);
        btn_me = (Button)findViewById(R.id.btn_me);
        btn_bs = (Button)findViewById(R.id.btn_basicsci);

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
                        for (int i : chipGroup.getCheckedChipIds()) {
                            chip = (Chip) findViewById(i);
                            Log.d("LibHome", chip.getText().toString().trim());
                        }
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

                    }

                } else {
                    ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
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

        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(APPNAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean(ISLOGGEDIN, false);
            edit.apply();
            Intent intent = new Intent(LibrarianHomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

