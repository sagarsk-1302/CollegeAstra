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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.collegeastra.utils.Constants.APPNAME;
import static com.collegeastra.utils.Constants.ISLOGGEDIN;

public class LibrarianHomeActivity extends AppCompatActivity {
    Button btn_addstudent;
    Button btn_addbook;
    Book book;
    Context context;
    TextInputLayout searchBook;
    TextInputEditText searchedBook;
    Chip chip;
    ChipGroup chipGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_home);
        btn_addstudent = (Button) findViewById(R.id.btn_addstudent);
        btn_addbook = (Button) findViewById(R.id.btn_addbook);
        searchBook = (TextInputLayout) findViewById(R.id.search_bar);
        searchedBook = (TextInputEditText) findViewById(R.id.et_search);
        chipGroup = (ChipGroup)findViewById(R.id.sort_chip);

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
                if (book.length() >= 1) {
                    if(chipGroup.getCheckedChipIds().size() != 0){
                        for(int i: chipGroup.getCheckedChipIds()){
                            chip = (Chip) findViewById(i);
                            Log.d("LibHome",chip.getText().toString().trim());
                        }
                        if(chip.getText().toString().trim().equals("BOOK")){
                            Intent intent = BookDetailsActivity.start(context,book);
                            context.startActivity(intent);
                            intent.putExtra("searchedBook", book);
                            intent.putExtra("chipChecked",true);
                            Log.d("Bookname ", book);
                        }
                    }
//                    Intent intent = new Intent(LibrarianHomeActivity.this, SearchActivity.class);
//                    intent.putExtra("searchedBook", book);
//                    startActivity(intent);
                } else {
                    ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
                    Snackbar.make(constraintLayout, "Enter Book Name", Snackbar.LENGTH_SHORT).show();
                }

            }
        });


//        if (chip_sort.isSelected()) {
//            searchBook.setEndIconOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switch (chip_sort.getCheckedChipId()) {
//                        case R.id.chip_usn:
//                            break;
//                        case R.id.chip_book:
//                            String book = searchedBook.getText().toString().trim();
//                            Intent intent = new Intent(LibrarianHomeActivity.this, BookDetailsActivity.class);
//                            intent.putExtra("searchedBook", book);
//                            startActivity(intent);
//                            break;
//                        case R.id.chip_bookid:
//                            break;
//                        default:
////                            Toast.makeText(this, "Select Valid Chip", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
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

