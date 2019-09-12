package com.example.ebreadshop.menuManagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFoodItemActivity extends AppCompatActivity {

    EditText txtName, txtUnitPrice, txtDiscount, txtDescription;
    Button btnUpload, btnCancel, btnAdd;
    DatabaseReference databaseReference;
    Product product;

    // Method to clear all user inputs
    private void clearControls() {
        txtName.setText("");
        txtUnitPrice.setText("");
        txtDiscount.setText("");
        txtDescription.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        Log.i("Lifecycle", "OnCreate() invoked");

        txtName = findViewById(R.id.name_val);
        txtUnitPrice = findViewById(R.id.unit_price_val);
        txtDiscount = findViewById(R.id.discount_val);
        txtDescription = findViewById(R.id.desc_val);

        btnUpload = findViewById(R.id.upload_img);
        btnCancel = findViewById(R.id.cancel);
        btnAdd = findViewById(R.id.add_item);

        product = new Product();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

                try {
                    if (TextUtils.isEmpty(txtName.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter product name", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(txtUnitPrice.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter unit price", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(txtDescription.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(txtDiscount.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter discount", Toast.LENGTH_LONG).show();
                    }

                    // add photo upload required condition
                    else {
                        // take inputs from the user and assigning them to this instance (product) of the Product
                        product.setName(txtName.getText().toString().trim());
                        product.setDescription(txtDescription.getText().toString().trim());
                        product.setUnitPrice(Integer.parseInt(txtUnitPrice.getText().toString().trim()));
                        product.setDiscount(Integer.parseInt(txtDiscount.getText().toString().trim()));

                        // insert into the database
                        databaseReference.push().setValue(product);

                        // provide feedback to the user via a toast
                        Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_LONG).show();

                        // clear all user inputs
                        clearControls();

                        // navigate to menu management activity
                        Intent intent = new Intent(AddFoodItemActivity.this, MenuManagementActivity.class);
                        startActivity(intent);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid input!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void cancel(View view) {
        Intent intent = new Intent(AddFoodItemActivity.this, MenuManagementActivity.class);
        startActivity(intent);
    }

    /*
    public void addItem(View view) {
        //product = new Product();

        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

        if (TextUtils.isEmpty(txtName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter product name", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(txtUnitPrice.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter unit price", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(txtDescription.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(txtDiscount.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter discount", Toast.LENGTH_LONG).show();
        }

        // add photo upload required condition

        else {
            // take inputs from the user and assigning them to this instance (product) of the Product

            product.setName(txtName.getText().toString().trim());
            product.setDescription(txtDescription.getText().toString().trim());

            try {
                product.setUnitPrice(Double.parseDouble(txtUnitPrice.getText().toString().trim()));
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid unit price", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error occurred! Try again", Toast.LENGTH_LONG).show();
            }

            try {
                product.setDiscount(Double.parseDouble(txtDiscount.getText().toString().trim()));
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid discount", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error occurred! Try again", Toast.LENGTH_LONG).show();
            }

            // insert into the database
            //databaseReference.child(String.valueOf(product.getId())).setValue(product);
            //databaseReference.child("abc").setValue(product);
            databaseReference.push().setValue(product);

            // provide feedback to the user via a toast
            //Context context = getApplicationContext();
            //CharSequence text = "Item Added Successfully";
            //int duration = Toast.LENGTH_LONG;
            //Toast toast = Toast.makeText(context, text, duration);
            //toast.show();
            Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_LONG).show();

            // clear all user inputs
            clearControls();

            // navigate to menu management activity
            Intent intent = new Intent(AddFoodItemActivity.this, MenuManagementActivity.class);
            startActivity(intent);
        }
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

    /*
    public void test(View view) {
        Intent intent = new Intent(AddFoodItemActivity.this, FoodListActivity.class);
        startActivity(intent);
    }
    */
}
