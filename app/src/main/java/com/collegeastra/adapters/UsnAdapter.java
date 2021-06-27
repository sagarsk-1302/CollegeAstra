package com.collegeastra.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeastra.R;
import com.collegeastra.activities.BookDetailsActivity;
import com.collegeastra.activities.RecordActivity;
import com.collegeastra.models.Student;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UsnAdapter extends FirestoreRecyclerAdapter<Student,UsnAdapter.UsnViewHolder> {

    Context context;
    public UsnAdapter(Context context, FirestoreRecyclerOptions<Student> usn){
        super(usn);
        this.context = context;
    }

    @NonNull
    @Override
    public UsnAdapter.UsnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usn_container,parent,false);
        return new UsnViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull UsnViewHolder holder, int position, @NonNull Student model) {
        ((Activity)context).findViewById(R.id.tv_noResult).setVisibility(View.GONE);
        ((Activity)context).findViewById(R.id.tv_search).setVisibility(View.VISIBLE);
        holder.tvUsn.setText(model.getUSN());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RecordActivity.studentRecordStart(context,model.getUSN());
                context.startActivity(intent);
            }
        });
    }

    public class UsnViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsn;
        ConstraintLayout constraintLayout;
        public UsnViewHolder(View itemView){
            super(itemView);
            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.const_layout);
            tvUsn = (TextView) itemView.findViewById(R.id.tv_usn);
        }
    }
}
