package com.example.rahmanm2.myapplication.IngrediansModel;

public class comments {
    private String UserID;
    private String MenuID;
    private String Date;
    private String userComments;
    private float userRating;

    public comments() {
    }

    public comments(String userID,String menuid, String date, String userComments, float userRating) {
        UserID = userID;
        MenuID = menuid;
        Date = date;
        this.userComments = userComments;
        this.userRating = userRating;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }
}
