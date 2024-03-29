package com.example.ebreadshop.menuManagement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.example.ebreadshop.user.custHomeActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddFoodItemActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;
    private long max_id = 0;

    private EditText txtName, txtUnitPrice, txtDiscount, txtDescription;
    private Button btnUpload, btnCancel, btnAdd;
    private ImageView imageView;

    private Product product;

    private String url = "";
    private String id = "";

    private String saveCurrentDate, saveCurrentTime, productKey;
    private String downloadImage;

    private Task<Uri> task;
    private Uri downloadUri;
    //private Uri filePath;
    private Uri mImageUri;

    private DatabaseReference databaseReference;

    private FirebaseStorage storage;
    private StorageReference storageReference, productImageReference;

    // method to clear all user inputs
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

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        productImageReference = FirebaseStorage.getInstance().getReference().child("Product");

        txtName = findViewById(R.id.name_val);
        txtUnitPrice = findViewById(R.id.unit_price_val);
        txtDiscount = findViewById(R.id.discount_val);
        txtDescription = findViewById(R.id.desc_val);

        btnUpload = findViewById(R.id.upload_img);
        btnCancel = findViewById(R.id.cancel);
        btnAdd = findViewById(R.id.add_item);

        imageView = findViewById(R.id.food_img);

        product = new Product();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                max_id = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFoodItemActivity.this);

                builder.setTitle("Add food item");
                builder.setMessage("Are you sure you want to continue?");

                builder.setCancelable(false);

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(CRUDFoodActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        //databaseReference.child("P1").removeValue();
                        //finish();

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

                        try {
                            if (TextUtils.isEmpty(txtName.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Please enter product name", Toast.LENGTH_SHORT).show();
                            } else if (TextUtils.isEmpty(txtUnitPrice.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Please enter unit price", Toast.LENGTH_SHORT).show();
                            } else if (TextUtils.isEmpty(txtDescription.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_SHORT).show();
                            } else if (TextUtils.isEmpty(txtDiscount.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Please enter discount", Toast.LENGTH_SHORT).show();
                            } else if (Double.parseDouble(txtUnitPrice.getText().toString().trim()) < Double.parseDouble(txtDiscount.getText().toString().trim())) {
                                Toast.makeText(getApplicationContext(), "Discount should be less than unit price", Toast.LENGTH_SHORT).show();
                                txtDiscount.setText(null);
                            } else if (mImageUri == null) {
                                Toast.makeText(getApplicationContext(), "Please upload an image of the product", Toast.LENGTH_SHORT).show();
                            } else {
                                uploadImg();

                                product.setName(txtName.getText().toString().trim());

                                try {
                                    //product.setUnitPrice(Double.parseDouble(txtUnitPrice.getText().toString().trim()));
                                    product.setUnitPrice(txtUnitPrice.getText().toString().trim());
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "Invalid unit price!", Toast.LENGTH_SHORT).show();
                                }

                                try {
                                    //product.setDiscount(Double.parseDouble(txtDiscount.getText().toString().trim()));
                                    product.setDiscount(txtDiscount.getText().toString().trim());
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "Invalid discount!", Toast.LENGTH_SHORT).show();
                                }

                                double up = Double.parseDouble(txtUnitPrice.getText().toString().trim());
                                double dis = Double.parseDouble(txtDiscount.getText().toString().trim());

                                double price = up - dis;

                                product.setPrice(String.valueOf(price));

                                product.setDescription(txtDescription.getText().toString().trim());

                                // insert into the database
                                //databaseReference.push().setValue(product);
                                //databaseReference.child(String.valueOf(max_id + 1)).setValue(product);
                                id = "P" + (max_id + 1);
                                databaseReference.child(id).setValue(product);

                                // provide feedback to the user via a toast
                                Toast.makeText(getApplicationContext(), "Item added successfully", Toast.LENGTH_SHORT).show();

                                // clear all user inputs
                                clearControls();

                                // navigate to menu management activity
                                Intent intent = new Intent(AddFoodItemActivity.this, MenuManagementActivity.class);
                                startActivity(intent);
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
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
        Intent intent = new Intent(AddFoodItemActivity.this, MenuManagementActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void chooseImage() {
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImg() {
        if (mImageUri != null) {
            /*
            final StorageReference ref = storageRef.child("images/mountains.jpg");
            uploadTask = ref.putFile(file);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
            */

            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //StorageReference reference = storageReference.child("images/" + UUID.randomUUID().toString());
            //url = reference.getDownloadUrl().toString();
            //task = reference.getDownloadUrl();

            Calendar calender = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calender.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calender.getTime());

            productKey = saveCurrentDate + saveCurrentTime;

            final StorageReference file_Path = productImageReference.child(mImageUri.getLastPathSegment() + productKey);

            final UploadTask uploadTask = file_Path.putFile(mImageUri);

            /*
            reference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            //task = reference.getDownloadUrl();
                            //downloadUri = task.getResult();

                            //downloadUri = reference.getDownloadUrl().getResult();

                            Toast.makeText(AddFoodItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddFoodItemActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
            */

            //task = reference.getDownloadUrl();
            //url = reference.getDownloadUrl().toString();

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();

                    String message = e.toString();
                    Toast.makeText(AddFoodItemActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    Toast.makeText(AddFoodItemActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            downloadImage = file_Path.getDownloadUrl().toString();

                            //product.setUri(downloadUri);
                            //product.setImgURL(downloadImage);
                            //product.setImgURL(product.getUri().toString());
                            //product.setTask(task);
                            //product.setImgURL(task.toString());

                            return file_Path.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImage = task.getResult().toString();

                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");

                                //product.setUri(downloadUri);
                                product.setImgURL(downloadImage);
                                //product.setImgURL(product.getUri().toString());
                                //product.setTask(task);
                                //product.setImgURL(task.toString());

                                Toast.makeText(AddFoodItemActivity.this, "Product image has been saved to database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
                    */
                }
            });
        }
    }

    /*
    public void addItem(View view) {
        //product = new Product();

        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");

        if (TextUtils.isEmpty(txtName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter product name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(txtUnitPrice.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter unit price", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(txtDescription.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(txtDiscount.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter discount", Toast.LENGTH_SHORT).show();
        }

        // add photo upload required condition

        else {
            product.setName(txtName.getText().toString().trim());
            product.setDescription(txtDescription.getText().toString().trim());

            try {
                product.setUnitPrice(Double.parseDouble(txtUnitPrice.getText().toString().trim()));
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid unit price", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error occurred! Try again", Toast.LENGTH_SHORT).show();
            }

            try {
                product.setDiscount(Double.parseDouble(txtDiscount.getText().toString().trim()));
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid discount", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error occurred! Try again", Toast.LENGTH_SHORT).show();
            }

            // insert into the database
            databaseReference.child(String.valueOf(product.getId())).setValue(product);
            //databaseReference.child("abc").setValue(product);
            //databaseReference.push().setValue(product);

            // provide feedback to the user via a toast
            //Context context = getApplicationContext();
            //CharSequence text = "Item Added Successfully";
            //int duration = Toast.LENGTH_SHORT;
            //Toast toast = Toast.makeText(context, text, duration);
            //toast.show();
            Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();

            // clear all user inputs
            clearControls();

            // navigate to menu management activity
            Intent intent = new Intent(AddFoodItemActivity.this, MenuManagementActivity.class);
            startActivity(intent);
        }
    }
    */

    /*
    public void test(View view) {
        Intent intent = new Intent(AddFoodItemActivity.this, FoodListActivity.class);
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
