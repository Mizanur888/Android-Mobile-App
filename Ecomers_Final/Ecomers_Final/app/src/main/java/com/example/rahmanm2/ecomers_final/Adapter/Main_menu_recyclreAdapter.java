package com.example.rahmanm2.ecomers_final.Adapter;

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

import com.example.rahmanm2.ecomers_final.App.productdetailsActivit;
import com.example.rahmanm2.ecomers_final.R;
import com.example.rahmanm2.ecomers_final.SampleDataModel.MainMenuItem;
import com.example.rahmanm2.ecomers_final.config;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Main_menu_recyclreAdapter extends RecyclerView.Adapter<Main_menu_recyclreAdapter.viewHolder> {

    private Context mContex;
    private LayoutInflater mLayoutInflater;
    private List<MainMenuItem>mMainMenuItems;

    public Main_menu_recyclreAdapter(Context ctx, List<MainMenuItem>menuItems){
        this.mContex = ctx;
        this.mMainMenuItems = menuItems;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContex).inflate(R.layout.main_menu_items,parent,false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MainMenuItem currentItems = mMainMenuItems.get(position);
        holder.MenuImage.setImageResource(currentItems.getImages());
        holder.menuProductName.setText(currentItems.getProductName());
        holder.menu_item_descrip.setText(currentItems.getProductDescription());
        holder.menu_item_Price.setText("$"+currentItems.getProductPrice());

    }

    @Override
    public int getItemCount() {
        return mMainMenuItems.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView MenuImage;
        TextView menuProductName;
        TextView menu_item_descrip;
        TextView menu_item_Price;

        public viewHolder(View itemView) {
            super(itemView);
            MenuImage = (ImageView)itemView.findViewById(R.id.menu_image_itemID);
            menuProductName = (TextView)itemView.findViewById(R.id.menu_item_name);
            menu_item_descrip = (TextView)itemView.findViewById(R.id.menu_item_descripID);
            menu_item_Price = (TextView)itemView.findViewById(R.id.menu_item_PriceID);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            Log.d("item","itemClicked"+postion);

            MainMenuItem itemModel = mMainMenuItems.get(postion);
            Intent dataTransferIntent = new Intent(view.getContext(), productdetailsActivit.class);
            dataTransferIntent.putExtra(config.PRODUCT_DETAILS,itemModel);
            view.getContext().startActivity(dataTransferIntent);
        }
    }
}
