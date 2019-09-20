package com.example.ebreadshop.menuManagement;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class Product {

    //private static int n = 0;
    //private int id;

    private String name;

    //private double unitPrice;
    //private double discount;
    private String unitPrice;
    private String discount;

    private String description;
    private String imgURL;

    private Uri uri;
    private Task<Uri> task;


    public Product() {
        // default constructor

        //n = n + 1;
        //id = n;
    }


    public Task<Uri> getTask() {
        return task;
    }

    public void setTask(Task<Uri> task) {
        this.task = task;
    }


    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    */


    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }


    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }


    /*
    public Product(String name, double unitPrice, double discount, String description, String imgURL) {
        //n = n + 1;
        //id = n;

        this.name = name;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.description = description;
        this.imgURL = imgURL;
    }


    public static int getN() {
        return n;
    }

    public static void setN(int n) {
        Product.n = n;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    */
}
