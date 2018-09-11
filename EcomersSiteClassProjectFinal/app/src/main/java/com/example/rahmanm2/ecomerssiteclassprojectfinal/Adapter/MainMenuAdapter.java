package com.example.rahmanm2.ecomerssiteclassprojectfinal.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahmanm2.ecomerssiteclassprojectfinal.R;
import com.example.rahmanm2.ecomerssiteclassprojectfinal.SampleDataModel.UploadImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.AuctionViewHolder>{

    private Context mContext;
    private List<UploadImage> mUploadImage;
    private LayoutInflater mLayoutInflater;

    public MainMenuAdapter(Context context, List<UploadImage>image){
        mUploadImage = image;
        mContext = context;
    }
    @NonNull
    @Override
    public AuctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_menu_layout,parent,false);
        AuctionViewHolder viewHolder = new AuctionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionViewHolder holder, int position) {
        UploadImage currentItem =mUploadImage.get(position);
        holder.descreptionText.setText(currentItem.getName());
        Picasso.with(mContext).load(currentItem.getImaheUri())
                .fit()
                .centerCrop()
                .into(holder.menuItemImage);
    }

    @Override
    public int getItemCount() {
        return mUploadImage.size();
    }

    public class AuctionViewHolder extends RecyclerView.ViewHolder {
        ImageView menuItemImage;
        TextView descreptionText;

        public AuctionViewHolder(View itemView) {
            super(itemView);
            descreptionText = (TextView)itemView.findViewById(R.id.diff_Layout_TextID);
            menuItemImage = (ImageView)itemView.findViewById(R.id.diff_layout_ImageID);
        }
    }
}
