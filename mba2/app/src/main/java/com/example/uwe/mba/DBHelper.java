package com.example.uwe.mba;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Hin on 21/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_THABLE_ORDERS =
            "CREATE TABLE " + DbContact.DbEntry.orders + " (" +
                    DbContact.DbEntry.oid + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbContact.DbEntry.fname + " TEXT, " +
                    DbContact.DbEntry.lname + " TEXT, " +
                    DbContact.DbEntry.phone + " TEXT, " +
                    DbContact.DbEntry.email + " TEXT, " +
                    DbContact.DbEntry.odate + " TEXT, " +
                    DbContact.DbEntry.otime + " TEXT, " +
                    DbContact.DbEntry.location + " TEXT, " +
                    DbContact.DbEntry.paymentstatus + " TEXT, " +
                    DbContact.DbEntry.jobstatus + " TEXT, " +
                    DbContact.DbEntry.deposit + " TEXT, " +
                    DbContact.DbEntry.finalpay + " TEXT, " +
                    DbContact.DbEntry.remarks + " TEXT)";

    private static final String SQL_DELETE_THABLE_ORDERS =
            "DROP TABLE " + DbContact.DbEntry.orders;

    private static final String SQL_CREATE_THABLE_PHOTOS =
            "CREATE TABLE " + DbContact.DbEntry.photos + " (" +
                    DbContact.DbEntry.pid + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbContact.DbEntry.photopath + " TEXT, " +
                    DbContact.DbEntry.orderid + " TEXT)";

    private static final String SQL_DELETE_THABLE_PHOTOS =
            "DROP TABLE " + DbContact.DbEntry.photos;

    private final static int DATABASE_VERSION = 1; //<-- 版本
    private final static String DATABASE_NAME = "MBADb.db";  //<-- db name

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("HELPER", "DB HELPER CALLED");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_THABLE_ORDERS);
        Log.d("Helper", SQL_CREATE_THABLE_ORDERS);
        db.execSQL(SQL_CREATE_THABLE_PHOTOS);
        Log.d("Helper", SQL_CREATE_THABLE_PHOTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_THABLE_ORDERS);
        Log.d("Helper", SQL_DELETE_THABLE_ORDERS);
        db.execSQL(SQL_DELETE_THABLE_PHOTOS);
        Log.d("Helper", SQL_DELETE_THABLE_PHOTOS);
        onCreate(db);
    }

    public void deleteOrders(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_THABLE_ORDERS);
        Log.d("Helper", SQL_DELETE_THABLE_ORDERS);
        db.execSQL(SQL_DELETE_THABLE_PHOTOS);
        Log.d("Helper", SQL_DELETE_THABLE_PHOTOS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertOrder(String fname, String lname, String phone, String email, String odate, String otime,
                               String location, String paymentstatus, String jobstatus, String deposit, String finalpay, String remarks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContact.DbEntry.fname, fname);
        contentValues.put(DbContact.DbEntry.lname, lname);
        contentValues.put(DbContact.DbEntry.phone, phone);
        contentValues.put(DbContact.DbEntry.email, email);
        contentValues.put(DbContact.DbEntry.odate, odate);
        contentValues.put(DbContact.DbEntry.otime, otime);
        contentValues.put(DbContact.DbEntry.location, location);
        contentValues.put(DbContact.DbEntry.paymentstatus, paymentstatus);
        contentValues.put(DbContact.DbEntry.jobstatus, jobstatus);
        contentValues.put(DbContact.DbEntry.deposit, deposit);
        contentValues.put(DbContact.DbEntry.finalpay, finalpay);
        contentValues.put(DbContact.DbEntry.remarks, remarks);
        long result = db.insert(DbContact.DbEntry.orders, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean UpdateOrder(String orderId, String fname, String lname, String phone, String email, String odate, String otime, String location, String deposit, String finalpay, String remarks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContact.DbEntry.fname, fname);
        contentValues.put(DbContact.DbEntry.lname, lname);
        contentValues.put(DbContact.DbEntry.phone, phone);
        contentValues.put(DbContact.DbEntry.email, email);
        contentValues.put(DbContact.DbEntry.odate, odate);
        contentValues.put(DbContact.DbEntry.otime, otime);
        contentValues.put(DbContact.DbEntry.location, location);
        contentValues.put(DbContact.DbEntry.deposit, deposit);
        contentValues.put(DbContact.DbEntry.finalpay, finalpay);
        contentValues.put(DbContact.DbEntry.remarks, remarks);
        long result = db.update(DbContact.DbEntry.orders, contentValues,
                "id =?",
                new String[]{String.valueOf(orderId)});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean UpdateOrderStatus(String orderId, String paymentstatus, String jobstatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContact.DbEntry.paymentstatus, paymentstatus);
        contentValues.put(DbContact.DbEntry.jobstatus, jobstatus);
        long result = db.update(DbContact.DbEntry.orders, contentValues,
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
        long result = db.delete(DbContact.DbEntry.orders,
                DbContact.DbEntry.oid + "=?",
                new String[]{String.valueOf(orderId)});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + DbContact.DbEntry.orders, null);
        Log.d("dbbbbbbbbbbbbbb", data.toString());
        return data;
    }

    public ArrayList<Booking> getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Booking> booking = new ArrayList<Booking>();
        Cursor result = db.rawQuery("SELECT * FROM " + DbContact.DbEntry.orders, null);

        while(result.moveToNext()){
            booking.add( new Booking(result.getInt(0),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getString(11),
                    result.getString(12)));
        }
        return booking;
    }

    public Cursor getEdit(String oid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + DbContact.DbEntry.orders + " WHERE oid=" + oid, null);
        Log.d("SSSSSSSSreached", data.toString());
        return data;
    }
}
