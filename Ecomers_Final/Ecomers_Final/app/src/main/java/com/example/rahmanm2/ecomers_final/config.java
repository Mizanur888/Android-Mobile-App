package com.example.rahmanm2.ecomers_final;
//connect php with Volley
public class config {
   // public static String ROOT_URL = "http://192.168.252.124/Android_php_project/DataModel/crud.php";
    public static String ROOT_URL = "http://192.168.0.4/Register/Database/OrderCrud.php?";
    public static String update_endpoint = "update&Fname=keroat&Lname=peras&Address=mansfield&Phone=9090&Email=k@yahoo.com";
    public static String Insert_endPoint = "insert&Fname=karla&Lname=per&Address=boston&Email=k@yahoo.com&Phone=90909099&Password=1234";
    public static String View_endPoint = ROOT_URL+"view";

//http://localhost/Register/Database/OrderCrud.php?operasi=insert&ProductID=22&ProductName=Cat&ProductDesc=verycat&Quantity=3&ProductPrice=9090



    public static final String PRODUCT_DETAILS = "PRODUCT_DETAILS";
}
