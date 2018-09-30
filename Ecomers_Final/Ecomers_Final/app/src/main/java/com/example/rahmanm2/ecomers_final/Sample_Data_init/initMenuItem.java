package com.example.rahmanm2.ecomers_final.Sample_Data_init;

import com.example.rahmanm2.ecomers_final.R;
import com.example.rahmanm2.ecomers_final.SampleDataModel.FeedbackModel;
import com.example.rahmanm2.ecomers_final.SampleDataModel.MainMenuItem;

import java.util.ArrayList;
import java.util.List;

public class initMenuItem {
    public static List<MainMenuItem> mMainMenuItems = new ArrayList<>();
    public static List<FeedbackModel>FeedModel = new ArrayList<>();
    public static List<MainMenuItem>getMenuItem(){
        int []image = getImage();
        String[]name = getname();
        String[]desc = getDesc();
        String[]price = getPrices();

        for(int i = 0;i<image.length;i++){
            MainMenuItem item = new MainMenuItem();
            item.setImages(image[i]);
            item.setProductName(name[i]);
            item.setProductDescription(desc[i]);
            item.setProductPrice(price[i]);
            mMainMenuItems.add(item);
        }
        return mMainMenuItems;
    }
    public static List<FeedbackModel>GetFeedback(){
        FeedModel.add( new FeedbackModel("this is feed back 1"));
                FeedModel.add(   new FeedbackModel("this is secend Feedback Model"));
                return FeedModel;

    }
    private static int []getImage(){

        int []images = {
                R.drawable.airpods,R.drawable.eadphone,R.drawable.hobani,
                R.drawable.eadphone,R.drawable.lvive,R.drawable.olay
        };
        return images;
    }
    private static String[]getname(){
        String []name = {
                "Airpods","Headphone","Chobani","Iphone Red","Lvive Shampo","OLAY"
        };
        return name;
    }
    private static String[]getDesc(){
        String []desc = {
                "Air pods Red for comfortable use, with high quality sounds","Headphones Red for comfortable use, with high quality sounds, no noise",
                "Start your Morning with Healthy Chobani","Iphone Red for Every day Use",
                "Lvive beuatiful silky here every time after use","OLAY for beautiful Screen"
        };
        return desc;
    }
    private static String[]getPrices(){
        String[]price = {"15","220","3","500","12","15"};
        return price;
    }
}
