package com.example.uwe.mba;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hin on 21/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final static int _DBVersion = 1; //<-- 版本
    private final static String _DBName = "MBA.db";  //<-- db name
    private final static String _TableName = "Order"; //<-- table name
    private final static String _Name = "Photo"; //<-- table name

    public DBHelper(Context context) {
        super(context, _DBName, null, _DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "_fname TEXT, " +
                "_lname TEXT," +
                "_phone TEXT" +
                "_email TEXT, " +
                "_odate TEXT," +
                "_otime TEXT" +
                "_location TEXT, " +
                "_paystatus TEXT," +
                "_jobstatus TEXT" +
                "_deposit TEXT, " +
                "_total TEXT," +
                "_remake TEXT" +
                ");";
        db.execSQL(SQL);
        Log.d("Helper", "Create Order Table");

        final String SQL2 = "CREATE TABLE IF NOT EXISTS " + _Name + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "_path TEXT, " +
                "_oid TEXT," +
                ");";
        db.execSQL(SQL2);
        Log.d("Helper", "Create Photo Table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + _TableName;
        db.execSQL(SQL);
        final String SQL2 = "DROP TABLE " + _Name;
        db.execSQL(SQL2);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertOrder(String fname, String lname, String phone, String email, String odate, String otime, String location, String paystatus, String jobstatus, String deposit, String total, String remake){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fname", fname);
        contentValues.put("lname", lname);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("odate", odate);
        contentValues.put("otime", otime);
        contentValues.put("location", location);
        contentValues.put("paystatus", paystatus);
        contentValues.put("jobstatus", jobstatus);
        contentValues.put("deposit", deposit);
        contentValues.put("total", total);
        contentValues.put("remake", remake);
        long result = db.insert("Order", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean UpdateOrder(String orderId, String fname, String lname, String phone, String email, String odate, String otime, String location, String deposit, String total, String remake){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fname", fname);
        contentValues.put("lname", lname);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("odate", odate);
        contentValues.put("otime", otime);
        contentValues.put("location", location);
        contentValues.put("deposit", deposit);
        contentValues.put("total", total);
        contentValues.put("remake", remake);
        long result = db.update("Order", contentValues,
                "id =?",
                new String[]{String.valueOf(orderId)});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean UpdateOrderStatus(String orderId, String paystatus, String jobstatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("paystatus", paystatus);
        contentValues.put("jobstatus", jobstatus);
        long result = db.update("Order", contentValues,
                "id =?",
                new String[]{String.valueOf(orderId)});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteOrder(String orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Order",
                "id =?",
                new String[]{String.valueOf(orderId)});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

}
