package com.example.rahmanm2.myapplication.UserLoginModel;

public class SignUp {
    private int ID;
    private String Name;
    private String Email;
    private String Password;
    private String ConfromPasswprd;
    private String CreatedAt;
    private String UpdatedAt;

    public SignUp() {
    }

    public SignUp(String name, String email, String password, String confromPasswprd, String createdAt) {
        Name = name;
        Email = email;
        Password = password;
        ConfromPasswprd = confromPasswprd;
        this.CreatedAt = createdAt;
    }
    public SignUp(String name, String password, String confromPasswprd) {
        this.Name = name;
        this.Password = password;
        this.ConfromPasswprd = confromPasswprd;
    }
    public SignUp(String name, String password) {
        Name = name;
        Password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfromPasswprd() {
        return ConfromPasswprd;
    }

    public void setConfromPasswprd(String confromPasswprd) {
        ConfromPasswprd = confromPasswprd;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

}
