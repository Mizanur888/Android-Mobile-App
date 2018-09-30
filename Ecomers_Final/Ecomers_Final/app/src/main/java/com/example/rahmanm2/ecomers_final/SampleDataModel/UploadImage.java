package com.example.rahmanm2.ecomers_final.SampleDataModel;

public class UploadImage {
  private  String name;
  private String ImaheUri;

    public UploadImage() {
    }

    public UploadImage(String name, String imaheUri) {
        if(name.trim().equals("")){
            name = "No Name";
        }
        this.name = name;
        ImaheUri = imaheUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImaheUri() {
        return ImaheUri;
    }

    public void setImaheUri(String imaheUri) {
        ImaheUri = imaheUri;
    }
}
