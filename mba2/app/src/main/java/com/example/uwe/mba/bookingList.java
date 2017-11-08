package com.example.uwe.mba;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Switch;
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
    String list, mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        spinList = (Spinner)findViewById(R.id.spinList);
        final String[] lunch = {"All", "Confirmed", "Completed", "Waiting"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(bookingList.this, android.R.layout.simple_spinner_dropdown_item, lunch);
        spinList.setAdapter(lunchList);
/*
        spinList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list = spinList.getSelectedItem().toString();
                if(list=="All"){

                } else if(list=="Confirmed"){

                } else if(list=="Completed"){

                } else if(list=="Waiting"){

                }
            }
        });
*/
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
            case R.id.edit:
                intent = new Intent(this, EditBookingActivity.class);
                intent.putExtra("value", oneData);
                startActivity(intent);
                break;
            case R.id.status:
                intent = new Intent(this, ChangeStatusActivity.class);
                intent.putExtra("value", oneData);
                startActivity(intent);
                break;
            case R.id.delete:
                AlertDialog.Builder adExit= new AlertDialog.Builder(this);
                adExit.setTitle("Attention!");
                adExit.setMessage("Do you want to add?");
                adExit.setCancelable(false);
                adExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODOAuto‐generated method stub
                        openDB();
                        DH.deleteOrder(String.valueOf(oneData.getOid()));
                        Log.d("Delete      ", String.valueOf(oneData.getOid()));
                        System.out.println(String.valueOf(oneData.getOid()));
                        Toast.makeText(bookingList.this, "Booking has deleted !!!!!!!!!", Toast.LENGTH_LONG).show();
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
                break;
            case R.id.photo:
                /*Intent photoit = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    file = getFile();
                }catch  (IOException e) {
                    e.printStackTrace();
                }
                photoit.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(photoit, 1);
*/
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = getFile();
                    } catch (IOException ex) {

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.example.android.uwe",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
                break;
            case R.id.sms:
                String SMSUri = "sms:" + oneData.getPhone().toString() +
                        "?subject=MakeUp Service Reminder&body=Dear " +
                        oneData.getLname().toString() + " " + oneData.getFname().toString() +
                        ",\nI am your Wrdding MakeUp Friend. Please Check for your Deposit " +
                        "($" + oneData.getDeposit().toString() + ")or " +
                        "Final Payment ($" + oneData.getFinalpay().toString() + ").";
                Intent smsit = new Intent(Intent.ACTION_VIEW, Uri.parse(SMSUri));
                startActivity(smsit);
                break;
            case R.id.phone:
                String PhoneUri = "tel:" + oneData.getPhone().toString() + "";
                Intent phoneit = new Intent(Intent.ACTION_VIEW, Uri.parse(PhoneUri));
                startActivity(phoneit);
                break;
            case R.id.email:
                String MialUri = "mailto:" + oneData.getEmail().toString() +
                        "?subject=MakeUp Service Reminder&body=Dear " +
                        oneData.getLname().toString() + " " + oneData.getFname().toString() +
                        ",\nI am your Wrdding MakeUp Friend. Please Check for your Deposit or " +
                        "Final Payment Invoice.";
                Intent emailit = new Intent(Intent.ACTION_VIEW, Uri.parse(MialUri));
                startActivity(emailit);
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

    private File getFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
