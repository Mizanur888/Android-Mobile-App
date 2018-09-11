package com.example.rahmanm2.ecomerssiteclassprojectfinal.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rahmanm2.ecomerssiteclassprojectfinal.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuyNowFragment extends android.support.v4.app.Fragment {


    public BuyNowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_now, container, false);
    }

}
