package com.collegeastra.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.collegeastra.R;
import com.collegeastra.adapters.SearchAdapter;
import com.collegeastra.adapters.UsnAdapter;
import com.collegeastra.models.Student;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UsnActivity extends AppCompatActivity {

    TextView usn;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    UsnAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usn);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        usn = (TextView) findViewById(R.id.tv_usn);
        String usn = getIntent().getStringExtra("usn");
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("student").whereGreaterThanOrEqualTo("usn",usn)
                .whereLessThanOrEqualTo("usn", usn + "\\uf8ff");
        FirestoreRecyclerOptions<Student> student = new FirestoreRecyclerOptions.Builder<Student>().setQuery(query,Student.class).build();
        adapter = new UsnAdapter(this, student);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}