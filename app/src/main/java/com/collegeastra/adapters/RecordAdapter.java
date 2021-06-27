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
import com.collegeastra.models.Record;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordAdapter extends FirestoreRecyclerAdapter<Record, RecordAdapter.RecordViewHolder> {
    Context context;
    public RecordAdapter(Context context, FirestoreRecyclerOptions<Record> query){
        super(query);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RecordAdapter.RecordViewHolder holder, int position, @NonNull Record model) {
        Timestamp issue = model.getIssueDate();
        Timestamp returnDate = model.getReturnDate();
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy");
        String issuedate = formatter.format(new Date(issue.getSeconds() * 1000L));
        String returndate = formatter.format(new Date(returnDate.getSeconds() * 1000L));
        holder.tv_copyid.setText(model.getCopyId());
        holder.tv_issuedate.setText(issuedate);
        holder.tv_returndate.setText(returndate);
    }

    @NonNull
    @Override
    public RecordAdapter.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_container,parent,false);
        return new RecordViewHolder(view);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder{
        TextView tv_copyid,tv_issuedate,tv_returndate;
        public RecordViewHolder(View itemVIew){
            super(itemVIew);
            tv_copyid = itemVIew.findViewById(R.id.tv_copyid);
            tv_issuedate = itemVIew.findViewById(R.id.tv_issuedate);
            tv_returndate = itemVIew.findViewById(R.id.tv_returndate);
        }
    }
}
