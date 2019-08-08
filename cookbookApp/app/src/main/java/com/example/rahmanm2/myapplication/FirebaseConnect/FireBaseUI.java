package com.example.rahmanm2.myapplication.FirebaseConnect;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.rahmanm2.myapplication.App.FoodsrecipesMainActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;


public class FireBaseUI {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static final int RC_SIGN_IN = 1234;
    private static FireBaseUI mFireBaseUtil;
    private static Activity caller;

    //to check for admain
    public static boolean IsAdmain = false;

    //firebase autonication
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    //fire base storoge connection
    public static FirebaseStorage mFirebaseStorage;
    public static StorageReference mStorageReference;
    private FireBaseUI(){

    }
    public static void connectFirebaseUI(String Url){
        if(mFireBaseUtil==null){
            mFireBaseUtil = new FireBaseUI();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
        }
        mDatabaseReference = mFirebaseDatabase.getReference(Url);
    }
    public static void openFirebase(String Url, final Activity call){
        if(mFireBaseUtil==null){
            mFireBaseUtil = new FireBaseUI();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            caller = call;
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser()==null) {
                        FireBaseUI.SignIn();
                        IsAdmain = true;
                    }else{
                        String userId = firebaseAuth.getUid();
                       // checkAdmain(userId);
                    }
                    Toast.makeText(caller.getApplication(),"Welcome Back To CookBook App",Toast.LENGTH_LONG).show();
                }
            };
           // storageConnection("deals_picture");
        }
        mDatabaseReference = mFirebaseDatabase.getReference().child(Url);
    }
    private static void SignIn(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        // Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }
    public static void storageConnection(String url){
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child(url);
    }
    public static void AttachListener(){
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }
    public static void DetechListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }
}
