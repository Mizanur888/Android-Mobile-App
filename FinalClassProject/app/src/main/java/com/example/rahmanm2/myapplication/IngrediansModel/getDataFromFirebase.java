package com.example.rahmanm2.myapplication.IngrediansModel;

import java.util.List;

public interface getDataFromFirebase {
    void connector(String url);
    List<HorizontalViewIngredians>getAllIngredians();
    List<String>LoadSuggest(String listitemID);
    List<HorizontalViewIngredians>LoadProductList(String listitemID);
    List<HorizontalViewIngredians>StartSearch(CharSequence text);

}
