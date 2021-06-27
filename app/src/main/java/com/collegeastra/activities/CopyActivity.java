package com.collegeastra.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeastra.R;
import com.collegeastra.adapters.CopyAdapter;
import com.collegeastra.adapters.SearchAdapter;
import com.collegeastra.models.Copy;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.collegeastra.utils.Constants.APPNAME;

public class CopyActivity extends AppCompatActivity {
    String bookIdlocal;
    CopyAdapter adapter;
    RecyclerView recyclerView;
    TextView bookid;
    Boolean user;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        bookid = (TextView) findViewById(R.id.bookid);
        bookIdlocal = getIntent().getStringExtra("bookId");
        firebaseFirestore = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(APPNAME, MODE_PRIVATE);
        bookid.setText(bookIdlocal);
        user = sharedPreferences.getBoolean("ISLIBRARIAN",true);
        Query query = firebaseFirestore.collection("books").document(bookIdlocal).collection("copy");
        FirestoreRecyclerOptions<Copy> copy = new FirestoreRecyclerOptions.Builder<Copy>().setQuery(query,Copy.class).build();
        adapter = new CopyAdapter(this,copy,user,bookIdlocal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
