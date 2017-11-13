package com.example.uwe.mba;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


public class bookingList extends AppCompatActivity {

    ImageButton btnListBack, btnAddBooking;
    ListView listBooking;
    DBHelper DH = new DBHelper(this);
    MyBookingAdapter myCustomAdapter = null;
    MyBookingAdapter myAllCustomAdapter = null;
    ArrayList<Booking> cars = null;
    ArrayList<Booking> comfirm = null;
    Spinner spinList;
    Booking oneData;
    int post;
    String list, mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    public File file;
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    Uri imageUri;
    public Date newDate, newTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        spinList = (Spinner)findViewById(R.id.spinList);
        final String[] lunch = {"All", "Confirmed", "Completed", "Waiting"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(bookingList.this, android.R.layout.simple_spinner_dropdown_item, lunch);
        spinList.setAdapter(lunchList);

        openDB();

        btnListBack = (ImageButton)findViewById(R.id.btnListBack);
        btnListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAddBooking = (ImageButton)findViewById(R.id.btnAddBooking);
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

        spinList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list = spinList.getSelectedItem().toString();
                if(list=="All"){
                    Log.d("SSSSSSPINNER     ", list);
                    cars = DH.getData();
                    myCustomAdapter= new MyBookingAdapter(bookingList.this,R.layout.list_item,cars);
                    listBooking.setAdapter(myCustomAdapter);
                } else if(list=="Confirmed"){
                    Log.d("SSSSSSPINNER     ", list);
                    cars = DH.getComfirmData();
                    myCustomAdapter= new MyBookingAdapter(bookingList.this,R.layout.list_item,cars);
                    listBooking.setAdapter(myCustomAdapter);
                } else if(list=="Completed"){
                    Log.d("SSSSSSPINNER     ", list);
                    cars = DH.getCompleteData();
                    myCustomAdapter= new MyBookingAdapter(bookingList.this,R.layout.list_item,cars);
                    listBooking.setAdapter(myCustomAdapter);
                } else if(list=="Waiting"){
                    Log.d("SSSSSSPINNER     ", list);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            /*
            case R.id.photo:
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
                break;
                */
            case R.id.sms:
                String SMSUri = "sms:" + oneData.getPhone().toString() +
                        "?subject=MakeUp Service Reminder&body=Dear " +
                        oneData.getLname().toString() + " " + oneData.getFname().toString() +
                        ",\nI am your Wedding MakeUp Friend. Please Check for your Deposit " +
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
                String MailUri = "mailto:" + oneData.getEmail().toString() +
                        "?subject=MakeUp Service Reminder&body=Dear " +
                        oneData.getLname().toString() + " " + oneData.getFname().toString() +
                        ",\nI am your Wedding MakeUp Friend. Please Check for your Deposit or " +
                        "Final Payment Invoice.";
                Intent emailit = new Intent(Intent.ACTION_VIEW, Uri.parse(MailUri));
                startActivity(emailit);
                break;
            case R.id.receipt:
                String strCurrentDate = oneData.getOdate().toString();
                String strCrrentTime = oneData.getOtime().toString();
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

                String ReceiptUri = "mailto:" + oneData.getEmail().toString() +
                        "?subject=MakeUp Service Reminder&body=Dear " +
                        oneData.getLname().toString() + " " + oneData.getFname().toString() +
                        ",\nI am your Wedding MakeUp Friend." +
                        "\nWe have received your deposit." +
                        "\nThis is the receipt email of our company." +
                        "\nDeposit: " + oneData.getDeposit().toString() +
                        "\nDate: " + date +
                        "\nime: " + time +
                        "\nLocation: " + oneData.getLocation().toString() +
                        "\nIf you have any question about this receipt, simple reply to this email or contact our company." + "" +
                        "\nHotline: xxxx-xxxx";
                Intent receiptit = new Intent(Intent.ACTION_VIEW, Uri.parse(ReceiptUri));
                startActivity(receiptit);
                break;
            case R.id.invoice:
                String InvoiceUri = "mailto:" + oneData.getEmail().toString() +
                        "?subject=MakeUp Service Reminder&body=Dear " +
                        oneData.getLname().toString() + " " + oneData.getFname().toString() +
                        ",\nI am your Wedding MakeUp Friend." +
                        "\nWe have received your final pay." +
                        "\nThis is the final invoice email of our company." +
                        "\nFinal pay: " + oneData.getFinalpay().toString() +
                        "\nIf you have any question about this receipt, simple reply to this email or contact our company." + "" +
                        "\nHotline: xxxx-xxxx";
                Intent invoiceit = new Intent(Intent.ACTION_VIEW, Uri.parse(InvoiceUri));
                startActivity(invoiceit);
                break;
        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                //use imageUri here to access the image
                Bundle extras = data.getExtras();
                Log.e("URI",imageUri.toString());
                Bitmap bmp = (Bitmap) extras.get("data");
                // here you will get the image as bitmap
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
            }
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

    private File createImageFile() throws IOException {
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
