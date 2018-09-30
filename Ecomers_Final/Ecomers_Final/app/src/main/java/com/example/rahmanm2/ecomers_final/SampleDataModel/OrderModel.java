package com.example.rahmanm2.ecomers_final.SampleDataModel;

public class OrderModel {
    private String ProductID;
    private String ProductName;
    private String ProductDesc;
    private String Quantity;
    private String Productprice;
    private String Discount;

    public OrderModel(String productID, String productName, String productDesc, String productprice) {
        ProductID = productID;
        ProductName = productName;
        ProductDesc = productDesc;
        Productprice = productprice;
    }

    public OrderModel(String productID, String productName, String productDesc, String quantity, String productprice) {
        ProductID = productID;
        ProductName = productName;
        ProductDesc = productDesc;
        Quantity = quantity;
        Productprice = productprice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getProductprice() {
        return Productprice;
    }

    public void setProductprice(String productprice) {
        Productprice = productprice;
    }
}
