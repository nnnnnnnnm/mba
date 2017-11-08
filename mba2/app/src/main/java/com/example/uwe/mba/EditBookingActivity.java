package com.example.uwe.mba;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditBookingActivity extends AppCompatActivity {

    EditText txtEditFname, txtEditLname, txtEditPhone, txtEditEmail, txtEditDate, txtEditTime,txtEditLoc, txtEditDep, txtEditFpay, txtEditRemark;
    DBHelper DH = new DBHelper(this);
    public Booking booking;
    Button btnEditBack, btnEdit;
    public boolean isInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking);

        txtEditFname = (EditText)findViewById(R.id.txtEditFname);
        txtEditLname = (EditText)findViewById(R.id.txtEditLname);
        txtEditPhone = (EditText)findViewById(R.id.txtEditPhone);
        txtEditEmail = (EditText)findViewById(R.id.txtEditEmail);
        txtEditDate = (EditText)findViewById(R.id.txtEditDate);
        txtEditTime = (EditText)findViewById(R.id.txtEditTime);
        txtEditLoc = (EditText)findViewById(R.id.txtEditLoc);
        txtEditDep = (EditText)findViewById(R.id.txtEditDep);
        txtEditFpay = (EditText)findViewById(R.id.txtEditFpay);
        txtEditRemark = (EditText)findViewById(R.id.txtEditRemark);
        btnEditBack = (Button)findViewById(R.id.btnEditBack);
        btnEdit = (Button)findViewById(R.id.btnEdit);

        openDB();

        Bundle b = this.getIntent().getExtras();
        booking = (Booking) getIntent().getSerializableExtra("value");

        txtEditFname.setText(booking.getFname().toString());
        txtEditLname.setText(booking.getLname().toString());
        txtEditPhone.setText(booking.getPhone().toString());
        txtEditEmail.setText(booking.getEmail().toString());
        txtEditDate.setText(booking.getOdate().toString());
        txtEditTime.setText(booking.getOtime().toString());
        txtEditLoc.setText(booking.getLocation().toString());
        txtEditDep.setText(booking.getDeposit().toString());
        txtEditFpay.setText(booking.getFinalpay().toString());
        txtEditRemark.setText(booking.getRemarks().toString());

        btnEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBackDialogBox();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtEditFname.getText().toString())) {
                    txtEditFname.setError("Client's first name is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditLname.getText().toString())) {
                    txtEditLname.setError("Client's last name is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditPhone.getText().toString())) {
                    txtEditPhone.setError("Client's phone is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditEmail.getText().toString())) {
                    txtEditEmail.setError("Client's email is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditDate.getText().toString())) {
                    txtEditDate.setError("Date is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditTime.getText().toString())) {
                    txtEditTime.setError("Time is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditLoc.getText().toString())) {
                    txtEditLoc.setError("Location is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditDep.getText().toString())) {
                    txtEditDep.setError("Deposit is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditFpay.getText().toString())) {
                    txtEditFpay.setError("Final pay is essential");
                    return;
                } else if (TextUtils.isEmpty(txtEditRemark.getText().toString())) {
                    txtEditRemark.setError("Remark is essential");
                    return;
                } else {
                    createEditDialogBox();
                }
            }
        });
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

    public void createEditDialogBox() {
        AlertDialog.Builder adExit= new AlertDialog.Builder(this);
        adExit.setTitle("Attention!");
        adExit.setMessage("Do you want to edit?");
        adExit.setCancelable(false);
        adExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODOAuto‐generated method stub
                edit();
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

    public void edit(){
        openDB();
        try {
            isInserted = DH.UpdateOrder(String.valueOf(booking.getOid()),
                    txtEditFname.getText().toString(),
                    txtEditLname.getText().toString(),
                    txtEditPhone.getText().toString(),
                    txtEditEmail.getText().toString(),
                    txtEditDate.getText().toString(),
                    txtEditTime.getText().toString(),
                    txtEditLoc.getText().toString(),
                    txtEditDep.getText().toString(),
                    txtEditFpay.getText().toString(),
                    txtEditRemark.getText().toString());
        } catch (SQLiteException e){
            Log.d("DDBBBBBB", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (isInserted == true) {
            Toast.makeText(this, "This Orders has edited!!!!", Toast.LENGTH_LONG).show();
            Log.d("db", "Edited!!!!!!!!!!!!!!!!!:");
        } else {
            Log.d("EEEEEEEdit", "GGGGGGGGG");
        }
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
}