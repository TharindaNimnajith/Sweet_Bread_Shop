package com.example.ebreadshop.menuManagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

public class NewFoodListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout linearLayout;

    private NewProductAdapter newProductAdapter;

    //private Context context;

    private List<ProductWrapper> list;

    private String s;

    private EditText search;

    /*
    public DataStatus getDataStatus() {
        DataStatus dataStatus = null;
        return dataStatus;
    }

    public interface DataStatus {
        void DataIsLoaded(List<Product> products, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food_list);

        Log.i("Lifecycle", "OnCreate() invoked");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        linearLayout = findViewById(R.id.new_food_row);

        list = new ArrayList<>();

        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        //final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //List<String> keys = new ArrayList<>();

                    for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        //keys.add(keyNode.getKey());

                        s = keyNode.getKey();

                        Product product = keyNode.getValue(Product.class);

                        ProductWrapper productWrapper = new ProductWrapper();
                        productWrapper.setKey(s);
                        productWrapper.setProduct(product);

                        list.add(productWrapper);
                    }

                    //final DataStatus dataStatus = getDataStatus();
                    //dataStatus.DataIsLoaded(list, keys);

                    //newProductAdapter = new NewProductAdapter(list, keys);
                    newProductAdapter = new NewProductAdapter(list);

                    newProductAdapter.setListener(new NewProductAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(ProductWrapper product) {
                            Intent intent = new Intent(NewFoodListActivity.this, ViewFoodActivity.class);

                            //passing data
                            intent.putExtra("s", product.getKey());
                            intent.putExtra("key", databaseReference.getKey());
                            intent.putExtra("name", product.getProduct().getName());
                            intent.putExtra("unitPrice", product.getProduct().getUnitPrice());
                            intent.putExtra("discount", product.getProduct().getDiscount());
                            intent.putExtra("description", product.getProduct().getDescription());
                            //imageview to show image
                            //image url for update image

                            startActivity(intent);
                        }
                    });

                    //recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(newProductAdapter);
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
                Intent intent = new Intent(NewFoodListActivity.this, ViewFoodActivity.class);
                startActivity(intent);
            }
        });
        */
    }

    /*
    public void onRowClick(View view) {
        Intent intent = new Intent(NewFoodListActivity.this, ViewFoodActivity.class);
        startActivity(intent);
    }
    */

    private void filter(String text) {
        ArrayList<ProductWrapper> filteredList = new ArrayList<>();

        for (ProductWrapper productWrapper : list) {
            if (productWrapper.getProduct().getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(productWrapper);
            }
        }

        newProductAdapter.filterList(filteredList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_menu, menu);
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
