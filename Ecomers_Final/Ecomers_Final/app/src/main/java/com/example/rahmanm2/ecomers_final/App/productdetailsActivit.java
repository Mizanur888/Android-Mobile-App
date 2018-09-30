package com.example.rahmanm2.ecomers_final.App;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmanm2.ecomers_final.Adapter.feedbackAdapter;
import com.example.rahmanm2.ecomers_final.DataBase.Database;
import com.example.rahmanm2.ecomers_final.DataBase.InsertOrderPHP;
import com.example.rahmanm2.ecomers_final.R;
import com.example.rahmanm2.ecomers_final.SampleDataModel.FeedbackModel;
import com.example.rahmanm2.ecomers_final.SampleDataModel.MainMenuItem;
import com.example.rahmanm2.ecomers_final.SampleDataModel.OrderModel;
import com.example.rahmanm2.ecomers_final.Sample_Data_init.initMenuItem;
import com.example.rahmanm2.ecomers_final.config;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class productdetailsActivit extends AppCompatActivity  {

    feedbackAdapter mFeedbackAdapter;
    private RecyclerView feedbackRecyclerView;
    private RecyclerView.LayoutManager mFeedBackLayoutManager;
    public float rating = 0;
    private FButton feedbackID;
    private Toolbar mToolbar;
    private TextView priceText, NameText,DescText;
    private ImageView mImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mBtnChart;
    private RatingBar mRatingBar;
    public  static List<FeedbackModel>UserFeedack;//= new ArrayList<>();
    public static List<OrderModel>Orderlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        setToolbar();
        setupCollapseableToolbar();
        UserFeedack = new ArrayList<FeedbackModel>();
        Orderlist = new ArrayList<OrderModel>();
        setUpRecycler();
        mBtnChart = (FloatingActionButton)findViewById(R.id.btnCart);
        priceText = (TextView)findViewById(R.id.txtProductPrice);
        NameText = (TextView)findViewById(R.id.txtProductName);
        DescText = (TextView)findViewById(R.id.txtProductDescriptionID);
        mImageView = (ImageView)findViewById(R.id.menuImage);

        final Intent pdIntent = getIntent();
        MainMenuItem menuDetails = (MainMenuItem)pdIntent.getSerializableExtra(config.PRODUCT_DETAILS);
        if(menuDetails == null){
            menuDetails = new MainMenuItem();
        }

        priceText.setText(menuDetails.getProductPrice());
        NameText.setText(menuDetails.getProductName());
        DescText.setText(menuDetails.getProductDescription());
        Picasso.with(this)
                .load(menuDetails.getImages())
                .fit()
                .centerCrop()
                .into(mImageView);
       mCollapsingToolbarLayout.setTitle(menuDetails.getProductName());

        Log.d("items",menuDetails.getProductName()+"|"+menuDetails.getProductDescription()
                +" | "+menuDetails.getProductPrice());
        AddtoCart();
        ratingVar();
        setUpRecycler();
    }
    private void AddtoCart(){
        mBtnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               OrderModel model = new OrderModel("12",NameText.getText().toString(),DescText.getText().toString()
                       ,"3",
                       priceText.getText().toString());
               //operasi=insert&ProductID=22&ProductName=Cat&ProductDesc=verycat&Quantity=3&ProductPrice=9090

               Orderlist.add(model);
               String url = config.ROOT_URL+"order=insert&ProductID="+model.getProductID()+"&ProductName="+
                          model.getProductName()+"&ProductDesc="+model.getProductDesc()+"&Quantity="+model.getQuantity()
                          +"&ProductPrice="+model.getProductprice();

                InsertOrderPHP.InsertOrder(url,model,productdetailsActivit.this);
                Toast.makeText(productdetailsActivit.this,"Added To Cart",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.menuProduct);
        setSupportActionBar(mToolbar);
    }

    private void setupCollapseableToolbar(){
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collpsingToolbar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpanedAppbar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

    }
    //working with rating
    private void ratingVar(){

        mRatingBar = (RatingBar)findViewById(R.id.RATINGinitialvalueratingID);
        rating = mRatingBar.getRating();
        feedbackID = (FButton) findViewById(R.id.feedbackID);
        feedbackID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackAlartDialog();
                Log.d("Rating",String.valueOf(mRatingBar.getRating()));
                Toast.makeText(getApplicationContext(),"Rating"+mRatingBar.getRating(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void feedbackAlartDialog(){
        String feedback = "";
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        final EditText txtFeedback = new EditText(this);
        txtFeedback.setInputType(InputType.TYPE_CLASS_TEXT);
        txtFeedback.setHint("Please Enter Feedback Here");
        linearLayout.addView(txtFeedback);

        final AlertDialog.Builder feedbacBuilder = new AlertDialog.Builder(this);
        feedbacBuilder.setIcon(R.drawable.ic_account_box_black_24dp);
        feedbacBuilder.setTitle("Please Give Us Feedback");
        feedbacBuilder.setView(linearLayout);
        feedbacBuilder.setPositiveButton("Feedback", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // String rating = String.valueOf(mRatingBar.getRating());
                //String feedback = txtFeedback.getText().toString();
                //  FeedbackModel model = new FeedbackModel("11",rating,feedback);
                //  UserFeedack.add(model);
                Toast.makeText(getApplicationContext(),mRatingBar.getRating()+"|"+txtFeedback.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });
        feedbacBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        feedbacBuilder.show();

    }

    private void setUpRecycler(){
        feedbackRecyclerView = (RecyclerView)findViewById(R.id.feedbackRecyclerID);
        feedbackRecyclerView.setHasFixedSize(true);
        mFeedBackLayoutManager = new LinearLayoutManager(this);
        feedbackRecyclerView.setLayoutManager(mFeedBackLayoutManager);
        mFeedbackAdapter = new feedbackAdapter(initMenuItem.GetFeedback(),productdetailsActivit.this);
        feedbackRecyclerView.setAdapter(mFeedbackAdapter);
    }
    private void addtoCartUsingSql(){
          new Database(getBaseContext()).AddTocart(new OrderModel(
                        "1",
                        NameText.getText().toString(),
                        DescText.getText().toString(),
                        "2",
                        priceText.getText().toString()

                ));
    }
}



