package com.example.rahmanm2.ecomers_final.SampleDataModel;

public class AuctionModel {
    private boolean isAuction;
    private boolean isFinance;
    private boolean isBuynow;
    private String  producdID;
    private String  productname;
    private String  productDesc;
    private String  productRating;
    private String  productPrice;
    private String  productDiscount;
    private String  productStartTime;
    private String  productEndTime;
    private String  productRelist;
    private String[]images;


    public AuctionModel(boolean isAuction, boolean isFinance, boolean isBuynow, String producdID, String productDesc, String productRating,
                        String productPrice, String productDiscount, String productStartTime, String productEndTime) {
        this.isAuction = isAuction;
        this.isFinance = isFinance;
        this.isBuynow = isBuynow;
        this.producdID = producdID;
        this.productDesc = productDesc;
        this.productRating = productRating;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productStartTime = productStartTime;
        this.productEndTime = productEndTime;
    }

    public AuctionModel(boolean isAuction, boolean isFinance, boolean isBuynow, String producdID,
                        String productname, String productDesc, String productRating, String productPrice,
                        String productDiscount, String productStartTime,
                        String productEndTime, String productRelist, String[] images) {
        this.isAuction = isAuction;
        this.isFinance = isFinance;
        this.isBuynow = isBuynow;
        this.producdID = producdID;
        this.productname = productname;
        this.productDesc = productDesc;
        this.productRating = productRating;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productStartTime = productStartTime;
        this.productEndTime = productEndTime;
        this.productRelist = productRelist;
        this.images = images;
    }

    public boolean isAuction() {
        return isAuction;
    }

    public void setAuction(boolean auction) {
        isAuction = auction;
    }

    public boolean isFinance() {
        return isFinance;
    }

    public void setFinance(boolean finance) {
        isFinance = finance;
    }

    public boolean isBuynow() {
        return isBuynow;
    }

    public void setBuynow(boolean buynow) {
        isBuynow = buynow;
    }

    public String getProducdID() {
        return producdID;
    }

    public void setProducdID(String producdID) {
        this.producdID = producdID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductRating() {
        return productRating;
    }

    public void setProductRating(String productRating) {
        this.productRating = productRating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductStartTime() {
        return productStartTime;
    }

    public void setProductStartTime(String productStartTime) {
        this.productStartTime = productStartTime;
    }

    public String getProductEndTime() {
        return productEndTime;
    }

    public void setProductEndTime(String productEndTime) {
        this.productEndTime = productEndTime;
    }

    public String getProductRelist() {
        return productRelist;
    }

    public void setProductRelist(String productRelist) {
        this.productRelist = productRelist;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
