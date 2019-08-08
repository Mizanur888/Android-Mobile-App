package com.example.rahmanm2.myapplication.DataBase;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.comexamplerahmanm2myapplicationappdatamodel_libary.SignUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLoginModel {
    public static ProgressDialog mProgressDialog;
    static List<SignUp>mSignUp = new ArrayList<>();

    public static void InsertUser(final String url, final SignUp model, final Context context){
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please Wait ........");
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                Toast.makeText(context,"Sign Up Successful",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.hide();
                Log.d("Error",error.getMessage());
                Toast.makeText(context,"Sign Up Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parms = new HashMap<>();
                parms.put("Name",model.getName());
                parms.put("Email",model.getEmail());
                parms.put("Password",model.getPassword());
                return parms;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
