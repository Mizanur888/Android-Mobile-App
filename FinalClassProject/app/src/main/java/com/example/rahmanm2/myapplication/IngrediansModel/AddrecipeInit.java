package com.example.rahmanm2.myapplication.IngrediansModel;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmanm2.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddrecipeInit {
    private Spinner mFoodcatagory;
    private Context mContext;
    private View mView;

    public AddrecipeInit( Context context, View view,Spinner spinner){
        this.mContext = context;
        this.mView = view;
        this.mFoodcatagory = spinner;
    }

    private void setupSpinnerCatagory(){
        mFoodcatagory = (Spinner)mView.findViewById(R.id.recipe_forCatagory);
        String[]catagory_for = {"Breakfast","Lunch","Dinner","Other"};
        List<String> catagory = new ArrayList<>(Arrays.asList(catagory_for));
        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,catagory){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position,convertView,parent);
                TextView tv = (TextView) view;
                if(position%2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#4CAF50"));
                }
                else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#69F0AE"));
                }
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mFoodcatagory.setAdapter(arrayAdapter);
        mFoodcatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                String selectedItem = (String)adapterView.getItemAtPosition(postion);
                Toast.makeText(view.getContext(),"Selected: "+selectedItem,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
