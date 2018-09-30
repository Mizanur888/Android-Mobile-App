package com.example.rahmanm2.ecomers_final.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rahmanm2.ecomers_final.Fragment.AuctionFragment;
import com.example.rahmanm2.ecomers_final.Fragment.BuyNowFragment;
import com.example.rahmanm2.ecomers_final.Fragment.FinanceFragment;


public class PageAdapter extends FragmentPagerAdapter {
    int itemNum;
    public PageAdapter(FragmentManager fm, int item){
        super(fm);
        this.itemNum = item;
    }

    @Override
    public Fragment getItem(int iteam) {
        switch (iteam){
            case 0:
                return new AuctionFragment();
            case 1:
                return new BuyNowFragment();
            case 2:
               return new FinanceFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return itemNum;
    }
}
