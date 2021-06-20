package com.collegeastra.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.collegeastra.R;
import com.collegeastra.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddStudentActivity extends AppCompatActivity {
    Button btn_submit;
    EditText et_name, et_password, et_usn, et_email;
    ChipGroup department;
    FirebaseFirestore firebaseFirestore;
    String dep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_addstudent);
        btn_submit = (Button) findViewById(R.id.btn_addstud);
        department = (ChipGroup) findViewById(R.id.chipGroup2);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_usn = (EditText) findViewById(R.id.et_usn);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dep = "";
                String name = et_name.getText().toString().trim();
                String usn = et_usn.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (!name.isEmpty() && !usn.isEmpty() && !email.isEmpty() && !password.isEmpty() && department.getCheckedChipId() != View.NO_ID) {
                    switch (department.getCheckedChipId()) {
                        case R.id.chip_cse:
                            dep = "CSE";
                            break;
                        case R.id.chip_ise:
                            dep = "ISE";
                            break;
                        case R.id.chip_ece:
                            dep = "ECE";
                            break;
                        case R.id.chip_me:
                            dep = "ME";
                            break;
                        case R.id.chip_civ:
                            dep = "CIV";
                            break;
                        case R.id.chip_bs:
                            dep = "BS";
                            break;
                        default:
                            dep = "error";
                    }
                    DocumentReference documentReference = firebaseFirestore.collection("student").document(usn);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                    Snackbar.make(btn_submit, "Account already exists", Snackbar.LENGTH_SHORT).setAnchorView(btn_submit).show();
                                } else {
                                    Student student = new Student(usn, name, password, email, dep);
                                    firebaseFirestore.collection("student").document(usn).set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Snackbar.make(btn_submit, "The student has been added", Snackbar.LENGTH_SHORT).setAnchorView(btn_submit).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(btn_submit, "Error occured", Snackbar.LENGTH_SHORT).setAnchorView(btn_submit).show();
                                        }
                                    });
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(btn_submit, "Error occured", Snackbar.LENGTH_SHORT).setAnchorView(btn_submit).show();
                        }
                    });
                } else {
                    Snackbar.make(btn_submit, "Fill all the fields", Snackbar.LENGTH_SHORT).setAnchorView(btn_submit).show();
                }
            }

        });
    }
}
