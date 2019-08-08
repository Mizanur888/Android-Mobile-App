package com.example.rahmanm2.myapplication.IngrediansModel;

import java.io.Serializable;

public class HorizontalViewIngredians implements Serializable {

    private String itemsKeys;
    private String UserID;
    private String itemID;
    private String Title;
    private String ItemDescription;
    private String Image;
    private int int_Image;
    private String FoodType;
    private String timetoMakeFoods;
    private String CountryType;
    private String RecipeCatagory;
    private String Description;
    private String Ingredians;
    private String Direction;
    private menuItemID menuItemID;

    public HorizontalViewIngredians() {

    }
    public HorizontalViewIngredians(String userId, String menuItemID,String itemName, String times, String FoodType, String CountryType,
                                     String RecipeCatagory,String itemImage) {
        this.UserID = userId;
        this.itemID = menuItemID;
        this.Title = itemName;
        this.timetoMakeFoods = times;
        this.FoodType = FoodType;
        this.CountryType = CountryType;
        this.RecipeCatagory = RecipeCatagory;
        this.Image = itemImage;
    }
    public HorizontalViewIngredians(String userId, String menuItemID,String itemName, String times, String FoodType, String CountryType,
                                    String RecipeCatagory,String itemImage,String Description,
                                    String Ingredians, String direction) {
        this.UserID = userId;
        this.itemID = menuItemID;
        this.Title = itemName;
        this.timetoMakeFoods = times;
        this.FoodType = FoodType;
        this.CountryType = CountryType;
        this.RecipeCatagory = RecipeCatagory;
        this.Image = itemImage;
        this.Description = Description;
        this.Ingredians = Ingredians;
        this.Direction = direction;
    }
    public HorizontalViewIngredians(String userID, String title, String ItemDescription, String image, String foodType, String countryType, String Description,
                                    String Ingredians, String direction) {
        UserID = userID;
        Title = title;
        this.ItemDescription = ItemDescription;
        Image = image;
        FoodType = foodType;
        CountryType = countryType;
        this.Description = Description;
        this.Ingredians = Ingredians;
        Direction = direction;
    }

    public HorizontalViewIngredians(String userID, String title, String foodType, String countryType, String Description,
                                    String Ingredians, String direction,
                                    String image, int int_Image) {
        UserID = userID;
        Title = title;
        FoodType = foodType;
        CountryType = countryType;
        this.Description = Description;
        this.Ingredians = Ingredians;
        Direction = direction;
        Image = image;
        this.int_Image = int_Image;
    }

    public String getItemsKeys() {
        return itemsKeys;
    }

    public void setItemsKeys(String itemsKeys) {
        this.itemsKeys = itemsKeys;
    }

    public menuItemID getMenuItemID() {
        return menuItemID;
    }

    public void setMenuItemID(menuItemID menuItemID) {
        this.menuItemID = menuItemID;
    }

    public String getRecipeCatagory() {
        return RecipeCatagory;
    }

    public void setRecipeCatagory(String recipeCatagory) {
        RecipeCatagory = recipeCatagory;
    }

    public String getTimetoMakeFoods() {
        return timetoMakeFoods;
    }

    public void setTimetoMakeFoods(String timetoMakeFoods) {
        this.timetoMakeFoods = timetoMakeFoods;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getInt_Image() {
        return int_Image;
    }

    public void setInt_Image(int int_Image) {
        this.int_Image = int_Image;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public String getFoodType() {
        return FoodType;
    }

    public void setFoodType(String foodType) {
        FoodType = foodType;
    }

    public String getCountryType() {
        return CountryType;
    }

    public void setCountryType(String countryType) {
        CountryType = countryType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getIngredians() {
        return Ingredians;
    }

    public void setIngredians(String ingredians) {
        this.Ingredians = ingredians;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

}
