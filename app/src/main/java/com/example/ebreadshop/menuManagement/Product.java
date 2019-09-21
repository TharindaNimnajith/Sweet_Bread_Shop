package com.example.ebreadshop.menuManagement;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class Product {

    private static int n = 0;
    private int id;

    private String name;

    //private double unitPrice;
    private String unitPrice;

    //private double discount;
    private String discount;

    private String price;

    private String description;

    private String imgURL;

    private Uri uri;
    private Task<Uri> task;

    private String key;

    public Product() {
        //n = n + 1;
        //id = n;

        //uri = null;
        //task = null;

        //key = "";

        //name = "";

        //unitPrice = "";
        //discount = "";

        //price = "";

        //description = "";

        //imgURL = "";
    }

    /*
    public Product(String name, String unitPrice, String discount, String description, String imgURL) {
        //n = n + 1;
        //id = n;

        //uri = null;
        //task = null;

        //key = "";

        this.name = name;

        this.unitPrice = unitPrice;
        this.discount = discount;

        //price = unitPrice - discount;

        this.description = description;
        this.imgURL = imgURL;
    }
    */

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    /*
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    */

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
