package com.example.rahmanm2.myapplication.App;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comexamplerahmanm2myapplicationappdatamodel_libary.SignUp;
import com.example.rahmanm2.myapplication.Adapter.mainMenuVerticalMenu;
import com.example.rahmanm2.myapplication.App.FragmentModel.BreakFastFragment;
import com.example.rahmanm2.myapplication.App.FragmentModel.DinnerFragment;
import com.example.rahmanm2.myapplication.App.FragmentModel.LunchFragment;
import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.IngrediansModel.initmainView;
import com.example.rahmanm2.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodsrecipesMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,FragmentManager.OnBackStackChangedListener
{
    private android.support.v7.widget.Toolbar mainPageToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mBarDrawerToggle;
    private NavigationView mNavigationView;
    private Menu mMenu;
    String  userid = "";
    private RecyclerView mRecyclerView;
    private GridLayoutManager manager1;
    private FloatingActionButton voiceSearch;
    private final int REQUEST_SPECH_CODE = 888;
    private MaterialSearchBar MenuSearchBarID;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    //nav header
    private ImageView navHeaderImage;
    private TextView txtNameHeader;
    private TextView txtEmailHeader;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    //private final AlertDialog.Builder loginBuilder = new AlertDialog.Builder(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsrecipes_main);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayoutID);
        mNavigationView = (NavigationView)findViewById(R.id.navigationViewID);
        //init navigation header
        View hView =  mNavigationView.getHeaderView(0);
        navHeaderImage = (ImageView)hView.findViewById(R.id.navHeaderImage);
        txtNameHeader = (TextView) hView.findViewById(R.id.txtNameHeader);
        txtEmailHeader = (TextView)hView.findViewById(R.id.txtEmailHeader);
        //end of init navigatin header
        if(getIntent()!=null){
            userid = getIntent().getStringExtra("UserId");
        }
        MenuSearchBarID = (MaterialSearchBar)findViewById(R.id.MenuSearchBarID) ;
        MenuSearchBarID.setHint("Please Enter Item Name to Search Recipe");


        setupToolbar();
        setupNavDrawer();
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavID);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        setUpRecyclerView();
        voiceSearch();
        setupBottomNavigationView();

        if(LoginActivity.IsLogedin == true){
                setupNavHeaderData();
        }


        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setupNavHeaderData(){
        //TODO
        FirebaseUIConnector.DatabaseConnection("User");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;

        mDatabaseReference.orderByChild("name").equalTo(currentuser.currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    SignUp currentUser = data.getValue(SignUp.class);
                    txtNameHeader.setText(currentUser.getName());
                    txtEmailHeader.setText(currentUser.getEmail());
                    Picasso.with(getBaseContext()).load(currentUser.getProfileImage()).into(navHeaderImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


//<item android:state_checked="true"
  //  android:drawable="@color/button_pressed"/>
    private void setupBottomNavigationView(){
        //bottomNavigationView.setBackgroundColor(Color.parseColor("#607D8B"));
        bottomNavigationView.setItemBackgroundResource(R.color.bootomColor);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.breakfastID:

                        bottomNavigationView.setItemBackgroundResource(R.drawable.button_background);
                        BreakFastFragment breakFastFragment = new BreakFastFragment();
                        //breakFastFragment.menuID(getBaseContext());
                        mTransaction = mFragmentManager.beginTransaction();
                        mTransaction.replace(R.id.mainpageLayoutID, breakFastFragment, "breakfast");
                        mTransaction.addToBackStack("AddBreakfast");
                        mTransaction.commit();

                        if(breakFastFragment!=null){
                            fagTools();

                        }

                    break;
                    case R.id.LunchID:
                        bottomNavigationView.setItemBackgroundResource(R.drawable.button_background);
                        LunchFragment lunchFragment = new LunchFragment();
                        mTransaction= mFragmentManager.beginTransaction();
                        mTransaction.replace(R.id.mainpageLayoutID,lunchFragment,"launch");
                        mTransaction.addToBackStack("AddLunch");
                        mTransaction.commit();
                        if(lunchFragment!=null){
                            fagTools();

                        }
                        break;
                    case R.id.dinnerID:
                        bottomNavigationView.setItemBackgroundResource(R.drawable.button_background);
                        DinnerFragment dinnerFragment = new DinnerFragment();
                        mTransaction = mFragmentManager.beginTransaction();
                        mTransaction.replace(R.id.mainpageLayoutID,dinnerFragment,"dinner");
                        mTransaction.addToBackStack("AddDinner");
                        mTransaction.commit();
                        if(dinnerFragment!=null){
                            fagTools();

                        }
                        break;
                }
                return true;
            }
        });
    }
    private void voiceSearch(){
        voiceSearch = (FloatingActionButton)findViewById(R.id.voiceSearch);
        voiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please Say Something to search");
                try{
                    startActivityForResult(intent,REQUEST_SPECH_CODE);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Sorry Device Doesn't Support Spech Input",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_SPECH_CODE:{
                if(resultCode==RESULT_OK && null!=data){
                    ArrayList<String>result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String spechText = MenuSearchBarID.getText().toString()+"\n"+result.get(0);
                    MenuSearchBarID.setText(spechText);
                }
            }
        }
    }

    private void setUpRecyclerView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.mainVerticalPageRecyclerID);
       // mRecyclerView.setHasFixedSize(true);

        manager1 = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(manager1);
        //LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(mRecyclerView.getContext(),
         //       R.anim.layout_fall_down);
        //mRecyclerView.setLayoutAnimation(controller);

        mainMenuVerticalMenu adapter = new mainMenuVerticalMenu(initmainView.getRecipesView(),FoodsrecipesMainActivity.this);
        mRecyclerView.setAdapter(adapter);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    protected void onResume() {
        super.onResume();
        //login with google and yahoo

        /*FireBaseUI.openFirebase("CookBookUser",FoodsrecipesMainActivity.this);
        mFirebaseDatabase = FireBaseUI.mFirebaseDatabase;
        mDatabaseReference = FireBaseUI.mDatabaseReference;
        FireBaseUI.AttachListener();*/
    }

    //set up navigation drawer
    private void setupNavDrawer(){
        mNavigationView.setNavigationItemSelectedListener(this);
        mBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mainPageToolbar,R.string.Draw_Open,R.string.Draw_Closed);
        mDrawerLayout.addDrawerListener(mBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mBarDrawerToggle.syncState();
            }
        });
    }
    //onclick navigation item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.idUsa:
                Toast.makeText(this,"USA Foods",Toast.LENGTH_SHORT).show();
                return true;

        }
        CloseDrawer();
        return true;
    }

    //menu option logic
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting,menu);
        MenuItem signOut = menu.findItem(R.id.idSignOut);
        MenuItem favoriteItem = menu.findItem(R.id.idmyFavoriteID);
        MenuItem idMyrecipesID = menu.findItem(R.id.idMyrecipesID);
        MenuItem idAddRecipe = menu.findItem(R.id.idAddRecipe);
        MenuItem idSignIn = menu.findItem(R.id.idSignIn);
        MenuItem idAccountSetting = menu.findItem(R.id.idAccountSetting);
        if(LoginActivity.IsLogedin==true){
            favoriteItem.setVisible(true);
            signOut.setVisible(true);
            idMyrecipesID.setVisible(true);
            idAddRecipe.setVisible(true);
            idAccountSetting.setVisible(true);
            idSignIn.setVisible(false);
        }
        else if(LoginActivity.IsLogedin==false){
            favoriteItem.setVisible(false);
            signOut.setVisible(false);
            idMyrecipesID.setVisible(false);
            idAddRecipe.setVisible(false);
            idAccountSetting.setVisible(false);
            idSignIn.setVisible(true);
        }

        return true;
    }

    //on menu option click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idAccountSetting:
                Toast.makeText(FoodsrecipesMainActivity.this,"Account Setting",Toast.LENGTH_LONG).show();
                break;
            case R.id.idSignOut:
                Intent SignIn = new Intent(FoodsrecipesMainActivity.this,LoginActivity.class);
                SignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(SignIn);
                break;
            case R.id.idAddRecipe:
                if(LoginActivity.IsLogedin == true) {
                    String user = userid;
                    Intent intent = new Intent(FoodsrecipesMainActivity.this, AddRecipesActivity.class);
                    intent.putExtra("userID",user);
                    startActivity(intent);
                }
                break;
            case R.id.idSignIn:
                if(LoginActivity.IsLogedin == false){
                    startActivity(new Intent(FoodsrecipesMainActivity.this,LoginActivity.class));
                }
                break;
            case R.id.idmyFavoriteID:
                //if(LoginActivity.IsLogedin == true){
                    startActivity(new Intent(FoodsrecipesMainActivity.this,ShowFavoriteActivity.class));
                //}
                break;
            case R.id.idMyrecipesID:
                //TODO redirect to myrecipe pgee
                startActivity(new Intent(FoodsrecipesMainActivity.this,MyRecipesActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);

    }


    private void setupToolbar(){
        mainPageToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.mainpageToolbarID);
        setSupportActionBar(mainPageToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainPageToolbar.setTitle("CookBook");
        setSupportActionBar(mainPageToolbar);

    }

    private void fagTools(){
       android.support.v7.widget.Toolbar mainPageToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.mainpageToolbarID);
        setSupportActionBar(mainPageToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");
        mainPageToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 BreakFastFragment fastFragment = (BreakFastFragment) mFragmentManager.findFragmentByTag("breakfast");
                 DinnerFragment dinnerFragment = (DinnerFragment)mFragmentManager.findFragmentByTag("dinner");
                 LunchFragment lunchFragment = (LunchFragment)mFragmentManager.findFragmentByTag("launch");
                 if(fastFragment!=null){
                     FragmentTransaction finalTransaction = mFragmentManager.beginTransaction();
                     frag(finalTransaction,fastFragment);
                 }
                 if(dinnerFragment!=null){
                     FragmentTransaction finalTransaction1 = mFragmentManager.beginTransaction();
                     frag(finalTransaction1,dinnerFragment);
                 }
                 if(lunchFragment!=null){
                     FragmentTransaction finalTransaction2 = mFragmentManager.beginTransaction();
                     frag(finalTransaction2,lunchFragment);
                 }
                 else {
                     Toast.makeText(getApplicationContext(), "Clicked Here TO Home", Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }

    private void frag(FragmentTransaction finalTransaction, Fragment fragment){
        finalTransaction.detach(fragment);
        finalTransaction.commit();
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavID);
        bottomNavigationView.setItemBackgroundResource(R.color.bootomColor);
        setupToolbar();
        setupNavDrawer();
    }

    private void showDrawer(){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
    private void CloseDrawer(){
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            CloseDrawer();

        }
        if(mFragmentManager.getBackStackEntryCount()>0){
            mFragmentManager.popBackStack();
        }
        else
            super.onBackPressed();
        if(LoginActivity.IsLogedin == true) {
            LoginActivity.IsLogedin = false;
            Intent SignIn = new Intent(FoodsrecipesMainActivity.this, LoginActivity.class);
            SignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(SignIn);
        }
    }

    @Override
    public void onBackStackChanged() {
        int length = mFragmentManager.getBackStackEntryCount();
        StringBuilder msg = new StringBuilder("");
        for(int i = 0;i<length;i++){
            msg.append("Index").append(i).append(" : ");
            msg.append(mFragmentManager.getBackStackEntryAt(i).getName());
            msg.append("\n");
        }
        Log.i("fragment","\n"+msg.toString()+"\n");
    }
}
