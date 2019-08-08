package com.example.rahmanm2.myapplication.FirebaseConnect;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUIConnector {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDataBaseReference;
    public static ChildEventListener mfirebaseChildListener;
    //fire base storage
    public static FirebaseStorage mFirebaseStorage;
    public static StorageReference mStorageReference;

    //fire base auth
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    //check for Admain
    public static boolean IsAdmain = false;
    private static FirebaseUIConnector mFirebaseUI;
    private FirebaseUIConnector(){

    }

    public static void DatabaseConnection(String url){
        if(mFirebaseUI == null){
            mFirebaseDatabase = FirebaseDatabase.getInstance();
        }
        mDataBaseReference = mFirebaseDatabase.getReference(url);
    }

    public static void StorageConnection(String url){
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child(url);
    }
}
