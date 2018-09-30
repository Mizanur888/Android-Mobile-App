package com.example.rahmanm2.ecomers_final.App;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rahmanm2.ecomers_final.Adapter.Main_menu_recyclreAdapter;
import com.example.rahmanm2.ecomers_final.FirebaseUI.FirebaseUI;
import com.example.rahmanm2.ecomers_final.R;
import com.example.rahmanm2.ecomers_final.App_Firebase.createSaleActivity;
import com.example.rahmanm2.ecomers_final.App_Firebase.showManin_menuItem;
import com.example.rahmanm2.ecomers_final.SampleDataModel.MainMenuItem;
import com.example.rahmanm2.ecomers_final.Sample_Data_init.initMenuItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    NavigationView mNavigationView;

    private RecyclerView mRecyclerView;

    private List<MainMenuItem>mMainMenuItemsList;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainMenuItemsList = new ArrayList<>();
        //toolbarmDatabaseReference
        setUpToolbar();
        //recycler
        setuprec();
        //setUpRecyclermainmenuView();
        //nav menu setup
        setUpDrawer();
    }
    private void setuprec(){
        mRecyclerView = (RecyclerView)findViewById(R.id.main_menu_itemRecyclerID);
        Main_menu_recyclreAdapter adapter = new Main_menu_recyclreAdapter(MainActivity.this, initMenuItem.getMenuItem());
        mRecyclerView.setAdapter(adapter);
       // LinearLayoutManager manager = new LinearLayoutManager(this);
        //manager.setOrientation(LinearLayoutManager.VERTICAL);
        //mRecyclerView.setLayoutManager(manager);
        GridLayoutManager manager1 = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(manager1);
    }
    private void setUpRecyclermainmenuView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.main_menu_itemRecyclerID);
        mRecyclerView.setHasFixedSize(true);
        //LinearLayoutManager manager = new LinearLayoutManager(this);
        GridLayoutManager manager1 = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(manager1);

        FirebaseUI.DatabaseConnection("ecomerssiteclassproject");
        mFirebaseDatabase = FirebaseUI.mFirebaseDatabase;
        mDatabaseReference = FirebaseUI.mDataBaseReference;

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot data:dataSnapshot.getChildren()){
                   MainMenuItem items = data.getValue(MainMenuItem.class);
                   mMainMenuItemsList.add(items);
               }
                Main_menu_recyclreAdapter adapter = new Main_menu_recyclreAdapter(MainActivity.this,mMainMenuItemsList);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void setUpToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.menuToolbar);
        mToolbar.setTitle("Ecomers");
        setSupportActionBar(mToolbar);
    }
    private void setUpDrawer(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView)findViewById(R.id.NavDrawer);
        mNavigationView.setNavigationItemSelectedListener(this);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.Draw_Open,
                                                           R.string.Draw_Closed);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //  item.setCheckable(true);
        // String menuItem = (String)item.getTitle();
        // mTextView.setText(menuItem);
        switch (item.getItemId()){
            case R.id.idAuction:
                startActivity(new Intent(MainActivity.this,createSaleActivity.class));
                return true;
            case R.id.idBooks:
                startActivity(new Intent( MainActivity.this ,showManin_menuItem.class));
                return true;
            case R.id.idCloath:
                Toast.makeText(this,R.string.wentworth,Toast.LENGTH_SHORT).show();
                return true;
        }
        CloseDrawer();
        return true;
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
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.FragmentmenuID:
                startActivity(new Intent(MainActivity.this,FragmentActivity.class));
                return true;
            case R.id.cartID:
                startActivity(new Intent(MainActivity.this,Cart.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
