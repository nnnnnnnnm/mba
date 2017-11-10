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
import android.widget.ImageButton;
import android.widget.Toast;

public class addBooking extends AppCompatActivity {

    DBHelper DH = null;
    ImageButton btnAdd, btnBack, btnReset;
    EditText txtAddFname, txtAddLname, txtAddPhone, txtAddEmail, txtAddDate, txtAddTime, txtAddLoc, txtAddDep, txtAddFpay, txtAddRemark;
    public boolean isInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        btnAdd = (ImageButton)findViewById(R.id.btnAdd);
        btnBack = (ImageButton)findViewById(R.id.btnViewBack);
        btnReset = (ImageButton)findViewById(R.id.btnViewDel);
        txtAddFname = (EditText)findViewById(R.id.txtAddFname);
        txtAddLname = (EditText)findViewById(R.id.txtAddLname);
        txtAddPhone = (EditText)findViewById(R.id.txtAddPhone);
        txtAddEmail = (EditText)findViewById(R.id.txtAddEmail);
        txtAddDate = (EditText)findViewById(R.id.txtAddDate);
        txtAddTime = (EditText)findViewById(R.id.txtAddTime);
        txtAddLoc = (EditText)findViewById(R.id.txtAddLoc);
        txtAddDep = (EditText)findViewById(R.id.txtAddDep);
        txtAddFpay = (EditText)findViewById(R.id.txtAddFpay);
        txtAddRemark = (EditText)findViewById(R.id.txtAddRemark);

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
                if (TextUtils.isEmpty(txtAddFname.getText().toString())) {
                    txtAddFname.setError("Client's first name is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddLname.getText().toString())) {
                    txtAddLname.setError("Client's last name is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddPhone.getText().toString())) {
                    txtAddPhone.setError("Client's phone is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddEmail.getText().toString())) {
                    txtAddEmail.setError("Client's email is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddDate.getText().toString())) {
                    txtAddDate.setError("Date is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddTime.getText().toString())) {
                    txtAddTime.setError("Time is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddLoc.getText().toString())) {
                    txtAddLoc.setError("Location is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddDep.getText().toString())) {
                    txtAddDep.setError("Deposit is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddFpay.getText().toString())) {
                    txtAddFpay.setError("Final pay is essential");
                    return;
                } else if (TextUtils.isEmpty(txtAddRemark.getText().toString())) {
                    txtAddRemark.setError("Remark is essential");
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
            isInserted = DH.insertOrder(txtAddFname.getText().toString(),
                    txtAddLname.getText().toString(),
                    txtAddPhone.getText().toString(),
                    txtAddEmail.getText().toString(),
                    txtAddDate.getText().toString(),
                    txtAddTime.getText().toString(),
                    txtAddLoc.getText().toString(),
                    " deposit received",
                    " confirmed",
                    txtAddDep.getText().toString(),
                    txtAddFpay.getText().toString(),
                    txtAddRemark.getText().toString());
        } catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (isInserted == true) {
            txtAddFname.setText("");
            txtAddLname.setText("");
            txtAddPhone.setText("");
            txtAddEmail.setText("");
            txtAddDate.setText("");
            txtAddTime.setText("");
            txtAddLoc.setText("");
            txtAddDep.setText("");
            txtAddFpay.setText("");
            txtAddRemark.setText("");
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
                txtAddFname.setText("");
                txtAddLname.setText("");
                txtAddPhone.setText("");
                txtAddEmail.setText("");
                txtAddDate.setText("");
                txtAddTime.setText("");
                txtAddLoc.setText("");
                txtAddDep.setText("");
                txtAddFpay.setText("");
                txtAddRemark.setText("");
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
