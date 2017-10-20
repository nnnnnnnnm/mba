package com.example.uwe.mba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class replyForm extends AppCompatActivity {

    Spinner spinPack;
    Button btnSmsBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_form);

        spinPack = (Spinner)findViewById(R.id.spinPack);
        final String[] lunch = {"Package A", "Package B", "Package C", "Package D", "Package E"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(replyForm.this, android.R.layout.simple_spinner_dropdown_item, lunch);
        spinPack.setAdapter(lunchList);

        btnSmsBack = (Button)findViewById(R.id.btnSmsBack);
        btnSmsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
