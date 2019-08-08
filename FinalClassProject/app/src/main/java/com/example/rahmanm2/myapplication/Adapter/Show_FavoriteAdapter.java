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
import android.widget.Toast;

import com.example.rahmanm2.myapplication.App.LoginActivity;
import com.example.rahmanm2.myapplication.App.ShowRecipeDetailsActivity;
import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.DataBase.addFavorite;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Show_FavoriteAdapter extends RecyclerView.Adapter<Show_FavoriteAdapter.viewHolder> {
    public List<HorizontalViewIngredians>mIngredians;
    Context mContext;
    LayoutInflater mLayoutInflater;
    addFavorite mLocalDB;
    public Show_FavoriteAdapter(Context context, List<HorizontalViewIngredians>list,addFavorite localDB){
        this.mContext = context;
        this.mIngredians = list;
        mLocalDB = localDB;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_recipe_list,parent,false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        HorizontalViewIngredians ingredians =mIngredians.get(position);
        holder.setData(ingredians,position);

        if(LoginActivity.IsLogedin ==true) {
            if (mLocalDB.isFavorite(currentuser.currentUser, mIngredians.get(position).getItemsKeys()))
                holder.fav.setImageResource(R.drawable.ic_favorite_black);
        }
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(holder,position);
            }
        });
    }

    public void removeItem(viewHolder holder, int position){
        HorizontalViewIngredians ingredians =mIngredians.get(position);
        if(mLocalDB.isFavorite(currentuser.currentUser,mIngredians.get(position).getItemsKeys())){
            mLocalDB.RemoveFavorite(currentuser.currentUser,mIngredians.get(position).getItemsKeys());
            holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            Toast.makeText(mContext," "+ingredians.getTitle()+" Was removed from Favorite "+mIngredians.get(position).getItemsKeys(),Toast.LENGTH_LONG).show();
            mIngredians.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mIngredians.size());
        }
    }
    @Override
    public int getItemCount() {
        return mIngredians.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView menu_image_itemID, fav;
        TextView menu_item_name, timetomakeFoodsID;
        int postion;
        HorizontalViewIngredians current;

        public viewHolder(View itemView) {
            super(itemView);
            menu_item_name = (TextView) itemView.findViewById(R.id.menu_list_item_name);
            timetomakeFoodsID = (TextView) itemView.findViewById(R.id.timetomakeFoodsID);
            menu_image_itemID = (ImageView) itemView.findViewById(R.id.menu_list_image_itemID);
            fav = (ImageView) itemView.findViewById(R.id.fav);
            itemView.setOnClickListener(this);
        }
        public void setData(HorizontalViewIngredians ingredians, int postion){
            this.menu_item_name.setText(ingredians.getTitle());
            this.timetomakeFoodsID.setText(ingredians.getTimetoMakeFoods());
            //this.menu_image_itemID.setImageResource(ingredians.getInt_Image());
            //TODO add picasso
            Picasso.with(mContext).load(ingredians.getImage()).into(menu_image_itemID);
            this.postion = postion;
            this.current = ingredians;
        }
        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            HorizontalViewIngredians itempostion = mIngredians.get(postion);
            // String menuID = itempostion.getItemID();
            //Log.d("Item","Item Postion"+postion);
            //Log.d("Item","Item Postion"+itempostion);
            Intent sendData = new Intent(view.getContext(), ShowRecipeDetailsActivity.class);
            sendData.putExtra("menuid",itempostion);
            mContext.startActivity(sendData);

        }
    }
}
