package com.example.ebreadshop.menuManagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebreadshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuManagementActivity extends AppCompatActivity {

    /*
    //ArrayList<String> myArrayList = new ArrayList<>();
    ArrayList<Product> myArrayList = new ArrayList<>();
    ListView myListView;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_management);

        Log.i("Lifecycle", "OnCreate() invoked");



        //final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArrayList);
        ProductAdapter productAdapter = new ProductAdapter(myArrayList);

        myListView = findViewById(R.id.listView);

        //myListView.setAdapter(myArrayAdapter);
        myListView.setAdapter((ListAdapter) productAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Product myChildValues = dataSnapshot.getValue(Product.class);
                myArrayList.add(myChildValues);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }
        });
    }
    */

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_management);

        Log.i("Lifecycle", "OnCreate() invoked");


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        Product product = npsnapshot.getValue(Product.class);
                        //= npsnapshot.getKey();
                        list.add(product);
                    }

                    productAdapter = new ProductAdapter(list);
                    recyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public void add(View view) {
        Intent intent = new Intent(MenuManagementActivity.this, AddFoodItemActivity.class);
        startActivity(intent);
    }


    /*
    public void crud(View view) {
        Intent intent = new Intent(MenuManagementActivity.this, CRUDFoodActivity.class);
        startActivity(intent);
    }
    */


    @Override
    protected void onStart() {
        super.onStart();

        Log.i("Lifecycle", "onStart() invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("Lifecycle", "onRestart() invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("Lifecycle", "onResume() invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("Lifecycle", "onPause() invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("Lifecycle", "onStop() invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("Lifecycle", "onDestroy() invoked");
    }
}
