package com.example.rahmanm2.myapplication.IngrediansModel;

import android.support.annotation.NonNull;

import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class getIngredians implements getDataFromFirebase{
    public static List<HorizontalViewIngredians>GetIngredians;

    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mDatabaseReference;
    @Override
    public void connector(String url){
        GetIngredians = new ArrayList<>();
        FirebaseUIConnector.DatabaseConnection(url);
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;
    }

    @Override
    public List<HorizontalViewIngredians> getAllIngredians() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    HorizontalViewIngredians dataImage = data.getValue(HorizontalViewIngredians.class);
                    GetIngredians.add(dataImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return GetIngredians;
    }

    @Override
    public List<String> LoadSuggest(String listitemID) {
         final List<String>SuggestedSearch = new ArrayList<>();
        mDatabaseReference.orderByChild("itemID").equalTo(listitemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    HorizontalViewIngredians ingredians = data.getValue(HorizontalViewIngredians.class);
                    SuggestedSearch.add(ingredians.getTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return SuggestedSearch;
    }

    @Override
    public List<HorizontalViewIngredians> LoadProductList(String listitemID) {
        final List<HorizontalViewIngredians>mListViewMenuItems = new ArrayList<>();
        mDatabaseReference.orderByChild("itemID").equalTo(listitemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    HorizontalViewIngredians itms = data.getValue(HorizontalViewIngredians.class);
                    mListViewMenuItems.add(itms);
                }
                //adapter = new ListMenuAdapter(ListMenuActivity.this, mListViewMenuItems);
                //mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return mListViewMenuItems;
    }

    @Override
    public List<HorizontalViewIngredians> StartSearch(CharSequence text) {
        final List<HorizontalViewIngredians>items = new ArrayList<>();
        //name is the foods name
        mDatabaseReference.orderByChild("Name").equalTo(text.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    HorizontalViewIngredians itms = data.getValue(HorizontalViewIngredians.class);
                    items.add(itms);
                }

               // searchAdapter = new ListMenuAdapter(getApplicationContext(), items);
                //mRecyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return items;
    }
}
