package com.example.rahmanm2.ecomers_final.SampleDataModel;

import java.io.Serializable;

public class MainMenuItem implements Serializable{
    private String Productid;
    private int Images;
    private String ProductName;
    private String ProductDescription;
    private String ProductPrice;
    private Owner  productOwner;
    private float maxbid;

    public MainMenuItem(){

    }


    public MainMenuItem(String productid, int images, String productName, String productDescription, String productPrice, Owner productOwner) {
        Productid = productid;
        Images = images;
        ProductName = productName;
        ProductDescription = productDescription;
        ProductPrice = productPrice;
        this.productOwner = productOwner;
    }

    public MainMenuItem(int images, String productName, String productDescription, String productPrice) {
        Images = images;
        ProductName = productName;
        ProductDescription = productDescription;
        ProductPrice = productPrice;
    }

    public float getMaxbid() {
        return maxbid;
    }

    public void setMaxbid(float maxbid) {
        this.maxbid = maxbid;
    }

    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public Owner getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(Owner productOwner) {
        this.productOwner = productOwner;
    }
}
