package com.collegeastra.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.collegeastra.R;
import com.collegeastra.activities.BookDetailsActivity;
import com.collegeastra.models.Book;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SearchAdapter extends FirestoreRecyclerAdapter<Book,SearchAdapter.SearchViewHolder> {

    Context context;
    public  SearchAdapter(Context context, FirestoreRecyclerOptions<Book> book){
        super(book);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position, @NonNull Book model) {
        holder.bookName.setText(model.getTitle());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BookDetailsActivity.start(context,model);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_container,parent,false);
        return new SearchViewHolder(view);
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView bookName;
        ConstraintLayout constraintLayout;
        public SearchViewHolder(View itemView){
            super(itemView);
            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.const_layout);
            bookName = (TextView) itemView.findViewById(R.id.bookName);
        }

    }
}
