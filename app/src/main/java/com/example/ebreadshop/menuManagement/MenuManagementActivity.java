package com.example.ebreadshop.menuManagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebreadshop.R;
import com.example.ebreadshop.user.custHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
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
    private LinearLayout linearLayout;

    private ProductAdapter productAdapter;

    //private Context context;

    private List<Product> list;

    /*
    public interface DataStatus {
        void DataIsLoaded(List<Product> products, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public DataStatus getDataStatus() {
        DataStatus dataStatus = null;
        return dataStatus;
    } 
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_management);

        Log.i("Lifecycle", "OnCreate() invoked");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        linearLayout = findViewById(R.id.food_row);

        list = new ArrayList<>();

        //final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //List<String> keys = new ArrayList<>();

                    for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        //keys.add(keyNode.getKey());

                        Product product = keyNode.getValue(Product.class);
                        list.add(product);
                    }

                    //final DataStatus dataStatus = getDataStatus();
                    //dataStatus.DataIsLoaded(list, keys);

                    //productAdapter = new ProductAdapter(list, keys);
                    productAdapter = new ProductAdapter(list);

                    productAdapter.setListener(new ProductAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(Product product) {
                            Intent intent = new Intent(MenuManagementActivity.this, CRUDFoodActivity.class);

                            // passing data
                            intent.putExtra("key", databaseReference.getKey());
                            intent.putExtra("name", product.getName());
                            intent.putExtra("unitPrice", product.getUnitPrice());
                            intent.putExtra("discount", product.getDiscount());
                            intent.putExtra("description", product.getDescription());
                            //imageview to show image
                            //image url for update image

                            startActivity(intent);
                        }
                    });

                    //recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }
        });

        /*
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuManagementActivity.this, CRUDFoodActivity.class);
                startActivity(intent);
            }
        });
        */
    }

    /*
    public void onAdminRowClick(View view) {
        Intent intent = new Intent(MenuManagementActivity.this, CRUDFoodActivity.class);
        startActivity(intent);
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signout) {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getApplicationContext(), custHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
