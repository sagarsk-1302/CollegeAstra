package com.collegeastra.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeastra.R;
import com.collegeastra.adapters.SearchAdapter;
import com.collegeastra.models.Book;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchActivity extends AppCompatActivity {

    TextView searchedName;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    SearchAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        firebaseFirestore = FirebaseFirestore.getInstance();
        String searchedBook = getIntent().getStringExtra("searchedBook");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        searchedName = (TextView)findViewById(R.id.tv_search);
        searchedName.setText("You searched for "+searchedBook);
        Query query = firebaseFirestore.collection("books").whereGreaterThanOrEqualTo("title",searchedBook)
                .whereLessThanOrEqualTo("title",searchedBook+"\\uf8ff");
        FirestoreRecyclerOptions<Book> book = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query,Book.class).build();
        adapter = new SearchAdapter(this,book);
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

