package com.example.rahmanm2.myapplication.App;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comexamplerahmanm2myapplicationappdatamodel_libary.InterfaceModel.IProgrebar;
import com.example.comexamplerahmanm2myapplicationappdatamodel_libary.SignUp;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.HelperModel.Validation;
import com.example.rahmanm2.myapplication.HelperModel.passwordHash;
import com.example.rahmanm2.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class SignUpActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener{

    private TextInputLayout layoutUID,layoutEmailid,layoutPasswordID,layoutConforPassword;
    private TextInputEditText UserUid,UserEmailid,UserPasswordID,UserConfPassword;
    private Button btnSignUpID, btnSkeepID;
    private ImageView menuImage;

    //firebase databse
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    //firebase storage
    FirebaseStorage mFirebaseStorage;
    StorageReference mStorageReference;
    private StorageTask mUploadStorageTask;


    private ProgressDialog mProgressDialog;
    private TextView txtTitle;
    private Toolbar mToolbar;
    private boolean signupNitifacation = false;
    private BottomNavigationView bottomNavIDSignUp;
    private static final int IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri mImageURI;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Init firebase
        FirebaseUIConnector.DatabaseConnection("User");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;

        FirebaseUIConnector.StorageConnection("UserImage");
        mFirebaseStorage = FirebaseUIConnector.mFirebaseStorage;
        mStorageReference = FirebaseUIConnector.mStorageReference;


        bottomNavIDSignUp = (BottomNavigationView)findViewById(R.id.bottomNavIDSignUp);
        menuImage = (ImageView)findViewById(R.id.menuImage);
        initData();
        setUpToolbar();
        setupNavaigationDrawer();
        signUpAction();

    }
    private void initData(){
        //txtTitle = (TextView)findViewById(R.id.txtTitle);
        //Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        //txtTitle.setTypeface(typeface);
        //init button
        //btnSkeepID = (Button) findViewById(R.id.btnSkeepID);
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
    }

    private void setupNavaigationDrawer(){
        bottomNavIDSignUp.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.uploadImageSignup:
                        chooseImage();
                        break;
                    case R.id.TakeImageSignUp:
                        dispatchTakePictureIntent();
                        break;
                }
                return true;
            }
        });
    }
    //choose Image from fe
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }
    private void dispatchTakePictureIntent () {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    mImageURI = data.getData();
                    //Picasso.with(this).load(mImageURI).into(mUploadImageViedID);
                    //Uri Img = data.getData();
                    try {
                        InputStream stream = getContentResolver().openInputStream(mImageURI);
                        bitmap = BitmapFactory.decodeStream(stream);
                        menuImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 2:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Log.d("Image", "Image captured" + data.getData());
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    menuImage.setImageBitmap(imageBitmap);
                    //mImageURI = getImageUri(getApplicationContext(), imageBitmap);
                    break;
                }

        }
    }



    private void setUpToolbar(){
        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.signUpToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login Page");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
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
        final String userEmail = UserEmailid.getText().toString();
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
               // String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                if(!UserUid.getText().toString().trim().isEmpty() && !UserEmailid.getText().toString().trim().isEmpty()
                        && !UserPasswordID.getText().toString().trim().isEmpty() && !UserConfPassword.getText().toString().trim().isEmpty() ) {

                    if (UserPasswordID.getText().toString().equals(UserConfPassword.getText().toString())) {
                        uploadImage();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Password Does Not Match", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                        Toast.makeText(SignUpActivity.this, "Please Fill up all the field", Toast.LENGTH_LONG).show();
                    }

            }
        });
    }

    private void uploadImage(){

        final View view = findViewById(R.id.signupcustomlayout);
        if(mImageURI!=null){
            StorageReference ref = mStorageReference.child(System.currentTimeMillis()+
            "."+getFileExtension(mImageURI));

            mUploadStorageTask = ref.putFile(mImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri>task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            try {
                                String profileimage1 = uri.toString();
                                String encrpt = passwordHash.generateStorngPasswordHash(UserPasswordID.getText().toString());
                                SignUp model =new SignUp(UserUid.getText().toString(),UserEmailid.getText().toString(),
                                        encrpt,encrpt,Validation.mydate,profileimage1.toString());
                                signUpUser(model);

                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (InvalidKeySpecException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            });
        }

    }
    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

}

