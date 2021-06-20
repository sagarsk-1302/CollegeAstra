package com.collegeastra.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.collegeastra.R;
import com.collegeastra.models.Book;

public class BookDetailsActivity extends AppCompatActivity {
    static Book book;
    Button record;
    TextView bookName,author,edition,pubyear,desc,copiesAvail;

    public static Intent start(Context context, Book model) {
        Intent intent = new Intent(context,BookDetailsActivity.class);
        book = model;
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbook);
        bookName = findViewById(R.id.tv_bookname);
        author = findViewById(R.id.tv_author);
        edition = findViewById(R.id.tv_edition);
        pubyear = findViewById(R.id.tv_pubyear);
        desc = findViewById(R.id.tv_description);
        copiesAvail = findViewById(R.id.tv_noofcopies);
        record = findViewById(R.id.btn_records);
        bookName.setText(book.getTitle());
        author.setText(book.getAuthor());
        edition.setText(book.getEdition());
        pubyear.setText(book.getPubYear());
        desc.setText(book.getDescription());
        copiesAvail.setText(book.getNoofcopies());
        Log.d("BookDetails",book.getEdition());

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailsActivity.this,CopyActivity.class);
                intent.putExtra("bookId",book.getBookId());
                startActivity(intent);
            }
        });
    }
}
