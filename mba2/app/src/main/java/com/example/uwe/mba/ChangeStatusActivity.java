package com.example.uwe.mba;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeStatusActivity extends AppCompatActivity {

    Spinner spinPay, spinJob;
    TextView txtChangeFname, txtChangeLname, txtChangePhone, txtChangeEmail, txtChangeDate, txtChangeTime, txtChangeLoc, txtChangeDep, txtChangeFpay, txtChangeRemark;
    Button btnStatus, btnChangeBack;
    Booking booking;
    public Date newDate, newTime;
    boolean isInserted;
    DBHelper DH = new DBHelper(this);
    String pay, job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        spinPay = (Spinner)findViewById(R.id.spinPay);
        spinJob = (Spinner)findViewById(R.id.spinJob);

        Bundle b = this.getIntent().getExtras();
        booking =  (Booking) getIntent().getSerializableExtra("value");

        final String[] pay = {"waiting deposit", "deposit received", "full payment received"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pay);
        spinPay.setAdapter(lunchList);

        final String[] job = {"not confirm", "confirmed", "completed", "final invoice sent"};
        ArrayAdapter<String> jobStat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, job);
        spinJob.setAdapter(jobStat);

        txtChangeFname = (TextView)findViewById(R.id.txtChangeFname);
        txtChangeLname = (TextView)findViewById(R.id.txtChangeLname);
        txtChangePhone = (TextView)findViewById(R.id.txtChangePhone);
        txtChangeEmail = (TextView)findViewById(R.id.txtChangeEmail);
        txtChangeDate = (TextView)findViewById(R.id.txtChangeDate);
        txtChangeTime = (TextView)findViewById(R.id.txtChangeTime);
        txtChangeLoc = (TextView)findViewById(R.id.txtChangeLoc);
        txtChangeDep = (TextView)findViewById(R.id.txtChangeDep);
        txtChangeFpay = (TextView)findViewById(R.id.txtChangeFpay);
        txtChangeRemark = (TextView)findViewById(R.id.txtChangeRemark);
        btnStatus = (Button)findViewById(R.id.btnStatus);
        btnChangeBack = (Button)findViewById(R.id.btnChangeBack);

        String strCurrentDate = booking.getOdate();
        String strCrrentTime = booking.getOtime();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat timefro = new SimpleDateFormat("hhmm");
        try{
            newDate = format.parse(strCurrentDate);
            newTime = timefro.parse(strCrrentTime);
        } catch (ParseException e){
            e.printStackTrace();
        }
        format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(newDate);
        timefro = new SimpleDateFormat("hh:mm aa");
        String time = timefro.format(newTime);

        txtChangeFname.setText(booking.getFname().toString());
        txtChangeLname.setText(booking.getLname().toString());
        txtChangePhone.setText(booking.getPhone().toString());
        txtChangeEmail.setText(booking.getEmail().toString());
        txtChangeDate.setText(date.toString());
        txtChangeTime.setText(time.toString());
        txtChangeLoc.setText(booking.getLocation().toString());
        txtChangeDep.setText("$" + booking.getDeposit().toString());
        txtChangeFpay.setText("$" + booking.getFinalpay().toString());
        txtChangeRemark.setText(booking.getRemarks().toString());

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChangeDialogBox();
            }
        });

        btnChangeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBackDialogBox();
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

    public void createChangeDialogBox() {
        AlertDialog.Builder adExit= new AlertDialog.Builder(this);
        adExit.setTitle("Attention!");
        adExit.setMessage("Do you want to change status?");
        adExit.setCancelable(false);
        adExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODOAuto‐generated method stub
                change();
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

    public void change() {
        openDB();
        pay = spinPay.getSelectedItem().toString();
        job = spinJob.getSelectedItem().toString();
        try {
            isInserted = DH.UpdateOrderStatus(String.valueOf(booking.getOid()), pay, job);
        } catch (SQLiteException e){
            Log.d("DDBBBBBB", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (isInserted == true) {
            Toast.makeText(this, "This Orders status has change!!!!", Toast.LENGTH_LONG).show();
            Log.d("db Status", "Changed!!!!!!!!!!!!!!!!!:");
            Log.d("db Statussssssss", pay +"  "+ job);
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
