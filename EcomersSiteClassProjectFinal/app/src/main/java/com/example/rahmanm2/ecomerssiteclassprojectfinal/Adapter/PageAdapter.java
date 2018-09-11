package com.example.rahmanm2.ecomerssiteclassprojectfinal.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rahmanm2.ecomerssiteclassprojectfinal.Fragment.AuctionFragment;
import com.example.rahmanm2.ecomerssiteclassprojectfinal.Fragment.BuyNowFragment;
import com.example.rahmanm2.ecomerssiteclassprojectfinal.Fragment.FinanceFragment;

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
