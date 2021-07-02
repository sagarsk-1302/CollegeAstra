package com.collegeastra.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.collegeastra.R;
import com.collegeastra.models.Librarian;
import com.collegeastra.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.collegeastra.utils.Constants.APPNAME;
import static com.collegeastra.utils.Constants.EMAIL;
import static com.collegeastra.utils.Constants.ISLIBRARIAN;
import static com.collegeastra.utils.Constants.ISLOGGEDIN;
import static com.collegeastra.utils.Constants.ISSTUDENT;
import static com.collegeastra.utils.Constants.USERNAME;
import static com.collegeastra.utils.Constants.USN;

public class LoginActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    EditText et_username,et_password;
    Button login;
    Chip chip_lib, chip_student;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseFirestore = FirebaseFirestore.getInstance();

        et_username = (EditText)findViewById(R.id.username);
        et_password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        chip_lib = (Chip)findViewById(R.id.chip_lib);
        chip_student = (Chip)findViewById(R.id.chip_student);
        constraintLayout = (ConstraintLayout)findViewById(R.id.const_layout);
        chip_lib.setChecked(true);
        chip_lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chip_lib.setChecked(true);
                chip_student.setChecked(false);
                et_username.setHint("Username");
//                constraintLayout.setBackgroundResource(R.color.black);
            }
        });
        chip_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chip_lib.setChecked(false);
                chip_student.setChecked(true);
                et_username.setHint("USN");
//                constraintLayout.setBackgroundResource(R.color.white);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if(chip_lib.isChecked()) {
                    if (username.length() != 0 && password.length() != 0) {
                        firebaseFirestore.collection("librarian").document(username).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        if (documentSnapshot != null && documentSnapshot.exists()) {
                                            Log.d("LoginActivity",documentSnapshot.toString());
                                            Librarian librarian = documentSnapshot.toObject(Librarian.class);
                                            if (librarian.getPassword().equals(password)) {
                                                SharedPreferences sharedPreferences = getSharedPreferences(APPNAME, MODE_PRIVATE);
                                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                                edit.putString(USERNAME, username);
                                                edit.putString(EMAIL, librarian.getEmail());
                                                edit.putBoolean(ISLOGGEDIN, true);
                                                edit.putBoolean(ISLIBRARIAN, true);
                                                edit.putBoolean(ISSTUDENT,false);
                                                edit.apply();
//                                                Toast.makeText(LoginActivity.this,"Login Success for Librarian",Toast.LENGTH_LONG).show();
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(LoginActivity.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Login Failed for Librarian",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(LoginActivity.this, "Please enter the credentials", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if (username.length() != 0 && password.length() != 0) {
                        Log.d("LoginActivity.java", "onClick: "+username+" "+password);
                        firebaseFirestore.collection("student").document(username).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        if (documentSnapshot != null && documentSnapshot.exists()) {
                                            Student student = documentSnapshot.toObject(Student.class);
                                            if (student.getPassword().equals(password)) {
                                                SharedPreferences sharedPreferences = getSharedPreferences(APPNAME, MODE_PRIVATE);
                                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                                edit.putString(USN, student.getUSN());
                                                edit.putString(USERNAME, student.getName());
                                                edit.putString(EMAIL, student.getEmail());
                                                edit.putBoolean(ISLOGGEDIN, true);
                                                edit.putBoolean(ISSTUDENT, true);
                                                edit.putBoolean(ISLIBRARIAN,false);
                                                edit.apply();
//                                                Toast.makeText(LoginActivity.this,"Login Success for Student",Toast.LENGTH_LONG).show();
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(LoginActivity.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Login Failed for Student",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(LoginActivity.this, "Please enter the credentials", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }


}
