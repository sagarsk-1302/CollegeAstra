package com.collegeastra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.collegeastra.R;

public class LibrarianHomeActivity extends AppCompatActivity {
    Button btn_addstudent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_home);
        btn_addstudent = (Button) findViewById(R.id.btn_addstudent);
        btn_addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianHomeActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });
    }
}
