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
import com.collegeastra.models.Copy;
import com.collegeastra.models.Record;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;

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
                                        Date returnDate = new Date(year, monthOfYear, dayOfMonth);
                                        Timestamp returnedDate = new Timestamp(returnDate);
                                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//                                    DocumentReference documentReference = firebaseFirestore.collection("books").document(model.getBookId())
//                                            .collection("copy").document(model.getCopyId()).collection("records")
//                                            .orderBy()

                                    }
                                };
                                new DatePickerDialog(context, date, myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                            }
                        }).setNegativeButton("No", null);

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
            if (model.getAvailable()) {
                holder.btn_valiate.setText(record.getReturnDate().toString());
                holder.btn_valiate.setVisibility(View.VISIBLE);
            } else {
                 DocumentReference documentReference = firebaseFirestore.collection("books").document(model.getBookId())
                         .collection("copy").document(model.getCopyId());
                 record.getReturnDate();



//
                holder.btn_valiate.setText(record.getReturnDate().toString());
                holder.btn_valiate.setVisibility(View.VISIBLE);

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
        TextView copyid;
        Button btn_valiate;

        public CopyViewHolder(View itemview) {
            super(itemview);
            copyid = (TextView) itemview.findViewById(R.id.tv_copyid);
            btn_valiate = (Button) itemview.findViewById(R.id.btn_validate);
        }
    }
}
