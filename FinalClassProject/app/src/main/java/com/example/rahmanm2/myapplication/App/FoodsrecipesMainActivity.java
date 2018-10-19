package com.example.rahmanm2.myapplication.App;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rahmanm2.myapplication.DataBase.UserLoginModel;
import com.example.rahmanm2.myapplication.FirebaseConnect.FireBaseUI;
import com.example.rahmanm2.myapplication.HelperModel.helper;
import com.example.rahmanm2.myapplication.R;
import com.example.rahmanm2.myapplication.UserLoginModel.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FoodsrecipesMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private android.support.v7.widget.Toolbar mainPageToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mBarDrawerToggle;
    private NavigationView mNavigationView;
    private Menu mMenu;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    //private final AlertDialog.Builder loginBuilder = new AlertDialog.Builder(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsrecipes_main);

        setupToolbar();
        setupNavDrawer();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavID);
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

    private void setupNavDrawer(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayoutID);
        mNavigationView = (NavigationView)findViewById(R.id.navigationViewID);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting,menu);
        MenuItem signOut = menu.findItem(R.id.idSignOut);
        MenuItem favoriteItem = menu.findItem(R.id.idmyFavoriteID);
        if(LoginActivity.IsLogedin==true){
            favoriteItem.setVisible(true);
            signOut.setVisible(true);
        }
        else if(LoginActivity.IsLogedin==false){
            favoriteItem.setVisible(false);
            signOut.setVisible(false);
        }

        return true;
    }

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
            case R.id.idSignIn:
                Intent intent = new Intent(FoodsrecipesMainActivity.this,AddRecipesActivity.class);
                break;

        }
        return super.onOptionsItemSelected(item);

    }




    private void hashPassword(String password){
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte[]messageDigest = digest.digest();
            StringBuffer md5Hash = new StringBuffer();
            for(int i = 0;i<messageDigest.length;i++){
                String hash = Integer.toHexString(0xFF & messageDigest[i]);
                while (hash.length()<2){
                    hash = "0"+hash;
                    md5Hash.append(hash);
                }

            }
        }catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
    }

    private void setupToolbar(){
        mainPageToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.mainpageToolbarID);
        mainPageToolbar.setTitle("CookBook");
        setSupportActionBar(mainPageToolbar);
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
}
