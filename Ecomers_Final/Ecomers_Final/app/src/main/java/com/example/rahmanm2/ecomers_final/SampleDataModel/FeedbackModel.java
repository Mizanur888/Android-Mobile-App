package com.example.rahmanm2.ecomers_final.SampleDataModel;

public class FeedbackModel {
    private String productID;
    private String Rating;
    private String Feedback;

    public FeedbackModel(){

    }
    public FeedbackModel(String feedback){
        this.Feedback = feedback;
    }
    public FeedbackModel(String rating, String feedback) {
        Rating = rating;
        Feedback = feedback;
    }

    public FeedbackModel(String productID, String rating, String feedback) {
        this.productID = productID;
        Rating = rating;
        Feedback = feedback;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }
}
