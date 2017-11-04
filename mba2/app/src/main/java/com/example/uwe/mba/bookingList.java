package com.example.uwe.mba;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;


public class bookingList extends AppCompatActivity {

    Button btnListBack, btnAddBooking;
    ListView listBooking;
    private DBHelper DH = null;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayAdapter<String> lvItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        retrieveRecords();

        openDB();
        Log.d("db", "OOOOOOOOOOOOPEN ");

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

        listBooking = (ListView)findViewById(R.id.listBooking);

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
