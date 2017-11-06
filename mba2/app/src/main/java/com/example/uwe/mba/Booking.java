package com.example.uwe.mba;

/**
 * Created by Hin on 5/11/2017.
 */

public class Booking {
    String fname, lname, phone, email, odate, otime,
            location, paymentstatus, jobstatus, deposit, finalpay, remarks;

    public Booking(String fname, String lname, String phone, String email,
                   String odate, String otime, String location, String paymentstatus,
                   String jobstatus, String deposit, String finalpay, String remarks) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.odate = odate;
        this.otime = otime;
        this.location = location;
        this.paymentstatus = paymentstatus;
        this.jobstatus = jobstatus;
        this.deposit = deposit;
        this.finalpay = finalpay;
        this.remarks = remarks;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public String getOtime() {
        return otime;
    }

    public void setOtime(String otime) {
        this.otime = otime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getFinalpay() {
        return finalpay;
    }

    public void setFinalpay(String finalpay) {
        this.finalpay = finalpay;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
