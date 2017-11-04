package com.example.uwe.mba;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.Toast;

public class addBooking extends AppCompatActivity {

    DBHelper DH = null;
    Button btnAdd, btnBack;
    EditText etFname, etLname, etPhone, etEmail, etDate, etTime, etLoc, etDep, etFPay, etRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);
        etFname = (EditText)findViewById(R.id.etFname);
        etLname = (EditText)findViewById(R.id.etLname);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etDate = (EditText)findViewById(R.id.etDate);
        etTime = (EditText)findViewById(R.id.etTime);
        etLoc = (EditText)findViewById(R.id.etLoc);
        etDep = (EditText)findViewById(R.id.etDep);
        etFPay = (EditText)findViewById(R.id.etFPay);
        etRemark = (EditText)findViewById(R.id.etRemark);

        openDB();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDB();
                onBackPressed();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddDialogBox();
            }
        });

    }

    private void openDB() {
        DH = new DBHelper(this);
    }
    private void closeDB() {
        DH.close();
    }
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void add(){
        try {
            SQLiteDatabase db = DH.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("_fname", etFname.toString());
            values.put("_lname", etLname.toString());
            values.put("_phone", etPhone.toString());
            values.put("_email", etEmail.toString());
            values.put("_odate", etDate.toString());
            values.put("_otime", etTime.toString());
            values.put("_location", etLoc.toString());
            values.put("_paystatus", "deposit received");
            values.put("_jobstatus", "confirm");
            values.put("_deposit", etDep.toString());
            values.put("_total", etFPay.toString());
            values.put("_remake", etRemark.toString());
            db.insert("Booking", null, values);
        } catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "This Booking has added!!!!", Toast.LENGTH_LONG).show();
        Log.d("db", "Added!!!!!!!!!!!!!!!!!:");

        etFname.setText("");
        etLname.setText("");
        etPhone.setText("");
        etEmail.setText("");
        etDate.setText("");
        etTime.setText("");
        etLoc.setText("");
        etDep.setText("");
        etFPay.setText("");
        etRemark.setText("");

    }
    public void createAddDialogBox() {
        AlertDialog.Builder adExit= new AlertDialog.Builder(this);
        adExit.setTitle("Attention!");
        adExit.setMessage("Do you want to add?");
        adExit.setCancelable(false);
        adExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            // TODOAuto‐generated method stub
                add();
            }
        });
        adExit.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            // TODOAuto‐generated method stub
                dialog.cancel();
            }
        });
        adExit.create();
        adExit.show();
    }

}
