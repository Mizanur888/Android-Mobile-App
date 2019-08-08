package com.example.rahmanm2.myapplication.App;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comexamplerahmanm2myapplicationappdatamodel_libary.SignUp;
import com.example.rahmanm2.myapplication.HelperModel.Validation;
import com.example.rahmanm2.myapplication.HelperModel.passwordHash;
import com.example.rahmanm2.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Forget_PasswordActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    ProgressDialog mProgressDialog;
    TextInputLayout flayoutUID ,flayoutPasswordID,flayoutConforPassword;
    TextInputEditText fUserUid,fUserPasswordID,fUserConfPassword;
    Button fbtnSignUpID;
    String createtAt = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);
        initData();
        update();
    }
    private void initData(){
        TextView fTxtTitle = (TextView)findViewById(R.id.fTxtTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        fTxtTitle.setTypeface(typeface);
        //layout
        flayoutUID = (TextInputLayout)findViewById(R.id.flayoutUID);
        flayoutPasswordID = (TextInputLayout)findViewById(R.id.flayoutPasswordID);
        flayoutConforPassword = (TextInputLayout)findViewById(R.id.flayoutConforPassword);

        //edittext
        fUserUid = (TextInputEditText)findViewById(R.id.fUserUid);
        fUserPasswordID = (TextInputEditText)findViewById(R.id.fUserPasswordID);
        fUserConfPassword = (TextInputEditText)findViewById(R.id.fUserConfPassword);

        //button
        fbtnSignUpID = (Button)findViewById(R.id.fbtnSignUpID);
    }
    //update user account
    private void update(){
        fbtnSignUpID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation.validateLogin(fUserUid,fUserPasswordID,fUserConfPassword,
                        flayoutUID,flayoutPasswordID,flayoutConforPassword);
                if(!fUserUid.getText().toString().trim().isEmpty() && !fUserPasswordID.getText().toString().trim().isEmpty()
                        && !fUserConfPassword.getText().toString().trim().isEmpty()) {
                    try {
                        String encrpt = passwordHash.generateStorngPasswordHash(fUserPasswordID.getText().toString());
                        SignUp model = new SignUp(fUserUid.getText().toString(),encrpt,
                                encrpt,Validation.mydate);
                        updateUser(model);
                        clear();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(Forget_PasswordActivity.this,"Please Fill up all the field",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void clear(){
        fUserUid.setText("");
        fUserPasswordID.setText("");
                fUserConfPassword.setText("");
    }
    private void updateUser(final SignUp model){
       final String nam = fUserUid.getText().toString();
        mProgressDialog = new ProgressDialog(Forget_PasswordActivity.this);
        mProgressDialog.setMessage("Please Wait..........");
        mProgressDialog.show();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("User");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(nam).exists()){
                    mProgressDialog.dismiss();
                    if(model.getPassword().equals(model.getConfromPasswprd())){
                        if((fUserPasswordID.getText().toString().length()>=8)&&(fUserConfPassword.getText().toString().length()>=8)) {
                            mDatabaseReference.child(nam).setValue(model);
                            Toast.makeText(Forget_PasswordActivity.this, "Sucessfull Please Sign In", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Forget_PasswordActivity.this,
                                                     LoginActivity.class));
                        }
                        else{
                            Toast.makeText(Forget_PasswordActivity.this,"Enter Password greater thanm 8",Toast.LENGTH_LONG).show();
                        }

                    }
                    else{
                        Toast.makeText(Forget_PasswordActivity.this,"User Password does not match",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(Forget_PasswordActivity.this,"User Does Not Exist Please Sign Up",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Forget_PasswordActivity.this, "Updated Password Faield", Toast.LENGTH_LONG).show();
            }
        });
    }
}
