package com.example.rahmanm2.myapplication.App.FragmentModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.rahmanm2.myapplication.Adapter.breakFastAdapter;
import com.example.rahmanm2.myapplication.App.ShowRecipeDetailsActivity;
import com.example.rahmanm2.myapplication.DataBase.addFavorite;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.IngrediansModel.Banner;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.IngrediansModel.initmainView;
import com.example.rahmanm2.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BreakFastFragment extends Fragment {
    HashMap<String,String>image_List;
    SliderLayout mSliderLayout;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    List<HorizontalViewIngredians>itemlist;
    RecyclerView mRecyclerView;
    breakFastAdapter Adapter;
    addFavorite localDb;
    GridLayoutManager manager1;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public BreakFastFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.breakfast_fragment,container,false);

         mSliderLayout = (SliderLayout)view.findViewById(R.id.sliderMenuLayout);
         mRecyclerView = (RecyclerView)view.findViewById(R.id.brekfastRecyclerID);
         mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
         mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        // mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUIConnector.DatabaseConnection("Recipes");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;
        itemlist = new ArrayList<>();
       // itemlist = initmainView.getRecipesView();
         getBreakFast();
        setupSlider(getView());
    }
    private List<HorizontalViewIngredians>getBreakFast(){
        final List<HorizontalViewIngredians>listItems = new ArrayList<>();
        mDatabaseReference.orderByChild("recipeCatagory").equalTo("Breakfast").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    HorizontalViewIngredians ingredians = data.getValue(HorizontalViewIngredians.class);
                    listItems.add(ingredians);

                }
                Adapter = new breakFastAdapter(getContext(),listItems);
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
    private void setupSlider(View view) {
        HorizontalViewIngredians mbanner;
        image_List = new HashMap<>();
       // final DatabaseReference banner = mFirebaseDatabase.getReference("banner");
        mDatabaseReference.orderByChild("recipeCatagory").equalTo("Breakfast").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){
                    HorizontalViewIngredians mbanner = postSnapshot.getValue(HorizontalViewIngredians.class);
                    itemlist.add(mbanner);
                    image_List.put(mbanner.getTitle()+"@@@"+mbanner.getItemID(),mbanner.getImage());
                }
                for(final String key:image_List.keySet()){
                    String[]keySplit = key.split("@@@");
                    String nameofFoods = keySplit[0];
                    final String idofFoods = keySplit[1];
                    //create Slider
                    final TextSliderView textSliderView = new TextSliderView(getContext());
                    textSliderView
                            .description(nameofFoods)
                            .image(image_List.get(key))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    //int id = textSliderView.getView().getId();
                                    for(int i = 0;i<itemlist.size();i++){
                                        String  current = itemlist.get(i).getItemID();
                                        if(idofFoods.equals(current)){
                                            HorizontalViewIngredians items = itemlist.get(i);
                                            Intent sendData = new Intent(getContext(), ShowRecipeDetailsActivity.class);
                                             sendData.putExtra("menuid",items);
                                            startActivity(sendData);
                                            break;
                                        }
                                    }
                                }
                            });

                    //add extra bundle
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("menuid",idofFoods);
                    mSliderLayout.addSlider(textSliderView);
                    mDatabaseReference.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
      //  mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        //  mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
       // mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        //mSliderLayout.setDuration(4000);
    }

    @Override
    public void onStop() {
        super.onStop();
          mSliderLayout.startAutoCycle();
    }

}
