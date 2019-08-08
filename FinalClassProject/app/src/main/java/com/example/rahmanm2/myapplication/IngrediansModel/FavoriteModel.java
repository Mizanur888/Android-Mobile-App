package com.example.rahmanm2.myapplication.IngrediansModel;

public class FavoriteModel {
    private String userID;
    private String menuID;

    public FavoriteModel(String userID, String menuID) {
        this.userID = userID;
        this.menuID = menuID;
    }

    public FavoriteModel() {
    }

    public FavoriteModel(String menuID) {
        this.menuID = menuID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }
}
