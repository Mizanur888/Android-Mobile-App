package com.example.rahmanm2.myapplication.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rahmanm2.myapplication.App.LoginActivity;

public class addFavorite extends SQLiteOpenHelper {
    public static final int Datavase_Version = 9;
    public static final String Database_Name = "favorite";
    public static final String Table_Name = "addfavorite";
    public static final String UserID = "userid";
    public static final String column = "favoriteItems";
    SQLiteDatabase db;
    public addFavorite(Context context) {
        super(context, Database_Name, null, Datavase_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String query = "create table if not exists " + Table_Name +"("+column2+" varchar(50))";
       // String query = "create table if not exists " + Table_Name +"("+column+"TEXT UNSIGNED NOT NULL PRIMARY KEY"+")"+";";
        String query = "create table if not exists " + Table_Name +"("+UserID+" varchar(50)"+", "+column+" varchar(50))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists "+Table_Name+ ";");
        onCreate(db);
    }

    public boolean AddFavorite(String userID, String favorite){

        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserID, userID);
        contentValues.put(column, favorite);
        long result = db.insert(Table_Name, null, contentValues);
        if (result == -1) {
            return false; } else return true;


    }
    public void RemoveFavorite(String userID, String favorite){
        db = getWritableDatabase();
        if(checkForUserID(userID)==true) {

            String query = String.format("delete from " + Table_Name + " WHERE " + column + " ='%s';", favorite);
            db.execSQL(query);
        }
    }
    private boolean checkForUserID(String uid){
        boolean userid = true;
        db = getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from "+Table_Name+" WHERE "+UserID+" LIKE '"+uid+"'",null);
        //Cursor cursor = db.rawQuery("Select * from "+Table_Name+" WHERE "+column+" LIKE '"+id+"'",null);
        if(cursor!=null && cursor.getCount()>=0){
           return userid = true;
        }
        else{
            userid = false;
        }

        return userid;
    }
    public boolean isFavorite(String userID, String favorite){
        db = getReadableDatabase();
        String query = String.format("SELECT * FROM "+Table_Name+" WHERE "+UserID+" ='"+userID+"'"+" and "+column+" ='"+favorite+"';");
        Cursor cursor = db.rawQuery(query,null);
        if( cursor.getCount()<= 0){
            cursor.close();
            return false;
        }
        else{
            cursor.close();
            return true;
        }
    }
    public Cursor view(String id){
        Cursor cursor= getReadableDatabase().rawQuery("Select * from "+Table_Name+" WHERE "+UserID+" LIKE '"+id+"'",null);
        //Cursor cursor = db.rawQuery("Select * from "+Table_Name+" WHERE "+column+" LIKE '"+id+"'",null);
        return cursor;
    }

    public int getTotalfavorite(){
        int count = 0;
        db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM "+Table_Name);
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                count+=cursor.getCount();
            }while (cursor.moveToNext());
        }
        return count;
    }
}
