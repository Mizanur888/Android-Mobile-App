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
import android.widget.ListView;
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

public class ShowmenuListAdapter extends RecyclerView.Adapter<ShowmenuListAdapter.viewHolder>{
    List<HorizontalViewIngredians>recipes;
    Context mContext;
    addFavorite mLocalDB;
    public ShowmenuListAdapter(List<HorizontalViewIngredians> models, Context context, addFavorite localDB ){
        this.recipes  = models;
        this.mContext = context;
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

        final HorizontalViewIngredians ingredians =recipes.get(position);
        holder.menu_item_name.setText(ingredians.getTitle());
        holder.timetomakeFoodsID.setText(ingredians.getTimetoMakeFoods());
       // holder.menu_image_itemID.setImageResource(ingredians.getInt_Image());
        Picasso.with(mContext).load(ingredians.getImage()).into(holder.menu_image_itemID);
//.getItemID()
        if(LoginActivity.IsLogedin ==true) {
            if (mLocalDB.isFavorite(currentuser.currentUser, recipes.get(position).getItemsKeys()))
                holder.fav.setImageResource(R.drawable.ic_favorite_black);
        }

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.IsLogedin == false){
                    Toast.makeText(mContext,"please Login",Toast.LENGTH_LONG).show();
                    mContext.startActivity(new Intent(mContext,LoginActivity.class));
                }
                else{
                    if(!mLocalDB.isFavorite(currentuser.currentUser,recipes.get(position).getItemsKeys())){
                        mLocalDB.AddFavorite(currentuser.currentUser,recipes.get(position).getItemsKeys());
                        holder.fav.setImageResource(R.drawable.ic_favorite_black);
                        Toast.makeText(mContext," "+ingredians.getTitle()+" Is added to Favorite ",Toast.LENGTH_LONG).show();
                    }
                    else if(mLocalDB.isFavorite(currentuser.currentUser,recipes.get(position).getItemsKeys())){
                        mLocalDB.RemoveFavorite(currentuser.currentUser,recipes.get(position).getItemsKeys());
                        holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        Toast.makeText(mContext," "+ingredians.getTitle()+" Is removed from Favorite "+recipes.get(position).getItemsKeys(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView menu_image_itemID, fav;
        TextView menu_item_name,timetomakeFoodsID;

        public viewHolder(View itemView) {
            super(itemView);
            menu_item_name = (TextView)itemView.findViewById(R.id.menu_list_item_name);
            timetomakeFoodsID = (TextView)itemView.findViewById(R.id.timetomakeFoodsID);
            menu_image_itemID = (ImageView)itemView.findViewById(R.id.menu_list_image_itemID);
            fav = (ImageView)itemView.findViewById(R.id.fav);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            HorizontalViewIngredians itempostion = recipes.get(postion);
           // String menuID = itempostion.getItemID();
            //Log.d("Item","Item Postion"+postion);
            //Log.d("Item","Item Postion"+itempostion);
            Intent sendData = new Intent(view.getContext(), ShowRecipeDetailsActivity.class);
            sendData.putExtra("menuid",itempostion);
            mContext.startActivity(sendData);
        }
    }
}
