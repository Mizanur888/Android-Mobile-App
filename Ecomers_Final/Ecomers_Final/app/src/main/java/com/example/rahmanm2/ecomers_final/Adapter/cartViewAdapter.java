package com.example.rahmanm2.ecomers_final.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.rahmanm2.ecomers_final.R;
import com.example.rahmanm2.ecomers_final.SampleDataModel.OrderModel;
import com.google.firebase.database.collection.LLRBNode;

import org.w3c.dom.Text;

import java.util.List;

public class cartViewAdapter extends RecyclerView.Adapter<cartViewAdapter.cartViewHolder>{

    List<OrderModel>mOrderModelList;
    LayoutInflater mLayoutInflater;
    public cartViewAdapter(List<OrderModel>model,Context ctx){
        mOrderModelList = model;
        mLayoutInflater = LayoutInflater.from(ctx);
    }
    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.cart_view_layout,parent,false);
        cartViewHolder holder = new cartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull cartViewHolder holder, int position) {
        OrderModel currentorder = mOrderModelList.get(position);
        holder.setData(currentorder);
    }

    @Override
    public int getItemCount() {
        return mOrderModelList.size();
    }

    public class cartViewHolder extends RecyclerView.ViewHolder{

       private TextView cartItemName, cartItemPrice;
       private ImageView cartItemCount;

        //TODO-implements Cart View Holder same as cart layout
        public cartViewHolder(View itemView) {
            super(itemView);
            cartItemName = (TextView)itemView.findViewById(R.id.cartItemName);
            cartItemPrice = (TextView)itemView.findViewById(R.id.cartItemPrice);
            cartItemCount = (ImageView) itemView.findViewById(R.id.cartItemCount);
        }
        public void setData(OrderModel orderModel){
            cartItemName.setText(orderModel.getProductName());
            cartItemPrice.setText(orderModel.getProductprice());
        }
    }
}
