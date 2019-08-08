package com.example.rahmanm2.myapplication.App.FragmentModel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.rahmanm2.myapplication.Adapter.breakFastAdapter;
import com.example.rahmanm2.myapplication.Adapter.dinnerAdapter;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DinnerFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private dinnerAdapter Adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dinner_fragment,container,false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.dinnerRecyclerID);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUIConnector.DatabaseConnection("Recipes");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;
        getBreakFast();
    }
    private List<HorizontalViewIngredians> getBreakFast(){
        final List<HorizontalViewIngredians>listItems = new ArrayList<>();
        mDatabaseReference.orderByChild("recipeCatagory").equalTo("Dinner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    HorizontalViewIngredians ingredians = data.getValue(HorizontalViewIngredians.class);
                    listItems.add(ingredians);

                }
                Adapter = new dinnerAdapter(getContext(),listItems);
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(mRecyclerView.getContext(),
                        R.anim.layout_fall_down);
                mRecyclerView.setLayoutAnimation(controller);
                mRecyclerView.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return listItems;
    }
}
