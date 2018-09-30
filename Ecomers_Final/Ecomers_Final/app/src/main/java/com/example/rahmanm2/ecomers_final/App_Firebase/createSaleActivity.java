package com.example.rahmanm2.ecomers_final.App_Firebase;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rahmanm2.ecomers_final.App.MainActivity;
import com.example.rahmanm2.ecomers_final.FirebaseUI.FirebaseUI;
import com.example.rahmanm2.ecomers_final.R;
import com.example.rahmanm2.ecomers_final.SampleDataModel.MainMenuItem;
import com.example.rahmanm2.ecomers_final.SampleDataModel.UploadImage;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
    private String urlImage = "http://10.200.193.103/Register/ImageUpload/imageUp.php";
    private Uri mImageURI;
    private Bitmap bitmap;
    //firebase databse
     FirebaseDatabase mFirebaseDatabase;
     DatabaseReference mDatabaseReference;

     //firebase storage
     FirebaseStorage mFirebaseStorage;
     StorageReference mStorageReference;
     private StorageTask mUploadStorageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sale);

        //init firbase Storgae
        //mStorageReference = FirebaseStorage.getInstance().getReference("uploadImage");
        FirebaseUI.StorageConnection("uploadImage");
        mFirebaseStorage = FirebaseUI.mFirebaseStorage;
        mStorageReference = FirebaseUI.mStorageReference;

        //init firebaseDatabase
        FirebaseUI.DatabaseConnection("ecomerssiteclassproject");
        mFirebaseDatabase = FirebaseUI.mFirebaseDatabase;
        mDatabaseReference = FirebaseUI.mDataBaseReference;
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference("ecomerssiteclassproject");

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
                    //insertImageToPhp();
                    uploadImage();
                    IndexStyleNotification();
                    //uploadFile();
                }
            }
        });
        takePhoto();
    }
    @TargetApi(26)
    private void IndexStyleNotification(){
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int notifyId = 002;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
              .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Uploaded")
                .setContentText("message Recived")
                .setSound(uri)
                .setAutoCancel(true);
        //set notification style to index
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        //set title when notifaction expend
        inboxStyle.setBigContentTitle("Expend");

        //Add your message Here or loop thorough to generate message
        inboxStyle.addLine("message 1");
        inboxStyle.addLine("message 2");
        inboxStyle.addLine("message 3");//i can add as many inboxstyle.add line i want

        builder.setStyle(inboxStyle);
        Intent intent = getIntent();

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(createSaleActivity.this);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            String channelID2 = "2";
            String channelName = "chanel2";
            NotificationChannel notificationChannel = new NotificationChannel(channelID2,channelName,NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorAccent);
            notificationChannel.setShowBadge(true);
            //notificationChannel.setSound();
            notificationChannel.enableVibration(true);
            builder.setChannelId(channelID2);

            if(notificationManager!=null){
                notificationManager.createNotificationChannel(notificationChannel);

            }
            else{
                builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
            }
            if(notificationManager!=null){
                notificationManager.notify(notifyId,builder.build());
            }
        }
    }
    private void insertImageToPhp(){

        StringRequest request = new StringRequest(Request.Method.GET, urlImage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parms = new HashMap<>();
                String urlImage = imageToString(bitmap);
                parms.put("Image",urlImage);
                return super.getParams();
            }
        };
        Volley.newRequestQueue(this).add(request);
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
                            Toast.makeText(createSaleActivity.this,"Upload Success",Toast.LENGTH_SHORT).show();

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
                    //Picasso.with(this).load(mImageURI).into(mUploadImageViedID);
                    //Uri Img = data.getData();
                    try {
                        InputStream stream = getContentResolver().openInputStream(mImageURI);
                        bitmap = BitmapFactory.decodeStream(stream);
                        mUploadImageViedID.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 2:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Log.d("Image","Image captured"+data.getData());
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                mUploadImageViedID.setImageBitmap(imageBitmap);
                 mImageURI = getImageUri(getApplicationContext(),imageBitmap);
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

    //bitmap to string

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[]imageByte = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageByte,Base64.DEFAULT);
        return encodeImage;
    }

}
