package com.example.rahmanm2.myapplication.App;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.comexamplerahmanm2myapplicationappdatamodel_libary.SignUp;
import com.example.rahmanm2.myapplication.Adapter.Show_FavoriteAdapter;
import com.example.rahmanm2.myapplication.Adapter.commentsAdapter;
import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.HelperModel.Validation;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.IngrediansModel.comments;
import com.example.rahmanm2.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class ShowRecipeDetailsActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar mToolbar;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TextView usedName,cooktimeID, txtRecipeBy,txtDescription, txtShowIngredians,txtShowDirection;
    Button btnSubmit;
    EditText userCommmentsID;
    ImageView menuDetailsFoodImage, imgRecipeByImage;
    RatingBar RatinginitialvalueratingID;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    float mRating = 0;
    List<comments> mCommentsList;
    comments model;
    String menuid = "";
    String userName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe_details);
        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbatDeatilsRecipe);
        mToolbar.setTitle("Details Recipes");
        mToolbar.inflateMenu(R.menu.menu_bottom);

        //get intens data
        final Intent pdIntent = getIntent();
        HorizontalViewIngredians menuDetails = (HorizontalViewIngredians) pdIntent.getSerializableExtra("menuid");
        if(menuDetails == null){
            menuDetails = new HorizontalViewIngredians();
        }
        menuid = menuDetails.getItemsKeys();
        userName = menuDetails.getUserID();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
        mCollapsingToolbarLayout.setTitle(menuDetails.getTitle());
        mCommentsList = new ArrayList<>();
        //init firebase database

        //get menuid from intent
        /*if(getIntent()!=null){
            menuid = getIntent().getStringExtra("menuid");
        }
        if(!menuid.isEmpty() && menuid!=null){
          //  loadProdcutList(listItemID);
        }*/

        //recipe by init
        txtRecipeBy = (TextView)findViewById(R.id.txtRecipeBy);
        txtDescription = (TextView)findViewById(R.id.txtDescription);
        txtShowIngredians = (TextView)findViewById(R.id.txtShowIngredians);
        txtShowDirection = (TextView)findViewById(R.id.txtShowDirection);

        imgRecipeByImage = (ImageView)findViewById(R.id.imgRecipeByImage);

        usedName = (TextView)findViewById(R.id.usedName);
        userCommmentsID = (EditText)findViewById(R.id.userCommmentsID);
        RatinginitialvalueratingID = (RatingBar)findViewById(R.id.RatinginitialvalueratingID);
        menuDetailsFoodImage = (ImageView)findViewById(R.id.menuDetailsFoodImage);
        Picasso.with(this).load(menuDetails.getImage()).into(menuDetailsFoodImage);

        //menuDetailsFoodImage.setImageResource(menuDetails.getInt_Image());
        txtDescription.setText(menuDetails.getDescription());
        cooktimeID = (TextView)findViewById(R.id. cooktimeID);
        cooktimeID.setText(menuDetails.getTimetoMakeFoods());

        mRecyclerView = (RecyclerView)findViewById(R.id.showComments);
        mLayoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getFeedback();
        getRatingvar();
        sendFeedback();
        getRecipeBy();
    }

    private void getRecipeBy(){
        FirebaseUIConnector.DatabaseConnection("User");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;

        mDatabaseReference.orderByChild("name").equalTo(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    SignUp user = data.getValue(SignUp.class);
                    txtRecipeBy.setText("Recipe By: "+user.getName());
                    Picasso.with(getBaseContext()).load(user.getProfileImage()).into(imgRecipeByImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getFeedback(){
        FirebaseUIConnector.DatabaseConnection("comments");
        FirebaseDatabase mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        DatabaseReference mdatabaseReference = FirebaseUIConnector.mDataBaseReference;

        mdatabaseReference.orderByChild("menuID").equalTo(menuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot data : dataSnapshot.getChildren()) {
                   comments items = data.getValue(comments.class);
                   mCommentsList.add(items);
               }
               commentsAdapter adapter = new commentsAdapter(ShowRecipeDetailsActivity.this, mCommentsList);
               mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendFeedback(){
        FirebaseUIConnector.DatabaseConnection("comments");
        FirebaseDatabase mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        final DatabaseReference mdatabaseReference = FirebaseUIConnector.mDataBaseReference;
        btnSubmit = (Button)findViewById(R.id.btnSubmitComments);
        usedName.setText(currentuser.currentUser);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comments model = new comments(usedName.getText().toString(),menuid, Validation.mydate,userCommmentsID.getText().toString(),
                        RatinginitialvalueratingID.getRating());
                mdatabaseReference.push().setValue(model);
                clear();

            }
        });
    }
    private void clear(){
        userCommmentsID.setText("");
        RatinginitialvalueratingID.setRating(0);
    }
    private void addComments(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("comments");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments model = new comments(usedName.getText().toString(),menuid, Validation.mydate,userCommmentsID.getText().toString(),
                        mRating);
                mDatabaseReference.push().setValue(model);
                Toast.makeText(getApplicationContext(),"Comment Added Successfully",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getRatingvar() {

        RatinginitialvalueratingID = (RatingBar) findViewById(R.id.RatinginitialvalueratingID);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        RatinginitialvalueratingID.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                mRating = rating;
                Toast.makeText(getApplicationContext(),String.valueOf(mRating),Toast.LENGTH_LONG).show();

            }
        });
    }

}
