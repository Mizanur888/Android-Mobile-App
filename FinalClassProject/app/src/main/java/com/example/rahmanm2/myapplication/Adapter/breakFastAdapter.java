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
import android.widget.Toast;
import android.widget.Toolbar;


import com.example.rahmanm2.myapplication.App.LoginActivity;
import com.example.rahmanm2.myapplication.App.ShowRecipeDetailsActivity;
import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.DataBase.addFavorite;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class breakFastAdapter extends RecyclerView.Adapter<breakFastAdapter.viewHolder>{

    List<HorizontalViewIngredians>breakFastList;
    Context mContext;

    public breakFastAdapter(Context context, List<HorizontalViewIngredians>list){
        this.mContext = context;
        this.breakFastList = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.breakfastadapterlayout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        HorizontalViewIngredians current = breakFastList.get(position);
        holder.item_name.setText(current.getTitle());
        holder.timetomakeID.setText(current.getTimetoMakeFoods());
        //holder.image_itemID.setImageResource(current.getInt_Image());
        Picasso.with(mContext).load(current.getImage()).into(holder.image_itemID);
    }

    @Override
    public int getItemCount() {
        return breakFastList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image_itemID, fav;
        TextView item_name,timetomakeID;

        public viewHolder(View itemView) {
            super(itemView);
            item_name = (TextView)itemView.findViewById(R.id.item_name);
            timetomakeID = (TextView)itemView.findViewById(R.id.timetomakeID);
            image_itemID = (ImageView)itemView.findViewById(R.id.image_itemID);
            fav = (ImageView)itemView.findViewById(R.id.favID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            HorizontalViewIngredians itempostion = breakFastList.get(postion);
            Intent sendData = new Intent(view.getContext(), ShowRecipeDetailsActivity.class);
            sendData.putExtra("menuid",itempostion);
            mContext.startActivity(sendData);
        }
    }
}
