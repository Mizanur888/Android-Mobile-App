package com.example.rahmanm2.myapplication.App;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.rahmanm2.myapplication.R;

public class AddRecipesActivity extends AppCompatActivity {

    private AppCompatSpinner mSpinner;
    private android.support.v7.widget.Toolbar mToolbar;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipes);

        if(getIntent()!=null){
            userID = getIntent().getStringExtra("UserId");
        }
        if(!userID.isEmpty() && userID!=null){
        }

        mSpinner = (AppCompatSpinner)findViewById(R.id.foodType);
        String[] bankNames={"Spicy","Sweet","Savory",userID};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankNames);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);


    }
    private void setupToolbar(){
        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.addrecipesToolbarID);
        mToolbar.setTitle("Recipes");
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
