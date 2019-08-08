package com.example.rahmanm2.myapplication.IngrediansModel;

public class menuItemID {
    String menuID;
    String recipeName;


    public menuItemID() {
    }

    public menuItemID(String menuID, String recipeName) {
        this.menuID = menuID;
        this.recipeName = recipeName;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @Override
    public String toString() {
        return (recipeName);
    }
}
