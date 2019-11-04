package com.example.ebreadshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShowUserDelivery extends AppCompatActivity {


    private static final String TAG = "ShowUserDelivery";
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_delivery);

        Bundle extra = getIntent().getExtras();
        final String UseID = extra.getString("key");


        databaseReference= FirebaseDatabase.getInstance().getReference("DeliveryHomeTable");

        listView=(ListView) findViewById(R.id.listview);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String ID = dataSnapshot.getKey();
                String value=dataSnapshot.getValue(DeliveryHomeTable.class).toString2();
                String key = value;
                String [] h = key.split(",");
                String part1 = h[0];
                //final String text = String.valueOf(part1);
                //add USERID TO HERE
                if (part1.equals(UseID)){
                    arrayList.add(ID +"-"+ value);
                    arrayAdapter.notifyDataSetChanged();

                }
                arrayAdapter.notifyDataSetChanged();




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"onItemClick: name: " +arrayList.get(position));
                String key = arrayList.get(position);
                String [] h = key.split("-");
                String part1 = h[0];

                Intent i = new Intent(getApplicationContext(), UserUpdateDelivery.class);
                i.putExtra("key",part1);
                startActivity(i);

                Toast.makeText(ShowUserDelivery.this,"YOU CLICK ON:"+part1,Toast.LENGTH_SHORT).show();

            }
        });


    }
}
