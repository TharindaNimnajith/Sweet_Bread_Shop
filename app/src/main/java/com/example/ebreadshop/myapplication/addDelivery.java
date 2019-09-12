package com.example.ebreadshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;

public class addDelivery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);

    }

    public void delivery(View view) {
        Intent i = new Intent(getApplicationContext(), delivery.class);
        startActivity(i);
    }

    public void viewDelivery(View view) {
        Intent i = new Intent(getApplicationContext(), Dview.class);
        i.putExtra("maxid", "1");
        startActivity(i);
    }


}
