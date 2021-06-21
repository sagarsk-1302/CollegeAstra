package com.collegeastra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.collegeastra.R;
import com.collegeastra.models.Record;
import com.collegeastra.models.Student;
import com.collegeastra.models.StudentRecord;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IssueBookActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    DatePicker datePicker;
    TextView copy;
    TextInputEditText et_usn;
    Button issue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_issue_book);
        datePicker = findViewById(R.id.datePicker);
        copy = findViewById(R.id.tv_copyid);
        et_usn = findViewById(R.id.et_usn);
        issue = findViewById(R.id.issue_book);
        Intent intent = getIntent();
        String copyId = intent.getStringExtra("copyId");
        String bookId = intent.getStringExtra("bookId");
        copy.setText(copyId);
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn = et_usn.getText().toString().trim();
                //TODO link usn with student
                Date date = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                Timestamp returnDate = new Timestamp(date);
                Timestamp issueDate = new Timestamp(new Date());
                Record record = new Record(usn, issueDate, returnDate, copyId);
                StudentRecord studentRecord = new StudentRecord(copyId, issueDate, returnDate);
                DocumentReference documentReference2 = firebaseFirestore.collection("student").document(usn);
                documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                documentReference2.collection("records").whereEqualTo("returned", false)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                int count = value.size();
                                                if (count >= 2) {
                                                    Snackbar.make(issue, "The USN has already borrowed two books", Snackbar.LENGTH_LONG).show();
                                                } else {
                                                    DocumentReference documentReference1 = firebaseFirestore.collection("books").document(bookId)
                                                            .collection("copy").document(copyId);

                                                    documentReference1.collection("records").add(record)
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    Snackbar.make(issue, "Book has been issued!", Snackbar.LENGTH_LONG).show();
                                                                    Map<String, Object> data = new HashMap<>();
                                                                    data.put("available", false);
                                                                    documentReference1.update(data).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(IssueBookActivity.this, "Couldn't update the availability", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                    documentReference2.collection("records").add(studentRecord).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.d("TAG", "onSuccess: " + "record added to the student");
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            } else {
                                Snackbar.make(issue, "USN has not been registered", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

    }
}
