package com.example.rahmanm2.ecomerssiteclassprojectfinal;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rahmanm2.ecomerssiteclassprojectfinal.Adapter.MainMenuAdapter;
import com.example.rahmanm2.ecomerssiteclassprojectfinal.App.MainActivity;
import com.example.rahmanm2.ecomerssiteclassprojectfinal.SampleDataModel.UploadImage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showManin_menuItem extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    public MainMenuAdapter mMainMenuAdapter;
    private DatabaseReference mDatabaseReference;
    public List<UploadImage> mUploadImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_manin_menu_item);

        mRecyclerView = (RecyclerView)findViewById(R.id.show_main_menu_itemRecyclerID);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploadImages = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ecomerssiteclassproject");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UploadImage uploadImage = dataSnapshot1.getValue(UploadImage.class);
                    mUploadImages.add(uploadImage);
                }
                mMainMenuAdapter = new MainMenuAdapter(showManin_menuItem.this,mUploadImages);
                mRecyclerView.setAdapter(mMainMenuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(showManin_menuItem.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupRecyclerView(){

    }

}
