package com.example.rahmanm2.myapplication.App;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.rahmanm2.myapplication.Adapter.ShowmenuListAdapter;
import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.DataBase.addFavorite;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.IngrediansModel.FavoriteModel;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.IngrediansModel.getDataFromFirebase;
import com.example.rahmanm2.myapplication.IngrediansModel.getIngredians;
import com.example.rahmanm2.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class ShowListItemsActivity extends AppCompatActivity {

    List<HorizontalViewIngredians>itemList;
    RecyclerView mRecyclerView;
    GridLayoutManager manager1;
    addFavorite localDb;
    String menuID = "";
    ShowmenuListAdapter adapter;
    ShowmenuListAdapter SearchAdapter;
    private MaterialSearchBar MenuItemSearchBarID;
    private getDataFromFirebase mGetDataFromFirebase;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private List<String>SuggestedSearch;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_items);
        //init database
        localDb = new addFavorite(this);
        //init firebase
        FirebaseUIConnector.DatabaseConnection("Recipes");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;
        //end of init firebase
        mGetDataFromFirebase = new getIngredians();
        mGetDataFromFirebase.connector("Recipes");
        itemList = new ArrayList<>();
        MenuItemSearchBarID = (MaterialSearchBar)findViewById(R.id.ListMenuSearchBarID);

        //was here
        final Intent pdIntent = getIntent();
        HorizontalViewIngredians menuDetails = (HorizontalViewIngredians) pdIntent.getSerializableExtra("itemModel");
        if(menuDetails == null){
            menuDetails = new HorizontalViewIngredians();
        }
        menuID = menuDetails.getItemID();
        itemList.add(menuDetails);
        showlistItems(menuID);
        //swipe refresh here
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshListItemID);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Intent pdIntent = getIntent();
                HorizontalViewIngredians menuDetails = (HorizontalViewIngredians) pdIntent.getSerializableExtra("itemModel");
                if(menuDetails == null){
                    menuDetails = new HorizontalViewIngredians();
                }
                menuID = menuDetails.getItemID();
                itemList.add(menuDetails);
                showlistItems(menuID);
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                final Intent pdIntent = getIntent();
                HorizontalViewIngredians menuDetails = (HorizontalViewIngredians) pdIntent.getSerializableExtra("itemModel");
                if(menuDetails == null){
                    menuDetails = new HorizontalViewIngredians();
                }
                menuID = menuDetails.getItemID();
                itemList.add(menuDetails);
                showlistItems(menuID);
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.show_menu_itemRecyclerID);
        mRecyclerView.setHasFixedSize(true);
        manager1 = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(manager1);

        //add animation on recycler view
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(mRecyclerView.getContext(),
                R.anim.layout_fall_down);
        mRecyclerView.setLayoutAnimation(controller);

        SuggestedSearch = new ArrayList<>();
        MenuItemSearchBarID.setHint("Please Enter name to Search Recipe");
        //load suggested Search
        SuggestedSearch = mGetDataFromFirebase.LoadSuggest(menuID);
        MenuItemSearchBarID.setLastSuggestions(SuggestedSearch);
        MenuItemSearchBarID.setCardViewElevation(10);

        onsearchClick();
        //end of search

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void onsearchClick(){
        MenuItemSearchBarID.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String>currentSuggest = new ArrayList<>();
                for (String search : SuggestedSearch){
                    if(search.toLowerCase().contains(MenuItemSearchBarID.getText().toLowerCase())){
                        currentSuggest.add(search);
                    }
                }
                MenuItemSearchBarID.setLastSuggestions(currentSuggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        MenuItemSearchBarID.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is closed
                //restore orgnal adapter
                if(!enabled)
                    mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                //when search finished show result of search adapter
                StartSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }
    public void StartSearch(CharSequence text) {
        final List<HorizontalViewIngredians>items = new ArrayList<>();
        //name is the foods name
        mDatabaseReference.orderByChild("title").equalTo(text.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    HorizontalViewIngredians itms = data.getValue(HorizontalViewIngredians.class);
                    items.add(itms);
                }

                SearchAdapter = new ShowmenuListAdapter(items,ShowListItemsActivity.this,localDb);
                //SearchAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(SearchAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void showlistItems(String listitemID){
        final List<HorizontalViewIngredians>mListViewMenuItems = new ArrayList<>();
        mDatabaseReference.orderByChild("itemID").equalTo(listitemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    HorizontalViewIngredians itms = data.getValue(HorizontalViewIngredians.class);
                    String keys = data.getKey();
                    itms.setItemsKeys(keys);
                    mListViewMenuItems.add(itms);
                }
                adapter = new ShowmenuListAdapter(mListViewMenuItems,ShowListItemsActivity.this,localDb);
                mRecyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);

                //animation
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mRecyclerView.scheduleLayoutAnimation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
