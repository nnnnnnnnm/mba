package com.example.uwe.mba;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.Toast;

public class addBooking extends AppCompatActivity {

    DBHelper DH = null;
    Button btnAdd, btnBack, btnReset;
    EditText etFname, etLname, etPhone, etEmail, etDate, etTime, etLoc, etDep, etFPay, etRemark;
    public boolean isInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnViewBack);
        btnReset = (Button)findViewById(R.id.btnViewDel);
        etFname = (EditText)findViewById(R.id.txtFname);
        etLname = (EditText)findViewById(R.id.txtLname);
        etPhone = (EditText)findViewById(R.id.txtPhone);
        etEmail = (EditText)findViewById(R.id.txtEmail);
        etDate = (EditText)findViewById(R.id.txtDate);
        etTime = (EditText)findViewById(R.id.etTime);
        etLoc = (EditText)findViewById(R.id.txtLoc);
        etDep = (EditText)findViewById(R.id.txtDep);
        etFPay = (EditText)findViewById(R.id.txtFpay);
        etRemark = (EditText)findViewById(R.id.txtRemark);

        openDB();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBackDialogBox();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createResetDialogBox();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etFname.getText().toString())) {
                    etFname.setError("Client's first name is essential");
                    return;
                } else if (TextUtils.isEmpty(etLname.getText().toString())) {
                    etLname.setError("Client's last name is essential");
                    return;
                } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    etPhone.setError("Client's phone is essential");
                    return;
                } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
                    etEmail.setError("Client's email is essential");
                    return;
                } else if (TextUtils.isEmpty(etDate.getText().toString())) {
                    etDate.setError("Date is essential");
                    return;
                } else if (TextUtils.isEmpty(etTime.getText().toString())) {
                    etTime.setError("Time is essential");
                    return;
                } else if (TextUtils.isEmpty(etLoc.getText().toString())) {
                    etLoc.setError("Location is essential");
                    return;
                } else if (TextUtils.isEmpty(etDep.getText().toString())) {
                    etDep.setError("Deposit is essential");
                    return;
                } else if (TextUtils.isEmpty(etFPay.getText().toString())) {
                    etFPay.setError("Final pay is essential");
                    return;
                } else if (TextUtils.isEmpty(etRemark.getText().toString())) {
                    etRemark.setError("Remark is essential");
                    return;
                } else {
                    createAddDialogBox();
                }
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
            isInserted = DH.insertOrder(etFname.getText().toString(),
                    etLname.getText().toString(),
                    etPhone.getText().toString(),
                    etEmail.getText().toString(),
                    etDate.getText().toString(),
                    etTime.getText().toString(),
                    etLoc.getText().toString(),
                    " deposit received ",
                    " confirm ",
                    etDep.getText().toString(),
                    etFPay.getText().toString(),
                    etRemark.getText().toString());
        } catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (isInserted == true) {
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
            Toast.makeText(this, "This Orders has added!!!!", Toast.LENGTH_LONG).show();
            Log.d("db", "Added!!!!!!!!!!!!!!!!!:");
        } else {
            Log.d("AAAAAAAAAAADDDDDDD", "GGGGGGGGG");
        }
    }

    public void createBackDialogBox() {
        AlertDialog.Builder adExit= new AlertDialog.Builder(this);
        adExit.setTitle("Attention!");
        adExit.setMessage("Do you want to exit?");
        adExit.setCancelable(false);
        adExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            // TODOAuto‐generated method stub
            closeDB();
            onBackPressed();
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

    public void createResetDialogBox() {
        AlertDialog.Builder adExit= new AlertDialog.Builder(this);
        adExit.setTitle("Attention!");
        adExit.setMessage("Do you want to reset?");
        adExit.setCancelable(false);
        adExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODOAuto‐generated method stub
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
