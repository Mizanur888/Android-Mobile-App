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

import java.util.List;

public class launchAdapter extends RecyclerView.Adapter<launchAdapter.viewHolder> {

    Context mContext;
    List<HorizontalViewIngredians> Launchlist;

    public launchAdapter(Context context, List<HorizontalViewIngredians> model){
        this.mContext = context;
        this.Launchlist = model;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.launch_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        HorizontalViewIngredians ingredians = Launchlist.get(position);
        holder.setUpLaunchHolder(ingredians);
    }

    @Override
    public int getItemCount() {
        return Launchlist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView launch_ImageID,launch_favID;
        TextView launch_timetomakeID,launch_item_name;
        
        public viewHolder(View itemView) {
            super(itemView);

            launch_ImageID = (ImageView)itemView.findViewById(R.id.launch_ImageID);
            launch_favID = (ImageView)itemView.findViewById(R.id.launch_favID);
            launch_item_name = (TextView)itemView.findViewById(R.id.launch_item_name);
            launch_timetomakeID = (TextView)itemView.findViewById(R.id.launch_timetomakeID);
            itemView.setOnClickListener(this);
        }

        private void setUpLaunchHolder(HorizontalViewIngredians model){
            Picasso.with(mContext).load(model.getImage()).into(launch_ImageID);
            launch_item_name.setText(model.getTitle());
            launch_timetomakeID.setText(model.getTimetoMakeFoods());
        }

        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            HorizontalViewIngredians itempostion = Launchlist.get(postion);
            Intent sendData = new Intent(view.getContext(), ShowRecipeDetailsActivity.class);
            sendData.putExtra("menuid",itempostion);
            mContext.startActivity(sendData);
        }
    }
}
