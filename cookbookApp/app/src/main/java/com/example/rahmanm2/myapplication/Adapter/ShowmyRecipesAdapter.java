package com.example.rahmanm2.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowmyRecipesAdapter extends RecyclerView.Adapter<ShowmyRecipesAdapter.viewholder>{

    List<HorizontalViewIngredians>listofMyrecipes;
    Context mContex;

    public ShowmyRecipesAdapter(Context context, List<HorizontalViewIngredians>model){
        this.mContex = context;
        this.listofMyrecipes = model;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContex).inflate(R.layout.myrecipelayout,parent,false);
        viewholder holder = new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        HorizontalViewIngredians ingredians = listofMyrecipes.get(position);
        holder.menu_item_name.setText(ingredians.getTitle());
        holder.timetomakeFoodsID.setText(ingredians.getTimetoMakeFoods());
        Picasso.with(mContex).load(ingredians.getImage()).into(holder.menu_image_itemID);

    }

    @Override
    public int getItemCount() {
        return listofMyrecipes.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView menu_image_itemID;
        TextView menu_item_name,timetomakeFoodsID;
        public viewholder(View itemView) {
            super(itemView);

            menu_image_itemID = (ImageView)itemView.findViewById(R.id.menu_my_recipe_image_itemID);
            menu_item_name = (TextView)itemView.findViewById(R.id.my_recipe_item_name);
            timetomakeFoodsID = (TextView)itemView.findViewById(R.id.timetakeMy_recipeFoodsID);
        }

    }
}
