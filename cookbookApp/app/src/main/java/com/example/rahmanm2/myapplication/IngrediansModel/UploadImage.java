package com.example.rahmanm2.myapplication.IngrediansModel;

public class UploadImage{
    private String name;
    private String imageURI;

    public UploadImage(String name, String imageURI) {
        this.name = name;
        this.imageURI = imageURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
