package com.collegeastra.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.collegeastra.R;
import com.collegeastra.models.Book;
import com.collegeastra.models.Copy;
import com.collegeastra.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    EditText et_title,et_author,et_edition,et_pubYear,et_desc,et_bookId,et_copies;
    ChipGroup department;
    Button btn_addBook;
    List<String> depts;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        et_title = (EditText)findViewById(R.id.et_bookname);
        et_author = (EditText)findViewById(R.id.et_authorname);
        et_edition = (EditText)findViewById(R.id.et_edition);
        et_pubYear = (EditText)findViewById(R.id.et_pubyear);
        et_desc = (EditText)findViewById(R.id.et_description);
        et_bookId = (EditText)findViewById(R.id.et_bookid);
        et_copies= (EditText)findViewById(R.id.et_nocopies);
        department = (ChipGroup) findViewById(R.id.bookchipgroup);
        btn_addBook = (Button)findViewById(R.id.addbook);
        firebaseFirestore = FirebaseFirestore.getInstance();
        btn_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AddBook",department.toString());
                String title = et_title.getText().toString();
                String author = et_author.getText().toString();
                String edition = et_edition.getText().toString();
                String pubyear = et_pubYear.getText().toString();
                String desc = et_desc.getText().toString();
                String bookId = et_bookId.getText().toString();
                String copies = et_copies.getText().toString();
                Log.d("AddBook", String.valueOf(department.getCheckedChipIds().size()));
                if (!title.isEmpty() && !author.isEmpty() && !edition.isEmpty() && !pubyear.isEmpty() && !desc.isEmpty() && !bookId.isEmpty() && !copies.isEmpty() && department.getCheckedChipIds().size() != 0) {
                    depts = new ArrayList<>();
                    for(int i: department.getCheckedChipIds()){
                        Chip chip = (Chip) findViewById(i);
                        Log.d("AddBook",chip.getText().toString().trim());
                        depts.add(chip.getText().toString().trim());
                    }
//                    Log.d("AddBook",depts.toString());
                    DocumentReference documentReference = firebaseFirestore.collection("books").document(bookId);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                    Snackbar.make(btn_addBook, "Book already exists", Snackbar.LENGTH_SHORT).setAnchorView(btn_addBook).show();
                                } else {
                                    Book book = new Book(bookId, title, author, edition, pubyear, depts, copies, desc);
                                    firebaseFirestore.collection("books").document(bookId).set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            GenerateCopy(copies,bookId);
                                            Snackbar.make(btn_addBook, "The Book has been added", Snackbar.LENGTH_SHORT).setAnchorView(btn_addBook).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(btn_addBook, "Error occured", Snackbar.LENGTH_SHORT).setAnchorView(btn_addBook).show();
                                        }
                                    });
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(btn_addBook, "Error occured", Snackbar.LENGTH_SHORT).setAnchorView(btn_addBook).show();
                        }
                    });
                } else {
                    Snackbar.make(btn_addBook, "Fill all the fields", Snackbar.LENGTH_SHORT).setAnchorView(btn_addBook).show();
                }
            }
        });

    }
    private void GenerateCopy(String copy, String bookId){
        CollectionReference collectionReference = firebaseFirestore.collection("books").document(bookId)
                .collection("copy");
        for(int i=1;i<= Integer.valueOf(copy);i++){
            String docId = bookId+"_"+i;
            Copy copyObj = new Copy();
            copyObj.setBookId(bookId);
            copyObj.setCopyId(docId);
            collectionReference.document(docId).set(copyObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Copy","created");
                }
            });
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