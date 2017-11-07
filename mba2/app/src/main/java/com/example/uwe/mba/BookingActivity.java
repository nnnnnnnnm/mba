package com.example.uwe.mba;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.uwe.mba.R.id.view;

public class BookingActivity extends AppCompatActivity {

    TextView txtFname, txtLname, txtPhone, txtEmail, txtDate, txtTime, txtLoc, txtDep, txtFpay, txtRemark, txtPayment, txtJob;
    Button btnViewBack, btnViewDel;
    DBHelper DH = new DBHelper(this);
    Booking booking;
    public Date newDate, newTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        txtFname = (TextView)findViewById(R.id.txtFname);
        txtLname = (TextView)findViewById(R.id.txtLname);
        txtPhone = (TextView)findViewById(R.id.txtPhone);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtTime = (TextView)findViewById(R.id.txtTime);
        txtLoc = (TextView)findViewById(R.id.txtLoc);
        txtDep = (TextView)findViewById(R.id.txtDep);
        txtFpay = (TextView)findViewById(R.id.txtFpay);
        txtRemark = (TextView)findViewById(R.id.txtRemark);
        txtPayment = (TextView)findViewById(R.id.txtPayment);
        txtJob = (TextView)findViewById(R.id.txtJob);
        btnViewBack = (Button)findViewById(R.id.btnViewBack);
        btnViewDel = (Button)findViewById(R.id.btnViewDel);

        openDB();

        Bundle b = this.getIntent().getExtras();
        booking =  (Booking) getIntent().getSerializableExtra("value");

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

        txtFname.setText(booking.getFname().toString());
        txtLname.setText(booking.getLname().toString());
        txtPhone.setText(booking.getPhone().toString());
        txtEmail.setText(booking.getEmail().toString());
        txtDate.setText(date.toString());
        txtTime.setText(time.toString());
        txtLoc.setText(booking.getLocation().toString());
        txtDep.setText("$" + booking.getDeposit().toString());
        txtFpay.setText("$" + booking.getFinalpay().toString());
        txtRemark.setText(booking.getRemarks().toString());
        txtPayment.setText(booking.getPaymentstatus().toString());
        txtJob.setText(booking.getJobstatus().toString());

        btnViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        System.out.println(String.valueOf(booking.getOid()));
        btnViewDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDeleteDialogBox();
            }
        });
    }
    public void createDeleteDialogBox() {
        AlertDialog.Builder adExit= new AlertDialog.Builder(this);
        adExit.setTitle("Attention!");
        adExit.setMessage("Do you want to add?");
        adExit.setCancelable(false);
        adExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODOAuto‐generated method stub
                openDB();
                DH.deleteOrder(String.valueOf(booking.getOid()));
                Log.d("Delete      ", String.valueOf(booking.getOid()));
                System.out.println(String.valueOf(booking.getOid()));
                Toast.makeText(BookingActivity.this, "Booking has deleted !!!!!!!!!", Toast.LENGTH_LONG).show();
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
