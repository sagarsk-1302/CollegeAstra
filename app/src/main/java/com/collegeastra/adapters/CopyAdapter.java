package com.collegeastra.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeastra.R;
import com.collegeastra.activities.IssueBookActivity;
import com.collegeastra.activities.RecordActivity;
import com.collegeastra.models.Copy;
import com.collegeastra.models.Record;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;
import static com.collegeastra.utils.Constants.APPNAME;

public class CopyAdapter extends FirestoreRecyclerAdapter<Copy, CopyAdapter.CopyViewHolder> {
    Boolean user;
    Context context;
    Record record;
    String bookid;
    Boolean avail;
    FirebaseFirestore firebaseFirestore;

    public CopyAdapter(Context context, FirestoreRecyclerOptions<Copy> query, Boolean user, String bookid) {
        super(query);
        this.context = context;
        this.user = user;
        this.bookid = bookid;
    }

    @Override
    protected void onBindViewHolder(@NonNull CopyViewHolder holder, int position, @NonNull Copy model) {
        holder.copyid.setText(model.getCopyId());
        firebaseFirestore = FirebaseFirestore.getInstance();
        if (user == true) {
            if (!model.getAvailable()) {
                holder.btn_valiate.setVisibility(View.VISIBLE);
                holder.btn_valiate.setText("ACCEPT");
                holder.btn_valiate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO update database with the returned date
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setTitle("Alert");
                        alertBuilder.setMessage("Is the student returning the book?");
                        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Calendar myCalendar = Calendar.getInstance();
                                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                          int dayOfMonth) {
                                        myCalendar.set(Calendar.YEAR, year);
                                        myCalendar.set(Calendar.MONTH, monthOfYear);
                                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        Date returnDate = new Date(year-1900, monthOfYear, dayOfMonth);
                                        Timestamp returnedDate = new Timestamp(returnDate);
                                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                        DocumentReference documentReference = firebaseFirestore.collection("books").document(model.getBookId())
                                                .collection("copy").document(model.getCopyId()).collection("records").document(model.getIssuedId());
                                        documentReference.update("returnDate",returnedDate).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        DocumentReference documentReference1 = firebaseFirestore.collection("books").document(model.getBookId())
                                                .collection("copy").document(model.getCopyId());
                                        documentReference1.update("available",true).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    if(documentSnapshot!=null && documentSnapshot.exists()){
                                                        String usn = documentSnapshot.getString("usn");
                                                        HashMap<String,Object> map = new HashMap<>();
                                                        map.put("returnedOn",returnedDate);
                                                        map.put("returned",true);
                                                        firebaseFirestore.collection("student").document(usn).collection("records")
                                                                .document(model.getIssuedId()).update(map).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                holder.btn_valiate.setText("ISSUE");
                                                                model.setAvailable(true);
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });

                                    }
                                };
                                new DatePickerDialog(context, date, myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                            }
                        }).setNegativeButton("No", null);
                        alertBuilder.show();

                    }
                });
                holder.copyid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = RecordActivity.recordStart(context,model.getCopyId(),model.getBookId());
                        context.startActivity(intent);
                    }
                });
            } else {
                holder.btn_valiate.setText("ISSUE");
                holder.btn_valiate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO update the copy record that its not available and add a copy record to the student id
                        Intent intent = new Intent(context, IssueBookActivity.class);
                        intent.putExtra("copyId", model.getCopyId());
                        intent.putExtra("bookId", model.getBookId());
                        context.startActivity(intent);

                    }
                });
            }
        } else {
            holder.btn_valiate.setVisibility(View.GONE);
            if (model.getAvailable()) {
                holder.tv_return.setText("Available");
                holder.tv_return.setVisibility(View.VISIBLE);
            } else {
                DocumentReference documentReference = firebaseFirestore.collection("books").document(model.getBookId());
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            String issuedDocId = documentSnapshot.getString("issuedId");
                            documentReference.collection("records").document(issuedDocId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        DocumentSnapshot documentSnapshot1 = task.getResult();
                                        Timestamp timestamp = documentSnapshot1.getTimestamp("returnDate");
                                        Date date = new Date(timestamp.getSeconds());
                                        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy");
                                        String dateString = formatter.format(new Date(timestamp.getSeconds() * 1000L));
                                        holder.tv_return.setText(dateString);
                                        holder.tv_return.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }

    }

    @NonNull
    @Override
    public CopyAdapter.CopyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.copy_container, parent, false);
        return new CopyViewHolder(view);
    }

    public class CopyViewHolder extends RecyclerView.ViewHolder {
        TextView copyid, tv_return;
        Button btn_valiate;


        public CopyViewHolder(View itemview) {
            super(itemview);
            copyid = (TextView) itemview.findViewById(R.id.tv_copyid);
            btn_valiate = (Button) itemview.findViewById(R.id.btn_validate);
            tv_return = (TextView) itemview.findViewById(R.id.tv_date);
        }
    }
}
