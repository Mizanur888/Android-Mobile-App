package com.example.comexamplerahmanm2myapplicationappdatamodel_libary.InterfaceModel;

import com.example.comexamplerahmanm2myapplicationappdatamodel_libary.SignUp;

public interface IValidationChecker {
    SignUp ValidateUserData(SignUp userData, String UserID);
}
