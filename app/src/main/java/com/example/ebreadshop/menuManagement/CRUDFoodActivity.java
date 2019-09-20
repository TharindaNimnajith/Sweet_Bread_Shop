package com.example.ebreadshop.menuManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.example.ebreadshop.user.custHomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class CRUDFoodActivity extends AppCompatActivity {

    private TextView tvTitle;
    private EditText etName, etUnitPrice, etDiscount, etDescription;
    private Button btnEditImage, btnCancel, btnUpdate, btnDelete;
    private ImageView imageView;

    private Product product;
    private String url = "";

    private final int PICK_IMAGE_REQUEST = 71;

    // Database
    private DatabaseReference databaseReference;

    // Storage
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private Uri filePath;


    // Method to clear all user inputs
    private void clearControls() {
        etName.setText("");
        etUnitPrice.setText("");
        etDiscount.setText("");
        etDescription.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_food);

        Log.i("Lifecycle", "OnCreate() invoked");


        // Firebase Storage Init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        // Init View

        etName = findViewById(R.id.name_val_crud);
        etUnitPrice = findViewById(R.id.unit_price_val_crud);
        etDiscount = findViewById(R.id.discount_val_crud);
        etDescription = findViewById(R.id.desc_val_crud);

        btnEditImage = findViewById(R.id.edit_img_crud);
        btnCancel = findViewById(R.id.cancel_crud);
        btnUpdate = findViewById(R.id.update_crud);
        btnDelete = findViewById(R.id.delete_crud);

        //imageView = findViewById(R.id.ev);

        tvTitle = findViewById(R.id.title_crud);


        product = new Product();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");


        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Product");

                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("1")) {
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child("1");

                            databaseReference.removeValue();
                            clearControls();

                            Toast.makeText(getApplicationContext(), "Data Deleted Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source to Delete", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //
                    }
                });
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Product");

                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("1")) {
                            try {
                                product.setName(etName.getText().toString().trim());
                                //product.setUnitPrice(Double.parseDouble(etUnitPrice.getText().toString().trim()));
                                //product.setDiscount(Double.parseDouble(etDiscount.getText().toString().trim()));
                                product.setUnitPrice(etUnitPrice.getText().toString().trim());
                                product.setDiscount(etDiscount.getText().toString().trim());
                                product.setDescription(etDescription.getText().toString().trim());

                                uploadImg();

                                product.setImgURL(url);


                                databaseReference = FirebaseDatabase.getInstance().getReference().child("Product").child("1");

                                databaseReference.setValue(product);
                                clearControls();

                                Toast.makeText(getApplicationContext(), "Data update Successfully", Toast.LENGTH_LONG).show();
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source to Update", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //
                    }
                });
            }
        });


        /*
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Product").child("1");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            tvTitle.setText(dataSnapshot.child("name").getValue().toString());
                            etName.setText(dataSnapshot.child("name").getValue().toString());
                            etUnitPrice.setText(dataSnapshot.child("unitPrice").getValue().toString());
                            etDiscount.setText(dataSnapshot.child("discount").getValue().toString());
                            etDescription.setText(dataSnapshot.child("description").getValue().toString());
                            //image
                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //
                    }
                });
            }
        });
        */
    }


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


    public void cancel(View view) {
        Intent intent = new Intent(CRUDFoodActivity.this, MenuManagementActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void chooseImage() {
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadImg() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference reference = storageReference.child("images/" + UUID.randomUUID().toString());
            url = reference.getDownloadUrl().toString();

            reference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CRUDFoodActivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CRUDFoodActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    /*
    public void update(View view) {
        Intent intent = new Intent(CRUDFoodActivity.this, MenuManagementActivity.class);

        Context context = getApplicationContext();
        CharSequence text = "Item Details Updated Successfully";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        startActivity(intent);
        toast.show();
    }


    public void delete(View view) {
        Intent intent = new Intent(CRUDFoodActivity.this, MenuManagementActivity.class);

        Context context = getApplicationContext();
        CharSequence text = "Item Deleted Successfully";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        startActivity(intent);
        toast.show();
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
