package com.collegeastra.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.collegeastra.R;

import static com.collegeastra.utils.Constants.*;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
        if(sharedPreferences.getBoolean(ISLOGGEDIN,false)){
            if(sharedPreferences.getBoolean(ISSTUDENT,false)){
                //TODO Call student homepage activity
            }
            else {
                //TODO Call librarian homepage activity
            }
        }
        else {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            //TODO call login activity
        }
//        setContentView(R.layout.activity_main);
    }
}