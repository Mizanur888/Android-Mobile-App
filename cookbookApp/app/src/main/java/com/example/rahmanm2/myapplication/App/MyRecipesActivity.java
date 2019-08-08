package com.example.rahmanm2.myapplication.App;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.rahmanm2.myapplication.Adapter.ShowmenuListAdapter;
import com.example.rahmanm2.myapplication.Adapter.ShowmyRecipesAdapter;
import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class MyRecipesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    ShowmyRecipesAdapter adapter;
    ShowmyRecipesAdapter SearchAdapter;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    MaterialSearchBar myRecipeItemSearchBarID;
    List<String>SuggestedSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        //init firebase
        FirebaseUIConnector.DatabaseConnection("Recipes");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;
        SuggestedSearch = new ArrayList<>();
        myRecipeItemSearchBarID = (MaterialSearchBar)findViewById(R.id.myRecipeItemSearchBarID);
        mRecyclerView = (RecyclerView)findViewById(R.id.myrecipesRecyclerList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showlistItems();

        myRecipeItemSearchBarID.setHint("Please Enter recipe name");
        SuggestedSearch = LoadSuggest();
        myRecipeItemSearchBarID.setLastSuggestions(SuggestedSearch);
        onclickActionOnSearch();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void onclickActionOnSearch(){
        myRecipeItemSearchBarID.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String>currentSearch = new ArrayList<>();
                for(String search : SuggestedSearch){
                    if(search.toLowerCase().contains(myRecipeItemSearchBarID.getText().toLowerCase())){
                        currentSearch.add(search);
                    }

                }
                myRecipeItemSearchBarID.setLastSuggestions(currentSearch);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        myRecipeItemSearchBarID.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
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

                SearchAdapter = new ShowmyRecipesAdapter(MyRecipesActivity.this, items);
                SearchAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(SearchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public List<String> LoadSuggest() {
            final List<String>SuggestedSearch = new ArrayList<>();
            mDatabaseReference.orderByChild("userID").equalTo(currentuser.currentUser).addValueEventListener(new ValueEventListener() {
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

    private void showlistItems(){
        final List<HorizontalViewIngredians> mListViewMenuItems = new ArrayList<>();
        mDatabaseReference.orderByChild("userID").equalTo(currentuser.currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    HorizontalViewIngredians itms = data.getValue(HorizontalViewIngredians.class);
                    mListViewMenuItems.add(itms);
                }
                if(mListViewMenuItems!=null) {
                    adapter = new ShowmyRecipesAdapter(MyRecipesActivity.this, mListViewMenuItems);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
