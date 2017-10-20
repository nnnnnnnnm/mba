package com.example.uwe.mba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class bookingList extends AppCompatActivity {

    Button btnListBack, btnAddBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        btnListBack = (Button)findViewById(R.id.btnListBack);
        btnListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAddBooking = (Button)findViewById(R.id.btnAddBooking);
        btnAddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "You Clicked B1");
                Intent i=new Intent(bookingList.this, addBooking.class);
                startActivity(i);
            }
        });

    }

}
