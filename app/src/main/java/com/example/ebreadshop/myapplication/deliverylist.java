package com.example.ebreadshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;

public class deliverylist extends AppCompatActivity {

    Button bb, bb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_info);


        bb = findViewById(R.id.xxx);
        bb2 = findViewById(R.id.xx);


        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ShowUserDelivery.class);
                i.putExtra("key", "102");
                startActivity(i);

            }

        });

        bb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), deliveryHome.class);
                startActivity(i);
            }
        });
    }

}
