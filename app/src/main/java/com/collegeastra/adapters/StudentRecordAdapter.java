package com.collegeastra.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeastra.R;
import com.collegeastra.models.StudentRecord;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentRecordAdapter extends FirestoreRecyclerAdapter<StudentRecord,StudentRecordAdapter.StudentRecordViewHolder> {
    Context context;

    public StudentRecordAdapter(Context context, FirestoreRecyclerOptions<StudentRecord> query){
        super(query);
        this.context = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull StudentRecordViewHolder holder, int position, @NonNull StudentRecord model) {
        Timestamp issue = model.getBorrowedOn();
        Timestamp returnDate = model.getReturnedOn();
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy");
        String issuedate = formatter.format(new Date(issue.getSeconds() * 1000L));
        String returndate = formatter.format(new Date(returnDate.getSeconds() * 1000L));
        holder.tv_copyid.setText(model.getCopyid());
        holder.tv_issuedate.setText(issuedate);
        holder.tv_returndate.setText(returndate);
        if(model.getReturned()){
            holder.tv_hasreturned.setText("Yes");
        }else{
            holder.tv_hasreturned.setText("No");
        }
    }

    @NonNull
    @Override
    public StudentRecordAdapter.StudentRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentrecord_container,parent,false);
        return new StudentRecordViewHolder(view);
    }
    public class StudentRecordViewHolder extends RecyclerView.ViewHolder{
        TextView tv_copyid,tv_issuedate,tv_returndate,tv_hasreturned;
        public StudentRecordViewHolder(View view){
            super(view);
            tv_copyid = view.findViewById(R.id.tv_copyid);
            tv_issuedate = view.findViewById(R.id.tv_issuedate);
            tv_returndate = view.findViewById(R.id.tv_returndate);
            tv_hasreturned = view.findViewById(R.id.tv_hasreturned);
        }
    }
}
