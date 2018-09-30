package com.example.rahmanm2.ecomers_final.App;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rahmanm2.ecomers_final.Adapter.PageAdapter;
import com.example.rahmanm2.ecomers_final.R;


public class FragmentActivity extends AppCompatActivity {
    //Tablayout and Tabitems
    TabLayout mTabLayout;
    PageAdapter mPageAdapter;
    TabItem mTabAuction;
    TabItem mTabBuyNow;
    TabItem mTabFinance;
    ViewPager mViewPager;

    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mToolbar = (Toolbar)findViewById(R.id.AuctionToolbarID);
        mToolbar.setTitle("Ecomers");
        setSupportActionBar(mToolbar);
        //init viewpager and fragment
        SetUpFragment();

    }
    private void SetUpFragment(){
        mTabLayout = (TabLayout)findViewById(R.id.tabLayoutID);
        mTabAuction = (TabItem)findViewById(R.id.AuctionID);
        mTabBuyNow = (TabItem)findViewById(R.id.BuyNowID);
        mTabFinance = (TabItem)findViewById(R.id.FinanceItemID);
        mViewPager = (ViewPager)findViewById(R.id.viewPagerID);
        mPageAdapter = new PageAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==1){
                    mToolbar.setBackgroundColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimaryDark));
                    mTabLayout.setBackgroundColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimaryDark));

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimaryDark));
                    }
                }
                else if(tab.getPosition()==2){
                    mToolbar.setBackgroundColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimaryDark));
                    mTabLayout.setBackgroundColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimaryDark));

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimaryDark));
                    }
                }
                else{
                    mToolbar.setBackgroundColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimary));
                    mTabLayout.setBackgroundColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimary));

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(FragmentActivity.this,R.color.colorPrimary));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }
    private void setUpToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.AuctionToolbarID);
        mToolbar.setTitle("Ecomers");
        setSupportActionBar(mToolbar);
    }
}
