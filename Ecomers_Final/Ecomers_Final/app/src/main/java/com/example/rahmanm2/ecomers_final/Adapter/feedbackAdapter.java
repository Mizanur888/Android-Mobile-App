package com.example.rahmanm2.ecomers_final.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rahmanm2.ecomers_final.R;
import com.example.rahmanm2.ecomers_final.SampleDataModel.FeedbackModel;

import java.util.List;

public class feedbackAdapter extends RecyclerView.Adapter<feedbackAdapter.feedbackViewHolder>{

    List<FeedbackModel>edModel;
    LayoutInflater mLayoutInflater;
    public feedbackAdapter(List<FeedbackModel>model,Context context){
        this.edModel = model;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public feedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.feedback_layout,parent,false);
        feedbackViewHolder holder = new feedbackViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull feedbackViewHolder holder, int position) {
        FeedbackModel model = edModel.get(position);
        holder.edtFeedback.setText(model.getFeedback().toString());
    }

    @Override
    public int getItemCount() {
            return edModel.size();
    }

    public class feedbackViewHolder extends RecyclerView.ViewHolder {
        TextView edtFeedback;

        public feedbackViewHolder(View itemView) {
            super(itemView);
            edtFeedback = (TextView) itemView.findViewById(R.id.productFeedbackID);
        }
    }
}
