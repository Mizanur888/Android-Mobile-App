package com.example.rahmanm2.myapplication.IngrediansModel;

import com.example.rahmanm2.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class initmainView {

    //private ArrayList<HorizontalViewIngredians> Ingredians;
    public static List<HorizontalViewIngredians>getRecipesView(){
        List<HorizontalViewIngredians> Ingredians = new ArrayList<>();

        int []image =images();
        String[]title = titels();
        String[]desc = descripns();
        String[]meetfishs = meetfish();
        String[]vegitables = vegitable();
        String[]AmountOfEachss = AmountOfEachs();
        String[]FoodTypes = FoodType();
        String []id = menuItemID();
        String []time = timtomakeFoods();
        for(int i = 0;i<image.length;i++){
            HorizontalViewIngredians items = new HorizontalViewIngredians();
            items.setItemID(id[i]);
            items.setInt_Image(image[i]);
            items.setTitle(title[i]);
            items.setItemDescription(desc[i]);
            items.setIngredians(meetfishs[i]);
            items.setDescription(vegitables[i]);
            items.setDirection(AmountOfEachss[i]);
            items.setFoodType(FoodTypes[i]);
            items.setTimetoMakeFoods(time[i]);
            Ingredians.add(items);
        }


        return Ingredians;
    }

    public static String []menuItemID(){
        String[]ids = {
                "001","002","003","004","005","006"
        };
        return ids;
    }
    private static String[] timtomakeFoods(){
        String[]times = {
                "23","10","56","33","1.45","45"
        };
        return times;
    }
    private static String[]FoodType(){
        String []type = {
                "Spicy","sweet","not sweet","very sweet","hot","laizy"
        };
        return type;
    }
    private static int []images(){
        int []image = {

                R.drawable.a,R.drawable.cc,R.drawable.nodols,
                R.drawable.pizza,R.drawable.maistas,R.drawable.a
        };
        return image;
    }

    private static String[]titels(){
        String []title = {
                "Burger","Chicken","Pasta","Pizza","Burger","Burger"
        };
        return title;
    }
    private static String[]descripns(){
        String[]desc = {
                "beautiful Burger 1","beautiful Burger 2","beautiful Burger 3",
                "Burger 4","Burger 5",
                "Burger 6"
        };
        return desc;
    }
    private static String[]meetfish(){
        String[]meetFish = {
                "Fish","Beef","chicken","Egg","chicken","Egg"
        };
        return meetFish;
    }

    private static String[]vegitable(){
        String []vegitables = {
                "tomato","colyflower","brokoly","egg plant","potato","salad"
        };
        return vegitables;
    }
    private static String[]AmountOfEachs(){
        String []AmountOfEach = {
                "1pound brokoly","2 pic potato","3 Egg","Fish","3 Egg","Fish"
        };
        return AmountOfEach;
    }
}
