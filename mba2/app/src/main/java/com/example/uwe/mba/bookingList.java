package com.example.uwe.mba;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class bookingList extends AppCompatActivity {

    Button btnListBack, btnAddBooking;
    ListView listBooking;
    DBHelper DH = new DBHelper(this);
    MyBookingAdapter myCustomAdapter = null;
    ArrayList<Booking> cars = null;
    Booking cars2 = null;
    Spinner spinList;
    Cursor data;
    Booking oneData;
    int post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        spinList = (Spinner)findViewById(R.id.spinList);
        final String[] lunch = {"All", "Confirmed", "Completed", "Waiting"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(bookingList.this, android.R.layout.simple_spinner_dropdown_item, lunch);
        spinList.setAdapter(lunchList);

        openDB();

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
        cars = DH.getData();
        data = DH.getListContents();

        myCustomAdapter= new MyBookingAdapter(this,R.layout.list_item,cars);

        listBooking.setAdapter(myCustomAdapter);
        listBooking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oneData =(Booking) (parent.getItemAtPosition(position));
                post = oneData.getOid();
            }
        });
        registerForContextMenu(listBooking);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.layout_listbooking, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        openDB();
        switch (item.getItemId()) {
            case R.id.view:
                Intent intent = new Intent(this, BookingActivity.class);
                intent.putExtra("value", oneData);
                startActivity(intent);
                break;
        }
        return true;
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
