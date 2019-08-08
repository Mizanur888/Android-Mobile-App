package com.example.rahmanm2.myapplication.HelperModel;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Base64;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Validation {
    private static String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String AES = "AES";
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    public Validation(){

    }
    public static String getDateTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }
    public static void validate(TextInputEditText UserUid, TextInputEditText UserEmailid, TextInputEditText UserPasswordID, TextInputEditText UserConfPassword,
                            TextInputLayout layoutUID, TextInputLayout layoutEmailid, TextInputLayout layoutPasswordID,
                            TextInputLayout layoutConforPassword){
        //String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String userIDPatern = "^[a-zA-Z0-9]+$";
        if(!UserUid.getText().toString().trim().matches(userIDPatern)){
            layoutUID.setErrorEnabled(true);
            layoutUID.setError("Please Enter valied UserID EX: miz112 ");
        }
        else{
            layoutUID.setErrorEnabled(false);
        }
        if(!UserEmailid.getText().toString().trim().matches(pattern)){
            layoutEmailid.setErrorEnabled(true);
            layoutEmailid.setError("Please Enter Valied Email");
        }
        else{
            layoutEmailid.setErrorEnabled(false);
        }
        if(UserPasswordID.getText().toString().length()<=7){
            layoutPasswordID.setErrorEnabled(true);
            layoutPasswordID.setError("Please Enter Password Greater than 7");
        }
        else{
            layoutPasswordID.setErrorEnabled(false);
        }
        if(UserConfPassword.getText().toString().length()<=7){
            layoutConforPassword.setErrorEnabled(true);
            layoutConforPassword.setError("Please Enter Password Greater than 7");
        }
        else{
            layoutConforPassword.setErrorEnabled(false);
        }
    }

    public static void validateLogin(TextInputEditText UserUid ,TextInputEditText UserPasswordID,
                                TextInputLayout layoutUID, TextInputLayout layoutPasswordID){
        //String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String userIDPatern = "^[a-zA-Z0-9]+";
        if(!UserUid.getText().toString().trim().matches(userIDPatern)){
            layoutUID.setErrorEnabled(true);
            layoutUID.setError("Please Enter valied UserID EX: miz112 ");
        }
        else{
            layoutUID.setErrorEnabled(false);
        }
        if(UserPasswordID.getText().toString().length()<=8){
            layoutPasswordID.setErrorEnabled(true);
            layoutPasswordID.setError("Please Enter Password Greater than 7");
        }
        else{
            layoutPasswordID.setErrorEnabled(false);
        }
    }

    public static void validateLogin(TextInputEditText UserUid ,TextInputEditText UserPasswordID,TextInputEditText confPasw,
                                     TextInputLayout layoutUID, TextInputLayout layoutPasswordID,TextInputLayout confpasw){

        validateLogin(UserUid,UserPasswordID,layoutUID,layoutPasswordID);
        if(confPasw.getText().toString().length()<=8){
            confpasw.setErrorEnabled(true);
            confpasw.setError("Please Enter Password Greater than 7");
        }
        else{
            confpasw.setErrorEnabled(false);
        }
    }
    public String decrypt( String pass) throws Exception{
        SecretKeySpec keySpec = generateKey(pass);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,keySpec);
       // byte[]decodeValue = Base64.decode(pass,Base64.DEFAULT);
        byte[]decValue = c.doFinal();
        String decrytedValue = new String(decValue);
        return decrytedValue;
    }
    public String encrypt(String password)throws Exception{
        SecretKeySpec keyPas = generateKey(password);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,keyPas);
        byte[]encval = cipher.doFinal();
        String encryptedValue = Base64.encodeToString(encval,Base64.DEFAULT);
        return encryptedValue;
    }
    private SecretKeySpec generateKey(String pasw)throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[]bytes = pasw.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[]key = digest.digest();
        SecretKeySpec keySpec = new SecretKeySpec(key,"AES");
        return keySpec;
    }

}
