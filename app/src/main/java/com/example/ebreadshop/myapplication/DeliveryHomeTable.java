package com.example.ebreadshop.myapplication;

public class DeliveryHomeTable {

    private String OrderID;
    private String UserId;
    private String currentDate;
    private String Zipcode;
    private String city;
    private String lane;
    private String price;
    private String name;
    private String tp;
    private String tp2;
    private String time;
    private String date;

    public DeliveryHomeTable() {

    }

    public DeliveryHomeTable(String orderID, String userId, String currentDate, String zipcode, String city, String lane, String price, String name, String tp, String tp2, String time, String date) {
        this.OrderID = orderID;
        this.UserId = userId;
        this.currentDate = currentDate;
        this.Zipcode = zipcode;
        this.city = city;
        this.lane = lane;
        this.price = price;
        this.name = name;
        this.tp = tp;
        this.tp2 = tp2;
        this.time = time;
        this.date = date;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getTp2() {
        return tp2;
    }

    public void setTp2(String tp2) {
        this.tp2 = tp2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString(){
        return this.OrderID +" . "+ name+" - " + tp;
    }

    public String toString2(){
        return this.UserId +","+ OrderID ;
    }
}


