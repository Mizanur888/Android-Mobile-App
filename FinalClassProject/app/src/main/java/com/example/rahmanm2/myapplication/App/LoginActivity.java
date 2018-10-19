package com.example.rahmanm2.myapplication.App;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.FirebaseConnect.FireBaseUI;
import com.example.rahmanm2.myapplication.HelperModel.Validation;
import com.example.rahmanm2.myapplication.R;
import com.example.rahmanm2.myapplication.UserLoginModel.SignUp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import info.hoang8f.widget.FButton;

public class LoginActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener{

    private Button skeepBtn, SignInBtn, SignUpBtn, btnSignwithGoogle;
    private TextInputLayout layoutUid,layoutPassword,conf;
    private TextInputEditText UserUid,UserPassword,conp;
    private TextView txtTitle, txtforgetPassword;
    private android.support.v7.widget.Toolbar mToolbar;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;
    public static boolean IsLogedin;
    private static final int RC_SIGN_IN = 12345;
    private static final String TAG = "GoogleActivity";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    public GoogleSignInClient mGoogleSignInClient;
 //btnSkeep btnSignIn  btnSignUP
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //init fire base
        mFirebaseDatabase = FireBaseUI.mFirebaseDatabase;
        mDatabaseReference = FireBaseUI.mDatabaseReference;
       // FireBaseUI.OpenFirebase("User");

        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.loginToolbarID);
        setSupportActionBar(mToolbar);
        txtforgetPassword = (TextView)findViewById(R.id.txtForgetPasw);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exit");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        skeepBtn = (Button) findViewById(R.id.btnSkeep);
        skeepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.IsLogedin = false;

                startActivity(new Intent(LoginActivity.this,FoodsrecipesMainActivity.class));
            }
        });
        SignInBtn = (Button)findViewById(R.id.btnSignIn);
        SignUpBtn = (Button)findViewById(R.id.btnSignUP);
       // btnSignwithGoogle =(Button)findViewById(R.id.btnSignwithGoogle);

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        initData();

        signIn();
        ForgetPasswordUI();

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
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        txtTitle.setTypeface(typeface);

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
                if (dataSnapshot.child(userId).exists()) {
                    SignUp signIn = dataSnapshot.child(userId).getValue(SignUp.class);
                    if (signIn.getPassword().equals(password1)) {
                        IsLogedin = true;
                        Intent dataTransferIntent = new Intent(LoginActivity.this, FoodsrecipesMainActivity.class);
                         dataTransferIntent.putExtra("UserId",userId);
                         startActivity(dataTransferIntent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalied User Namse or Password", Toast.LENGTH_LONG).show();
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
                    currentuser.current =model;
                    login(model);
                }
                else {
                    Toast.makeText(LoginActivity.this,"Userid and password can not be Empty",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void ForgetPasswordUI(){
        txtforgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createForgetPassUI();
            }
        });
    }
    private void createForgetPassUI(){
        final LinearLayout linearLayout = new LinearLayout(LoginActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText UserID = new EditText(LoginActivity.this);
        UserID.setInputType(InputType.TYPE_CLASS_TEXT);
        UserID.setHint("UserID");
        linearLayout.addView(UserID);

        final EditText newpassword = new EditText(LoginActivity.this);
        newpassword.setInputType(InputType.TYPE_CLASS_TEXT);
        newpassword.setHint("New Password");
        linearLayout.addView(newpassword);

        final EditText confpassword = new EditText(LoginActivity.this);
        confpassword.setInputType(InputType.TYPE_CLASS_TEXT);
        confpassword.setHint("Confrom Password");
        linearLayout.addView(confpassword);

        final AlertDialog.Builder loginBuilder = new AlertDialog.Builder(LoginActivity.this);
        loginBuilder.setIcon(R.drawable.ic_account_box_black_24dp);
        loginBuilder.setTitle("Forget Password, create new Password");
        loginBuilder.setView(linearLayout);

        loginBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!UserID.getText().toString().trim().isEmpty() && !newpassword.getText().toString().trim().isEmpty()
                        && !confpassword.getText().toString().trim().isEmpty()){
                    SignUp model = new SignUp(UserID.getText().toString().trim(),
                            newpassword.getText().toString().trim(),
                            confpassword.getText().toString().trim());
                    updateUser(model);

                }
                else{
                    Toast.makeText(LoginActivity.this,"Field Can not be Empty",Toast.LENGTH_LONG).show();
                }
            }
        });
        loginBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        loginBuilder.show();
    }

    private void updateUser(final SignUp model){
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage("Please Wait..........");
        mProgressDialog.show();
        FireBaseUI.openFirebaseUI("User");
        mFirebaseDatabase = FireBaseUI.mFirebaseDatabase;
        mDatabaseReference = FireBaseUI.mDatabaseReference;
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                if(dataSnapshot.child(model.getName().toString().trim()).exists()){
                    if(model.getPassword().equals(model.getConfromPasswprd())){
                        mDatabaseReference.child(model.getName()).setValue(model);
                        Toast.makeText(LoginActivity.this,"Updated Password Sucessfully",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"User Does Not Exist Please Sign Up",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
