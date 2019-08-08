package com.example.rahmanm2.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahmanm2.myapplication.App.ShowRecipeDetailsActivity;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class dinnerAdapter extends RecyclerView.Adapter<dinnerAdapter.viewHolder>{
    Context mContext;
    List<HorizontalViewIngredians> dinnerlist;

    public dinnerAdapter(Context context, List<HorizontalViewIngredians> model){
        this.mContext = context;
        this.dinnerlist = model;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.dinner_layout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        HorizontalViewIngredians currentIndex = dinnerlist.get(position);
        holder.setUpDinnerHolder(currentIndex);
    }

    @Override
    public int getItemCount() {
        return dinnerlist.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView dinnerImageID,dinner_favID;
        TextView dinner_timetomakeID,dinner_item_name;
        public viewHolder(View itemView) {
            super(itemView);

            dinnerImageID = (ImageView)itemView.findViewById(R.id.dinnerImageID);
            dinner_favID = (ImageView)itemView.findViewById(R.id.dinner_favID);
            dinner_item_name = (TextView)itemView.findViewById(R.id.dinner_item_name);
            dinner_timetomakeID = (TextView)itemView.findViewById(R.id.dinner_timetomakeID);
            itemView.setOnClickListener(this);
        }

        private void setUpDinnerHolder(HorizontalViewIngredians model){
            Picasso.with(mContext).load(model.getImage()).into(dinnerImageID);
            dinner_item_name.setText(model.getTitle());
            dinner_timetomakeID.setText(model.getTimetoMakeFoods());
        }

        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            HorizontalViewIngredians itempostion = dinnerlist.get(postion);
            Intent sendData = new Intent(view.getContext(), ShowRecipeDetailsActivity.class);
            sendData.putExtra("menuid",itempostion);
            mContext.startActivity(sendData);
        }
    }
}
