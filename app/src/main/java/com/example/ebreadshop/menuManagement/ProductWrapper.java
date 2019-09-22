package com.example.ebreadshop.menuManagement;

public class ProductWrapper {
    private String key;
    private Product product;

    public ProductWrapper() {
        //
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
