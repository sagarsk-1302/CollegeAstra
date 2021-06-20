package com.collegeastra.activities;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.collegeastra.models.Copy;

public class RecordActivity extends AppCompatActivity {
    static String bookIdlocal;
    public static Intent start(Context context, String bookId) {
        Intent intent = new Intent(context,RecordActivity.class);
        bookIdlocal = bookId;
        return intent;
    }
}
