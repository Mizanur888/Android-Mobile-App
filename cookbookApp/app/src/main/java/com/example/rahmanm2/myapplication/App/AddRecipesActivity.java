package com.example.rahmanm2.myapplication.App;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmanm2.myapplication.App.currentUser.currentuser;
import com.example.rahmanm2.myapplication.FirebaseConnect.FirebaseUIConnector;
import com.example.rahmanm2.myapplication.IngrediansModel.HorizontalViewIngredians;
import com.example.rahmanm2.myapplication.IngrediansModel.menuItemID;
import com.example.rahmanm2.myapplication.R;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddRecipesActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    private Spinner mSpinner, mCountryTypeSpinner, mFoodcatagory, MenuItemID;
    private android.support.v7.widget.Toolbar mToolbar;
    private String userID;
    private Uri mImageURI;
    private Bitmap bitmap;
    ProgressBar progress_barID;
    private EditText edtDescription,edtIngredians,edtDirection;
    private TextView recipeNameedt;
    private ImageView menuImage;
    BottomNavigationView bottomNavID;
    Button submitRecipe;
    EditText edtHour, edtMin;
    //firebase databse
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    //firebase storage
    FirebaseStorage mFirebaseStorage;
    StorageReference mStorageReference;
    private StorageTask mUploadStorageTask;

    private menuItemID mMenuItemID;
    //food country name
    String CountryName = "";
    String SpicyType = "";
    String RecipeCatagory = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipes);

        if (getIntent() != null) {
            userID = getIntent().getStringExtra("userID");
        }

        setup();

        //init Firebase
        FirebaseUIConnector.StorageConnection("menuImage");
        mFirebaseStorage = FirebaseUIConnector.mFirebaseStorage;
        mStorageReference = FirebaseUIConnector.mStorageReference;

        FirebaseUIConnector.DatabaseConnection("Recipes");
        mFirebaseDatabase = FirebaseUIConnector.mFirebaseDatabase;
        mDatabaseReference = FirebaseUIConnector.mDataBaseReference;

        //init layout
        recipeNameedt = (TextView) findViewById(R.id.recipeNameedt);
        menuImage = (ImageView) findViewById(R.id.menuImage);
        bottomNavID = (BottomNavigationView) findViewById(R.id.bottomNavID);
        progress_barID = (ProgressBar)findViewById(R.id.progress_barID);
        submitRecipe = (Button) findViewById(R.id.submitRecipe);


        edtDescription = (EditText)findViewById(R.id.edtDescription);
        edtIngredians = (EditText)findViewById(R.id.edtIngredians);
        edtDirection = (EditText)findViewById(R.id.edtDirection);

        edtHour = (EditText)findViewById(R.id.edtHour);
        edtMin = (EditText)findViewById(R.id.edtMin);

        submitRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtIngredians.getText().toString().isEmpty()){
                    edtIngredians.setEnabled(true);
                    edtIngredians.setError("Error Field can not be empty");
                    Toast.makeText(getApplicationContext(),"Please Enter Ingredians",Toast.LENGTH_LONG).show();
                }
                uploadImage();
            }
        });
        // uploadImage,TakeImage


        //comprase Image
        //Bitmap bitmap = Bitmap.createScaledBitmap("Image",WIdth,height,true);


        //  AddrecipeInit catatogryBLD = new AddRecipesActivity(this,this,mFoodcatagory);
        setUpBottomNavigationView();

    }

    private void setUpBottomNavigationView() {
        bottomNavID.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.uploadImage:
                        //TODO implements Upload Image
                        chooseImage();
                        break;
                    case R.id.TakeImage:
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

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void comprassImage() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
    }

    private void uploadImage() {
        // Bitmap bitmap = Bitmap.createScaledBitmap(mImageURI,256,250,true);
        if (mImageURI != null) {

            StorageReference ref = mStorageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageURI));
            mUploadStorageTask = ref.putFile(mImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress_barID.setProgress(0);
                        }
                    }, 5000);

                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String photoLink = uri.toString();
                            Toast.makeText(AddRecipesActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();

                           String description =  edtDescription.getText().toString();
                           String ingredian = edtIngredians.getText().toString();
                           String direction = edtDirection.getText().toString();

                            String hour = edtHour.getText().toString();
                            String min = edtMin.getText().toString();
                            String hourMin = "";
                            if(!hour.isEmpty()){
                                 hourMin = hour+"h"+min+"m";
                            }
                            else{
                                hourMin = min+"m";
                            }

                            String ba = photoLink.toString();

                            String menuid = mMenuItemID.getMenuID();
                            HorizontalViewIngredians ingredians = new HorizontalViewIngredians(currentuser.currentUser,menuid,
                                    recipeNameedt.getText().toString(),hourMin,SpicyType,CountryName,RecipeCatagory,ba,
                                    description,ingredian,direction);

                            String uploadID = mDatabaseReference.push().getKey();
                            mDatabaseReference.child(uploadID).setValue(ingredians);
                            clear();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progress_barID.setProgress((int) progress);

                }
            });
        } else {
            Toast.makeText(AddRecipesActivity.this, "There Are No Image To Uload", Toast.LENGTH_LONG).show();
        }
    }

    private void clear(){
        recipeNameedt.setText("");
        menuImage.setImageResource(0);
        edtHour.setText("");
        edtMin.setText("");
        //edtSec = (Edit
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

        //capture Image to database
        private void dispatchTakePictureIntent () {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        private void setup () {
            Fade fade = new Fade();
            fade.setDuration(400);
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setEnterTransition(fade);
            //ListView listView = (ListView)findViewById(R.id.foodsList);
            List<String> list = new ArrayList<>();
            list.add("brokoli");
            list.add("brokoli");
            list.add("brokoli");
            list.add("brokoli");
            list.add("brokoli");
            list.add("brokoli");
            list.add("brokoli");
            list.add("brokoli");
            // checkTextViewAdapter checkTextViewAdapter = new checkTextViewAdapter(getApplicationContext(),list);
            //listView.setAdapter(checkTextViewAdapter);
            setupToolbar();
            menuItemID();
            setupSpinner();
            setupConuntrypinner();
            setupSpinnerCatagory();
        }
    private void menuItemID () {
        MenuItemID = (Spinner) findViewById(R.id.MenuItemID);

            ArrayAdapter  arrayAdapter = new ArrayAdapter (this, android.R.layout.simple_spinner_item,new menuItemID[]{
                    new menuItemID("001","Burger"),
                    new menuItemID("002","Chicken"),
                    new menuItemID("003","Pasta"),
                    new menuItemID("004","Pizza")
            });
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            MenuItemID.setAdapter(arrayAdapter);

            MenuItemID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                    mMenuItemID = (menuItemID) adapterView.getItemAtPosition(postion);
                    // Toast.makeText(getApplicationContext(), "Selected: " + mMenuItemID.getMenuID(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
    }
        private void setupConuntrypinner () {
            mCountryTypeSpinner = (Spinner) findViewById(R.id.CountryType);
            String[] country = {"Usa Recipe", "Italy Recipe", "Mexico Recipe", "Chines Recipe", "Japnis Recipe", "Indian Recipe"};
            final List<String> countryNam = new ArrayList<>(Arrays.asList(country));
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countryNam) {
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position % 2 == 1) {
                        // Set the item background color
                        tv.setBackgroundColor(Color.parseColor("#4CAF50"));
                    } else {
                        // Set the alternate item background color
                        tv.setBackgroundColor(Color.parseColor("#69F0AE"));
                    }
                    return view;
                }

            };
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            mCountryTypeSpinner.setAdapter(arrayAdapter);
            mCountryTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                    CountryName = (String) adapterView.getItemAtPosition(postion);
                   // Toast.makeText(getApplicationContext(), "Selected: " + CountryName, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        private void setupSpinner () {
            mSpinner = (Spinner) findViewById(R.id.foodType);
            String[] foodSpices = {"Spicy", "Sweet", "Savory", userID};
            final List<String> foodsSpice = new ArrayList<>(Arrays.asList(foodSpices));

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, foodsSpice) {
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position % 2 == 1) {
                        // Set the item background color
                        tv.setBackgroundColor(Color.parseColor("#4CAF50"));
                    } else {
                        // Set the alternate item background color
                        tv.setBackgroundColor(Color.parseColor("#69F0AE"));
                    }
                    return view;
                }

            };

            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            mSpinner.setAdapter(arrayAdapter);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                    SpicyType = (String) adapterView.getItemAtPosition(postion);
                    //Toast.makeText(getApplicationContext(), "Selected: " + SpicyType, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        private void setupSpinnerCatagory () {
            mFoodcatagory = (Spinner) findViewById(R.id.recipe_forCatagory);
            String[] catagory_for = {"Breakfast", "Lunch", "Dinner", "Other"};
            List<String> catagory = new ArrayList<>(Arrays.asList(catagory_for));
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, catagory) {
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position % 2 == 1) {
                        // Set the item background color
                        tv.setBackgroundColor(Color.parseColor("#4CAF50"));
                    } else {
                        // Set the alternate item background color
                        tv.setBackgroundColor(Color.parseColor("#69F0AE"));
                    }
                    return view;
                }
            };
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            mFoodcatagory.setAdapter(arrayAdapter);
            mFoodcatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                    RecipeCatagory = (String) adapterView.getItemAtPosition(postion);
                   // Toast.makeText(getApplicationContext(), "Selected: " + RecipeCatagory, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        private void setupToolbar () {
            // mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.addrecipesToolbarID);
            //mToolbar.setTitle("Recipes");
            //setSupportActionBar(mToolbar);
        }

        @Override
        public void onBackPressed () {
            super.onBackPressed();
        }

}
