package com.example.uwe.mba;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class replyForm extends AppCompatActivity {

    Spinner spinPack;
    Button btnSmsBack, btnSms;
    EditText etPNum, etFName, etLName, etDate;
    String list, dep, fpay;
    public Date newDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_form);

        spinPack = (Spinner) findViewById(R.id.spinPack);

        final String[] lunch = {"Package A", "Package B", "Package C", "Package D", "Package E"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(replyForm.this, android.R.layout.simple_spinner_dropdown_item, lunch);
        spinPack.setAdapter(lunchList);

        btnSmsBack = (Button) findViewById(R.id.btnSmsBack);
        btnSmsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etDate = (EditText)findViewById(R.id.etDate);
        etFName = (EditText)findViewById(R.id.etFName);
        etLName = (EditText)findViewById(R.id.etLName);
        etPNum = (EditText)findViewById(R.id.etPNum);

        btnSms = (Button) findViewById(R.id.btnSms);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCurrentDate = etDate.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
                try{
                    newDate = format.parse(strCurrentDate);
                } catch (ParseException e){
                    e.printStackTrace();
                }

                format = new SimpleDateFormat("yyyy-MM-dd");
                final String date = format.format(newDate);

                String SMSUri = "sms:" + etPNum.getText().toString() +
                        "?subject=MakeUp Service Reminder&body=Dear " +
                        etLName.getText().toString() + " " + etFName.getText().toString() +
                        ",\nI am your Wedding MakeUp Friend. You have choose the " +
                        list + " and you can dsposit " +
                        "($" + dep + ")or " +
                        "Final Payment ($" + fpay + ")" +
                        ",\nAnd the date is " + date + ".";
                Intent smsit = new Intent(Intent.ACTION_VIEW, Uri.parse(SMSUri));
                startActivity(smsit);
            }
        });

        spinPack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list = spinPack.getSelectedItem().toString();
                if(list=="Package A"){
                    Log.d("SSSSSSPINNER     ", list);
                    dep = "1000";
                    fpay = "2000";
                } else if(list=="Package B"){
                    Log.d("SSSSSSPINNER     ", list);
                    dep = "2000";
                    fpay = "4000";
                } else if(list=="Package C"){
                    Log.d("SSSSSSPINNER     ", list);
                    dep = "3000";
                    fpay = "6000";
                } else if(list=="Package D"){
                    Log.d("SSSSSSPINNER     ", list);
                    dep = "4000";
                    fpay = "8000";
                } else if(list=="Package E") {
                    Log.d("SSSSSSPINNER     ", list);
                    dep = "5000";
                    fpay = "10000";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
