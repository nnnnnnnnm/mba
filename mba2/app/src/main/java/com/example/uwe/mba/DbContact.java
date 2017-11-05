package com.example.uwe.mba;

import android.provider.BaseColumns;

/**
 * Created by Hin on 6/11/2017.
 */

public class DbContact {
    public DbContact() {

    }
    public static abstract class DbEntry implements BaseColumns {
        public static final String orders = "orders";
        public static final String oid = "oid";
        public static final String fname = "fname";
        public static final String lname = "lname";
        public static final String phone = "phone";
        public static final String email = "email";
        public static final String odate = "odate";
        public static final String otime = "otime";
        public static final String location = "location";
        public static final String paymentstatus = "paymentstatus";
        public static final String jobstatus = "jobstatus";
        public static final String deposit = "deposit";
        public static final String finalpay = "finalpay";
        public static final String remarks = "remarks";

        public static final String photos = "photos";
        public static final String pid = "pid";
        public static final String photopath = "photopath";
        public static final String orderid = "orderid";



    }
}
