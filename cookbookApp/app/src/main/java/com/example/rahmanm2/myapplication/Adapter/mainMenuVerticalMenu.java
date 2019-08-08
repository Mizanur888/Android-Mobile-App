package com.example.rahmanm2.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahmanm2.myapplication.App.ShowListItemsActivity;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class mainMenuVerticalMenu extends RecyclerView.Adapter<mainMenuVerticalMenu.viewHolder> {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<HorizontalViewIngredians> recipes;

    public mainMenuVerticalMenu(List<HorizontalViewIngredians> model,Context contex){
        this.recipes = model;
        this.mContext = contex;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_verticalview_layout,parent,false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        HorizontalViewIngredians ingredians = recipes.get(position);
        holder.menu_item_name.setText(ingredians.getTitle());
        holder.menu_image_itemID.setImageResource(ingredians.getInt_Image());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView menu_image_itemID;
        TextView menu_item_name;

        public viewHolder(View itemView) {
            super(itemView);
            menu_image_itemID = (ImageView)itemView.findViewById(R.id.menu_image_itemID);
            menu_item_name = (TextView)itemView.findViewById(R.id.menu_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            HorizontalViewIngredians itempostion = recipes.get(postion);
            Log.d("Item","Item Postion"+postion);
            Log.d("Item","Item Postion"+itempostion);

            Intent dataTransfer = new Intent(view.getContext(), ShowListItemsActivity.class);
            dataTransfer.putExtra("itemModel",itempostion);
            view.getContext().startActivity(dataTransfer);
        }
    }
}
