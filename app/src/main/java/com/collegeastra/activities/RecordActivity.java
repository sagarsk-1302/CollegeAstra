package com.collegeastra.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeastra.R;
import com.collegeastra.adapters.RecordAdapter;
import com.collegeastra.adapters.StudentRecordAdapter;
import com.collegeastra.models.Record;
import com.collegeastra.models.StudentRecord;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RecordActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentRecordAdapter studentRecordAdapter;
    RecordAdapter recordAdapter;
    static Boolean type;
    static String USN;
    static String copyid;
    static String bookid;
    FirebaseFirestore firebaseFirestore;
    Query query;
    public static Intent studentRecordStart(Context context,String usn){
        Intent intent = new Intent(context,RecordActivity.class);
        USN= usn;
        type = true;
        return intent;
    }
    public static Intent recordStart(Context context,String copyId,String bookId){
        Intent intent = new Intent(context,RecordActivity.class);
        copyid= copyId;
        bookid = bookId;
        type = false;
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_recordsoftransaction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        if(type){
            query = firebaseFirestore.collection("student").document(USN).collection("records");
            FirestoreRecyclerOptions<StudentRecord> options = new FirestoreRecyclerOptions.Builder<StudentRecord>().setQuery(query,StudentRecord.class).build();
            studentRecordAdapter = new StudentRecordAdapter(RecordActivity.this,options);
            recyclerView.setAdapter(studentRecordAdapter);
        }else{
            query = firebaseFirestore.collection("books").document(bookid).collection("copy").document(copyid).collection("records");
            FirestoreRecyclerOptions<Record> options = new FirestoreRecyclerOptions.Builder<Record>().setQuery(query,Record.class).build();
            recordAdapter = new RecordAdapter(RecordActivity.this,options);
            recyclerView.setAdapter(recordAdapter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(type){
            studentRecordAdapter.startListening();
        }
        else {
            recordAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(type){
        studentRecordAdapter.stopListening();}
        else {
            recordAdapter.stopListening();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
