package com.example.ebreadshop.menuManagement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.example.ebreadshop.user.custHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewFoodActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;

    private TextView tvTitle, price;
    private ImageView imageView;

    private Product product;

    private String url = "";
    private Uri filePath;

    private DatabaseReference databaseReference;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String key;
    private String name;
    private String unitPrice;
    private String discount;
    private String description;

    private Double uPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);

        Log.i("Lifecycle", "OnCreate() invoked");

        // get the widgets reference from XML layout
        final TextView tv = findViewById(R.id.unit_price);
        NumberPicker np = findViewById(R.id.qty_value_);

        // set TextView text color
        //tv.setTextColor(Color.parseColor("#ffd32b3b"));

        // populate NumberPicker values from minimum and maximum value range
        np.setMinValue(1);  // set the minimum value of NumberPicker
        np.setMaxValue(10);  // specify the maximum value/number of NumberPicker

        // gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        // set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // display the newly selected number from picker
                //tv.setText("Selected Number : " + newVal);
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        price = findViewById(R.id.price_value_);
        imageView = findViewById(R.id.food_image_);
        tvTitle = findViewById(R.id.title_);

        // data
        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        unitPrice = getIntent().getStringExtra("unitPrice");
        discount = getIntent().getStringExtra("discount");
        description = getIntent().getStringExtra("description");

        uPrice = Double.parseDouble(unitPrice) - Double.parseDouble(discount);

        product = new Product();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

        // view details
        tvTitle.setText(name);
        price.setText(uPrice.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_menu, menu);
        return true;
    }

    public void addToCart(View view) {
        //Intent intent = new Intent(ViewFoodActivity.this, FoodListActivity.class);
        Intent intent = new Intent(ViewFoodActivity.this, NewFoodListActivity.class);

        Context context = getApplicationContext();
        CharSequence text = "Item Added to the Shopping Cart";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        startActivity(intent);
        toast.show();
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
