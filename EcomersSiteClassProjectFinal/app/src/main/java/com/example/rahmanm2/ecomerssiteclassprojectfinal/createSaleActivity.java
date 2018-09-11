package com.example.rahmanm2.ecomerssiteclassprojectfinal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmanm2.ecomerssiteclassprojectfinal.App.MainActivity;
import com.example.rahmanm2.ecomerssiteclassprojectfinal.SampleDataModel.UploadImage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class createSaleActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    Button mBtnChooseImage;
    Button btnImageUpload;
    Button mBtntakePhoto;
    EditText file_nameID;
    ImageView mUploadImageViedID;
    TextView TextViewShowUploadID;
    ProgressBar progress_barID;

    private Uri mImageURI;

    //firebase
     FirebaseDatabase mFirebaseDatabase;
     DatabaseReference mDatabaseReference;

     StorageReference mStorageReference;
     private StorageTask mUploadStorageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sale);

        //init firbase
        mStorageReference = FirebaseStorage.getInstance().getReference("uploadImage");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ecomerssiteclassproject");

        mBtntakePhoto = findViewById(R.id.TakePicID);
        progress_barID = (ProgressBar)findViewById(R.id.progress_barID);
        TextViewShowUploadID = (TextView)findViewById(R.id.TextViewShowUploadID);
        btnImageUpload = (Button)findViewById(R.id.btnImageUpload);
        file_nameID = (EditText)findViewById(R.id.file_nameID);
        mUploadImageViedID = (ImageView)findViewById(R.id.upload_ImageViedID);
        mBtnChooseImage = (Button)findViewById(R.id.btnChooseImage);
        mBtnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        btnImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadStorageTask!=null && mUploadStorageTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(),"Upload Image In progress",Toast.LENGTH_LONG).show();
                }
                else{
                    uploadImage();
                   // uploadFile();
                }
            }
        });
        takePhoto();
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadImage(){
        if(mImageURI!=null){

            StorageReference ref = mStorageReference.child(System.currentTimeMillis()
                                             +"."+getFileExtension(mImageURI));
            mUploadStorageTask = ref.putFile(mImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress_barID.setProgress(0);
                        }
                    },5000);

                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String photoLink = uri.toString();
                            Toast.makeText(createSaleActivity.this,"Upload Success",Toast.LENGTH_LONG).show();

                            String ba = photoLink.toString();
                            UploadImage upload = new UploadImage(file_nameID.getText().toString().trim(),
                                    ba );

                            String uploadID = mDatabaseReference.push().getKey();
                            mDatabaseReference.child(uploadID).setValue(upload);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(createSaleActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progress_barID.setProgress((int)progress);

                }
            });
        }
        else{
            Toast.makeText(createSaleActivity.this,"There Are No Image To Uload",Toast.LENGTH_LONG).show();
        }
    }

//working fine to upload Image
    private void uploadFile() {
        if(mImageURI != null) {

            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageURI));
            final String fileconext = fileReference.toString();

            final StorageReference ref = mStorageReference.child("uploadImage");

            mUploadStorageTask = ref.putFile(mImageURI);
            Task<Uri>uriTask = mUploadStorageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot>task) throws Exception  {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progress_barID.setProgress(0);
                        Uri downloadUri = task.getResult();
                        String miUrlOk = downloadUri.toString();

                        UploadImage upload = new UploadImage(file_nameID.getText().toString().trim(), miUrlOk);
                        String uploadId = mDatabaseReference.push().getKey();
                        mDatabaseReference.child(uploadId).setValue(upload);

                    }
                    else{
                        // Handle failures
                        // ...
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(createSaleActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            Toast.makeText(this, "No File selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    mImageURI = data.getData();
                    Picasso.with(this).load(mImageURI).into(mUploadImageViedID);
                }
                break;
            case 2:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                //mUploadImageViedID.setImageBitmap(imageBitmap);
               // mImageURI = getImageUri(getApplicationContext(),imageBitmap);
                break;
            }
        }
    }
    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    //capture Image to database
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void takePhoto(){
        mBtntakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

}
