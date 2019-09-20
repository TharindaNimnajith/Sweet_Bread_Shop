package com.example.ebreadshop.myapplication;

import android.os.Bundle;
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

public class Dview extends AppCompatActivity {

    EditText orderID, txtaddress, txtdistricts, txtname, txttp, txttp2, txttime, txtdate;
    deliveryDB dev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dview);

        Bundle extra = getIntent().getExtras();
        String maxid = extra.getString("maxid");
        int result = Integer.parseInt(maxid);

        txtaddress = findViewById(R.id.Daddress1);
        txtdistricts = findViewById(R.id.Daddress2);
        txtname = findViewById(R.id.Dname);
        txttp = findViewById(R.id.Dtp);
        txttp2 = findViewById(R.id.Dtp2);
        txttime = findViewById(R.id.Dtime);
        txtdate = findViewById(R.id.Ddate);
        orderID = findViewById(R.id.orderID);


        dev = new deliveryDB();



        DatabaseReference readdb = FirebaseDatabase.getInstance().getReference().child("deliveryDB").child(String.valueOf(result));
        readdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    orderID.setText(dataSnapshot.child("orderID").getValue().toString());
                    txtaddress.setText(dataSnapshot.child("address").getValue().toString());
                    txtdistricts.setText(dataSnapshot.child("applyDate").getValue().toString());
                    txtname.setText(dataSnapshot.child("name").getValue().toString());
                    txttp.setText(dataSnapshot.child("tp").getValue().toString());
                    txttp2.setText(dataSnapshot.child("tp2").getValue().toString());
                    txttime.setText(dataSnapshot.child("time").getValue().toString());
                    txtdate.setText(dataSnapshot.child("date").getValue().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Nothis to display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
