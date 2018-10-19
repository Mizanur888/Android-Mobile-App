package com.example.rahmanm2.myapplication.App;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmanm2.myapplication.HelperModel.Validation;
import com.example.rahmanm2.myapplication.R;
import com.example.rahmanm2.myapplication.UserLoginModel.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import info.hoang8f.widget.FButton;

public class SignUpActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener{

    private TextInputLayout layoutUID,layoutEmailid,layoutPasswordID,layoutConforPassword;
    private TextInputEditText UserUid,UserEmailid,UserPasswordID,UserConfPassword;
    private Button btnSkeepID,btnSignUpID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;
    private TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        txtTitle.setTypeface(typeface);
        //init button
        btnSkeepID = (Button)findViewById(R.id.btnSkeepID);
        btnSignUpID = (Button)findViewById(R.id.btnSignUpID);
        //inputlayout
        layoutUID = (TextInputLayout)findViewById(R.id.layoutUID);
        layoutEmailid = (TextInputLayout)findViewById(R.id.layoutEmailid);
        layoutPasswordID = (TextInputLayout)findViewById(R.id.layoutPasswordID);
        layoutConforPassword = (TextInputLayout)findViewById(R.id.layoutConforPassword);

        UserUid = (TextInputEditText)findViewById(R.id.UserUid);
        UserEmailid = (TextInputEditText)findViewById(R.id.UserEmailid);
        UserPasswordID = (TextInputEditText)findViewById(R.id.UserPasswordID);
        UserConfPassword = (TextInputEditText)findViewById(R.id.UserConfPassword);

        UserUid.addTextChangedListener(this);
        UserEmailid.addTextChangedListener(this);
        UserPasswordID.addTextChangedListener(this);
        UserConfPassword.addTextChangedListener(this);

        UserUid.setOnFocusChangeListener(this);
        UserEmailid.setOnFocusChangeListener(this);
        UserPasswordID.setOnFocusChangeListener(this);
        UserConfPassword.setOnFocusChangeListener(this);
        signUpAction();
        testPassword();
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
    public void onFocusChange(View view, boolean hasFocused) {
        if(!hasFocused){
            Validation.validate(UserUid,UserEmailid,UserPasswordID,UserConfPassword,
                    layoutUID,layoutEmailid,layoutPasswordID,layoutConforPassword);
        }
    }
    private void signUpUser(final SignUp model){
        final String nam = UserUid.getText().toString();
        mProgressDialog = new ProgressDialog(SignUpActivity.this);
        mProgressDialog.setMessage("Please Wait .......");
        mProgressDialog.show();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference("User");

            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(nam).exists()){
                        mProgressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this,"User Exist Please Sign In",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                    }
                    else{
                        mProgressDialog.dismiss();
                        if((UserPasswordID.getText().toString().length()>=8)&&(UserConfPassword.getText().toString().length()>=8)) {
                            mDatabaseReference.child(nam).setValue(model);
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(SignUpActivity.this,"Please Enter Password Greater than 8",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
    private void signUpAction(){
        btnSignUpID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //created At time
                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                SignUp model =new SignUp(UserUid.getText().toString(),UserEmailid.getText().toString(),
                        UserPasswordID.getText().toString(),UserConfPassword.getText().toString(),mydate);

                if(!UserUid.getText().toString().trim().isEmpty() && !UserEmailid.getText().toString().trim().isEmpty()
                        && !UserPasswordID.getText().toString().trim().isEmpty() && !UserConfPassword.getText().toString().trim().isEmpty() )
                    if(model.getPassword().equals(model.getConfromPasswprd())) {
                        signUpUser(model);
                    }
                    else{
                        Toast.makeText(SignUpActivity.this,"Password Does Not Match",Toast.LENGTH_LONG).show();
                    }
                else{
                    Toast.makeText(SignUpActivity.this,"Please Fill up all the field",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void testPassword(){
        btnSkeepID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation valid = new Validation();
                try {
                    String encrpt = valid.encrypt(UserPasswordID.getText().toString());
                    String decrpt = valid.decrypt(UserPasswordID.getText().toString());
                    Log.d("pasw",encrpt);
                    Log.d("paswd",decrpt);
                    Toast.makeText(getApplication(),"",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

