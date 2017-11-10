package com.example.uwe.mba;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageButton btnReply, btnExit, btnBooking;
    Button btnDel;
    DBHelper DH = new DBHelper(this);
    String type = "image/*";
    String filename = "@drawable/myPhoto.jpg";
    String mediaPath = Environment.getExternalStorageDirectory() + filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReply = (ImageButton)findViewById(R.id.btnReply);
        btnBooking = (ImageButton)findViewById(R.id.btnBooking);
        btnExit = (ImageButton)findViewById(R.id.btnExit);

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "You Clicked B1");
                Intent i=new Intent(MainActivity.this, replyForm.class);
                startActivity(i);
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "You Clicked B1");
                Intent i=new Intent(MainActivity.this, bookingList.class);
                startActivity(i);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

        btnDel = (Button)findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DH.deleteOrders();
                createInstagramIntent(type, mediaPath);
                //Intent i=new Intent(MainActivity.this, AndroidImageCapture.class);
                //startActivity(i);
            }
        });
    }
    private void createInstagramIntent(String type, String mediaPath){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }

}
