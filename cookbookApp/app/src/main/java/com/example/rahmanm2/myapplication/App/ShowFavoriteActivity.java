package com.example.rahmanm2.myapplication.App;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.rahmanm2.myapplication.Adapter.Show_FavoriteAdapter;
import com.example.rahmanm2.myapplication.Adapter.ShowmenuListAdapter;
import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.DataBase.addFavorite;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.IngrediansModel.FavoriteModel;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.IngrediansModel.initmainView;
import com.example.rahmanm2.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowFavoriteActivity extends AppCompatActivity {

   public List<HorizontalViewIngredians> recipes;
    RecyclerView mRecyclerView;
    Show_FavoriteAdapter adapter;
    GridLayoutManager manager1;
    addFavorite localDb;
    private Cursor mCursor;
    private String userid = "";
    private List<FavoriteModel>itemsSql;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    SwipeRefreshLayout swipperLayoutshowFavoriteID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorite);

        swipperLayoutshowFavoriteID = (SwipeRefreshLayout)findViewById(R.id.swipperLayoutshowFavoriteID);
        swipperLayoutshowFavoriteID.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipperLayoutshowFavoriteID.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        //init Firebase
        FirebaseUIConnector.DatabaseConnection("Recipes");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;
        recipes = new ArrayList<HorizontalViewIngredians>();
        localDb = new addFavorite(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.show_favoriteItems);
        mRecyclerView.setHasFixedSize(true);

        manager1 = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(manager1);
        showFavoriteList();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    private void showFavoriteList(){
        final List<HorizontalViewIngredians>mListViewMenuItems = new ArrayList<>();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    HorizontalViewIngredians itms = data.getValue(HorizontalViewIngredians.class);
                    String keys = data.getKey();
                    itms.setItemsKeys(keys);
                    mListViewMenuItems.add(itms);
                }
                recipes = matchList(mListViewMenuItems);
                adapter = new Show_FavoriteAdapter(ShowFavoriteActivity.this, recipes, localDb);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private List<HorizontalViewIngredians>matchList(List<HorizontalViewIngredians>itemsData){
        List<HorizontalViewIngredians>mid = new ArrayList<>();
        for(int kk = 0;kk<itemsData.size();kk++){

            if (localDb.isFavorite(currentuser.currentUser, itemsData.get(kk).getItemsKeys())){
                HorizontalViewIngredians items = itemsData.get(kk);
                mid.add(items);
            }
        }
        return mid;
    }
    private void getAllFavoriteData(){

        Show_FavoriteAdapter adapter = new Show_FavoriteAdapter(ShowFavoriteActivity.this, currentuser.sViewIngredians,localDb);
        mRecyclerView.setAdapter(adapter);
    }

}
