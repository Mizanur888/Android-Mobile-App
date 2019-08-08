package com.example.rahmanm2.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.example.rahmanm2.myapplication.R;

import java.util.List;

public class checkTextViewAdapter extends BaseAdapter {
    List<String>mList;
    Context mContext;
    LayoutInflater mLayoutInflater;
    public checkTextViewAdapter(Context context, List<String>list){
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    public class Holder{
        CheckedTextView mCheckedTextView;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        if(view==null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.sample_layout_addrecipe, viewGroup, false);
            holder.mCheckedTextView = (CheckedTextView)view.findViewById(R.id.cTextView);
            view.setTag(holder);
        }
        else{
            holder = (Holder)view.getTag();
        }
        holder.mCheckedTextView.setText(mList.get(i));
        final Holder findViewHolder = holder;
        holder.mCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewHolder.mCheckedTextView.isChecked()){
                    findViewHolder.mCheckedTextView.setCheckMarkDrawable(0);
                    findViewHolder.mCheckedTextView.setChecked(false);
                }
                else{
                    findViewHolder.mCheckedTextView.setCheckMarkDrawable(R.drawable.checked_mark);
                    findViewHolder.mCheckedTextView.setChecked(true);
                }
            }
        });
        return view;
    }
}
