package com.example.uwe.mba;

/**
 * Created by Dicky on 6/11/2017.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyBookingAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Booking> booking;
    public Date newDate, newTime;

    public MyBookingAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context= context;
        booking=objects;
    }
    private class ViewHolder
    {
        TextView bookingFname, bookingPhone, bookingEmail, bookingLocation, bookingOdate, bookingOtime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.bookingFname = (TextView) convertView.findViewById(R.id.bookingFname);
            holder.bookingEmail = (TextView) convertView.findViewById(R.id.bookingEmail);
            holder.bookingPhone=(TextView)convertView.findViewById(R.id.bookingPhone);
            holder.bookingLocation = (TextView) convertView.findViewById(R.id.bookingLocation);
            holder.bookingOdate = (TextView) convertView.findViewById(R.id.bookingOdate);
            holder.bookingOtime=(TextView)convertView.findViewById(R.id.bookingOtime);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Booking individualCar= booking.get(position);
        holder.bookingFname.setText("Name: " + individualCar.getFname()+" "+individualCar.getLname() + "");
        holder.bookingEmail.setText("Email: "+ individualCar.getEmail()+"");
        holder.bookingPhone.setText("Phone: "+individualCar.getPhone()+"");
        holder.bookingLocation.setText("Location: "+ individualCar.getLocation()+"");
        String strCurrentDate = individualCar.getOdate();
        String strCrrentTime = individualCar.getOtime();
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
        holder.bookingOdate.setText("Date: "+ date+"");
        holder.bookingOtime.setText("Time: "+ time +"");
        return convertView;
    }
}
