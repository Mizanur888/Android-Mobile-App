package com.example.rahmanm2.ecomers_final.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.rahmanm2.ecomers_final.SampleDataModel.OrderModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "EcomersDb.db";
    private static final int DB_VER = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    //CREATE TABLE `OrderDetails` ( `ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `ProductID` TEXT, `ProductName` TEXT, `ProductDesc` TEXT, `Quantity` INTEGER, `Productprice` TEXT )
    public List<OrderModel>getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qp = new SQLiteQueryBuilder();
        String []sqlSelect = {"ProductID","ProductName","ProductDesc","Quantity","Productprice"};
        String sqlTableName = "OrderDetails";
        qp.setTables(sqlTableName);
        Cursor c = qp.query(db,sqlSelect,null,null,null,null,null);
        final List<OrderModel>result = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                result.add(new OrderModel(c.getString(c.getColumnIndex("ProductID")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("ProductDesc")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Productprice"))));
            }while (c.moveToNext());

        }
        return result;
    }

    public void AddTocart(OrderModel model){
        SQLiteDatabase db = getWritableDatabase();
        String sqlInsert = String.format("INSERT INTO OrderDetails (ProductID,ProductName,ProductDesc,Quantity,Productprice)" +
                "VALUES('%s','%s','%s','%s','%s');",model.getProductID(),
                                                    model.getProductName(),
                                                    model.getProductprice(),
                                                    model.getQuantity(),
                                                    model.getProductprice());
        db.execSQL(sqlInsert);
    }
    public void cleanCart(OrderModel model){
        SQLiteDatabase db = getReadableDatabase();
        String sqlInsert =String.format("DELETE FROM OrderDetails");
        db.execSQL(sqlInsert);
    }
}
