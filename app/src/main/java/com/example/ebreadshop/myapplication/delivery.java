package com.example.ebreadshop.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class delivery extends AppCompatActivity {


    EditText txtaddress, txtdistricts, txtname, txttp, txttp2, txttime, txtdate;
    Button submit;
    DatabaseReference db;
    deliveryDB dev;
    long maxid = 0;


    private void clear() {


        txtaddress.setText(" ");
        txtdistricts.setText(" ");
        txtname.setText(" ");
        txttp.setText(" ");
        txttp2.setText(" ");
        txttime.setText(" ");
        txtdate.setText(" ");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        txtaddress = findViewById(R.id.Daddress1);
        txtdistricts = findViewById(R.id.Daddress2);
        txtname = findViewById(R.id.Dname);
        txttp = findViewById(R.id.Dtp);
        txttp2 = findViewById(R.id.Dtp2);
        txttime = findViewById(R.id.Dtime);
        txtdate = findViewById(R.id.Ddate);

        submit = findViewById(R.id.Daddsubmit);
        dev = new deliveryDB();
        db = FirebaseDatabase.getInstance().getReference().child("deliveryDB");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    if (TextUtils.isEmpty(txtaddress.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please ADD your Address", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtdistricts.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please ADD your Districts", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtname.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please ADD your name", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txttp2.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please ADD your Telephone NO", Toast.LENGTH_SHORT).show();
//
                    else if (TextUtils.isEmpty(txttp.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please ADD your Telephone NO", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txttime.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Delivery Time", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtdate.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please ADD Delivery Date", Toast.LENGTH_SHORT).show();


                    dev.setAddress(txtaddress.getText().toString().trim());
                    dev.setDate(txtdate.getText().toString().trim());
                    dev.setApplyDate(txtdistricts.getText().toString().trim());
                    dev.setName(txtname.getText().toString().trim());

                    dev.setTp(Long.parseLong(txttp.getText().toString().trim()));
                    dev.setTp2(Long.parseLong(txttp2.getText().toString().trim()));

                    dev.setTime(txttime.getText().toString().trim());
                    dev.setDate(txtdate.getText().toString().trim());

                    //db.push().setValue(dev);

                    db.child(String.valueOf(maxid + 1)).setValue(dev);

                    Toast.makeText(getApplicationContext(), "your data is added", Toast.LENGTH_SHORT).show();
                    clear();


                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
