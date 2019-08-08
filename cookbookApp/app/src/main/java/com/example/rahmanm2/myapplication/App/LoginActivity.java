package com.example.rahmanm2.myapplication.App;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comexamplerahmanm2myapplicationappdatamodel_libary.SignUp;
import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.FirebaseConnect.FireBaseUI;
import com.example.rahmanm2.myapplication.HelperModel.Validation;
import com.example.rahmanm2.myapplication.HelperModel.passwordHash;
import com.example.rahmanm2.myapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LoginActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener{

    private CheckBox saveID;
    private Button skeepBtn, SignInBtn, btnSignwithGoogle;
    private TextInputLayout layoutUid,layoutPassword,conf;
    private TextInputEditText UserUid,UserPassword,conp;
    private TextView txtTitle, txtforgetPassword,SignUpBtn;
    private android.support.v7.widget.Toolbar mToolbar;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;
    public static boolean IsLogedin = false;
    private static final int RC_SIGN_IN = 12345;
    private static final String TAG = "GoogleActivity";
    //TODO add collapsingToolbar

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    public GoogleSignInClient mGoogleSignInClient;
 //btnSkeep btnSignIn  btnSignUP

    //shared preference
    private SharedPreferences mSharedPreferences;
    private static final String SAVE_USER_ID = "USERID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //init fire base
        //mFirebaseDatabase = FireBaseUI.mFirebaseDatabase;
        //mDatabaseReference = FireBaseUI.mDatabaseReference;
       // FireBaseUI.OpenFirebase("User");

        //for facebook hash
        //keyhashes();
        mSharedPreferences = getSharedPreferences(SAVE_USER_ID,MODE_PRIVATE);
        saveID = (CheckBox)findViewById(R.id.saveID);

        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.loginToolbarID);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Welcome To CookBook App");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtforgetPassword = (TextView)findViewById(R.id.txtForgetPasw);
        skeepBtn = (Button) findViewById(R.id.btnSkeep);
        skeepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.IsLogedin = false;

                startActivity(new Intent(LoginActivity.this,FoodsrecipesMainActivity.class));
            }
        });
        SignInBtn = (Button)findViewById(R.id.btnSignIn);
        SignUpBtn = (TextView) findViewById(R.id.btnSignUP);
       // btnSignwithGoogle =(Button)findViewById(R.id.btnSignwithGoogle);

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        initData();

        String storeUserID = mSharedPreferences.getString("USER_ID","");
        UserUid.setText(storeUserID);
        saveID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if(saveID.isChecked()){
                    saveID.setCompoundDrawablePadding(R.drawable.checked_mark);
                    saveID.setChecked(true);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("USER_ID",UserUid.getText().toString());
                    editor.apply();
                    UserUid.setText("");
                }
            }
        });
        signIn();
        ForgetPasswordUI();

    }
//hask key for facebook login
    private void keyhashes() {
        try {
           PackageInfo packageInfo = getPackageManager().getPackageInfo("com.example.rahmanm2.myapplication.App",
                                                                       PackageManager.GET_SIGNATURES);
            for(Signature signature:packageInfo.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void googleSignIn(){
        btnSignwithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireBaseUI.openFirebase("CookBookApp",LoginActivity.this);
                mFirebaseDatabase = FireBaseUI.mFirebaseDatabase;
                mDatabaseReference = FireBaseUI.mDatabaseReference;

                FireBaseUI.AttachListener();
                if(FireBaseUI.IsAdmain==true) {
                    startActivity(new Intent(LoginActivity.this,FoodsrecipesMainActivity.class));
                }
            }
        });
    }
    private void initData(){
        //txtTitle = (TextView)findViewById(R.id.txtTitle);
        //Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        //txtTitle.setTypeface(typeface);

        //inputlayout
        layoutUid = (TextInputLayout)findViewById(R.id.layoutUid);
        layoutPassword = (TextInputLayout)findViewById(R.id.layoutPassword);

        //TextInputEditText
        UserUid = (TextInputEditText)findViewById(R.id.UserUID);
        UserPassword = (TextInputEditText)findViewById(R.id.UserPassword);

        UserUid.addTextChangedListener(this);
        UserPassword.addTextChangedListener(this);

        UserUid.setOnFocusChangeListener(this);
        UserPassword.setOnFocusChangeListener(this);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean hasFocused) {

        if(!hasFocused){
            Validation.validateLogin( UserUid, UserPassword,
                    layoutUid,layoutPassword );
        }
    }

    private void login(SignUp model){

        final String userId = model.getName();
        final String password1 = model.getPassword();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("User");
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage("Please Wait");
        mProgressDialog.show();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                if (dataSnapshot.child(userId).exists())
                {
                    SignUp signIn = dataSnapshot.child(userId).getValue(SignUp.class);
                    //decode password
                    try {
                        boolean decrpt = passwordHash.validatePassword(password1,signIn.getPassword());
                        if (decrpt==true) {
                            IsLogedin = true;
                            currentuser.currentUser = userId;
                            Intent dataTransferIntent = new Intent(LoginActivity.this, FoodsrecipesMainActivity.class);
                            dataTransferIntent.putExtra("UserId",userId);
                            startActivity(dataTransferIntent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalied User Namse or Password", Toast.LENGTH_LONG).show();
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User dont exist Please Sign Up", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void signIn(){
        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserUid.getText().toString().isEmpty() && !UserPassword.getText().toString().isEmpty()) {
                    String uid = UserUid.getText().toString();
                    String password1 = UserPassword.getText().toString();
                    SignUp model = new SignUp(uid,password1);
                    currentuser.currentUser =uid;
                    login(model);
                }
                else {
                   // UserUid.setError("Please Enter User name");
                    Toast.makeText(LoginActivity.this,"Userid and password can not be Empty",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void ForgetPasswordUI(){
        txtforgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(LoginActivity.this,Forget_PasswordActivity.class));
            }
        });
    }


}
