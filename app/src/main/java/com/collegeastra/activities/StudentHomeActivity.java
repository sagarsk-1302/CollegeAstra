package com.collegeastra.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.collegeastra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.collegeastra.utils.Constants.APPNAME;
import static com.collegeastra.utils.Constants.EMAIL;
import static com.collegeastra.utils.Constants.ISLOGGEDIN;
import static com.collegeastra.utils.Constants.USERNAME;
import static com.collegeastra.utils.Constants.USN;

public class StudentHomeActivity extends AppCompatActivity {
    TextInputLayout searchBook;
    TextInputEditText searchedBook;
    Button btn_cse,btn_ise,btn_ece,btn_civ,btn_me,btn_bs;
    TextView book1,book2,date1,date2;
    LinearLayout row1,row2;
    String usn;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        searchBook = (TextInputLayout) findViewById(R.id.search_bar);
        searchedBook = (TextInputEditText) findViewById(R.id.et_search);
        getSupportActionBar().setTitle("CollegeAstra");
        getSupportActionBar().setSubtitle("Student");
        btn_cse = (Button)findViewById(R.id.btn_cs);
        btn_ise = (Button)findViewById(R.id.btn_is);
        btn_ece = (Button)findViewById(R.id.btn_ec);
        btn_civ = (Button)findViewById(R.id.btn_civ);
        btn_me = (Button)findViewById(R.id.btn_me);
        btn_bs = (Button)findViewById(R.id.btn_basicsci);
        book1 = findViewById(R.id.book1);
        book2 =findViewById(R.id.book2);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        row1 =findViewById(R.id.firstrow);
        row2 = findViewById(R.id.secondrow);
        firebaseFirestore = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
        usn = sharedPreferences.getString("USN","");
        Log.d("USN",usn+"");
        firebaseFirestore.collection("student").document(usn).collection("records").whereEqualTo("returned",false)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> list = querySnapshot.getDocuments();
                    Log.d("size",list.size()+"");
                    if(list.size() == 0){
                        row1.setVisibility(View.GONE);
                        row2.setVisibility(View.GONE);
                    }
                    else if(list.size() == 1){
                        row2.setVisibility(View.GONE);
                        book1.setText(list.get(0).getString("copyid"));
                        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy");
                        String returndate = formatter.format(new Date(list.get(0).getTimestamp("returnedOn").getSeconds() * 1000L));
                        date1.setText(returndate);
                    }
                    else {
                        book1.setText(list.get(0).getString("copyid"));
                        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy");
                        String returndate = formatter.format(new Date(list.get(0).getTimestamp("returnedOn").getSeconds() * 1000L));
                        date1.setText(returndate);
                        book2.setText(list.get(1).getString("copyid"));
                        String returndate2 = formatter.format(new Date(list.get(1).getTimestamp("returnedOn").getSeconds() * 1000L));
                        date2.setText(returndate2);
                        Log.d("Return1",returndate);
                        Log.d("Return2",returndate2);
                    }

                }
            }
        });

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
        SharedPreferences sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear();
            edit.apply();
            Intent intent = new Intent(StudentHomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else if(id == R.id.details){
            String details = "Name : "+sharedPreferences.getString(USERNAME,"")+"\nUSN : "+sharedPreferences.getString(USN,"")+"\nEmail : "+sharedPreferences.getString(EMAIL,"");
            new AlertDialog.Builder(StudentHomeActivity.this)
                    .setTitle(sharedPreferences.getString(USERNAME,""))
                    .setMessage(details)
                    .setPositiveButton("Close", null)
                    .show();
        }else if(id == R.id.aboutus){
            String details = "Adithya Sunder (1VI18CS002)\nSahana (1VI18CS091)\nSagar S K (1VI18CS090)";
            new AlertDialog.Builder(StudentHomeActivity.this)
                    .setTitle("Team Details")
                    .setMessage(details)
                    .setPositiveButton("Close", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
