package com.example.ebreadshop.menuManagement;

public class Product {
    // private static int n = 0;
    // private int id;

    private String name;
    private int unitPrice;
    private int discount;
    private String description;

    // image

    public Product() {
        // default constructor

        //n = n + 1;
        //id = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // image?

    /*
    public Product(String name, double unitPrice, double discount, String description) {
        n = n + 1;
        id = n;

        this.name = name;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.description = description;

        // image?
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
