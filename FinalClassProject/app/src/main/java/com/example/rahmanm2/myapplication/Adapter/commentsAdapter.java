package com.example.rahmanm2.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rahmanm2.myapplication.IngrediansModel.comments;
import com.example.rahmanm2.myapplication.R;

import java.util.List;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.viewHolder> {

    List<comments>mCommentsList;
    Context mContext;

    public commentsAdapter(Context context,List<comments>list){
        this.mContext = context;
        this.mCommentsList = list;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_comments,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        comments current = mCommentsList.get(position);
        holder.setupData(current);
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView usedName, dateTextView,commentsID;
        RatingBar RATINGinitialvalueratingID;

        public viewHolder(View itemView) {
            super(itemView);
            usedName = (TextView)itemView.findViewById(R.id.usedName);
            dateTextView = (TextView)itemView.findViewById(R.id.dateTextView);
            commentsID = (TextView)itemView.findViewById(R.id.commentsID);
            RATINGinitialvalueratingID = (RatingBar)itemView.findViewById(R.id.RATINGinitialvalueratingID);
        }
        public void setupData(comments model){
            usedName.setText(model.getUserID());
            dateTextView.setText(model.getDate());
            commentsID.setText(model.getUserComments());
            RATINGinitialvalueratingID.setRating(model.getUserRating());
        }
    }
}
