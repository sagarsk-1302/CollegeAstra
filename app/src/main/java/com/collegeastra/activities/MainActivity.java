package com.collegeastra.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import static com.collegeastra.utils.Constants.*;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
        if(sharedPreferences.getBoolean(ISLOGGEDIN,false)){
            if(sharedPreferences.getBoolean(ISSTUDENT,false)){
                Intent intent = new Intent(MainActivity.this,StudentHomeActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(MainActivity.this,LibrarianHomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
//        setContentView(R.layout.activity_main);
    }
}