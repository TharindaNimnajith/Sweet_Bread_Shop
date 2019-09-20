package com.example.ebreadshop.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.example.ebreadshop.menuManagement.MenuManagementActivity;
import com.example.ebreadshop.user.Model.admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText adminuname, adminpass;
    private Button login;
    private ProgressDialog loadingBar;
    private String parentDbName = "Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminuname = findViewById(R.id.adminuname);
        adminpass = findViewById(R.id.adminpass);
        login = findViewById(R.id.adminlogin);
        loadingBar = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginToAdmin();
            }
        });

    }

    public void LoginToAdmin() {
        String name = adminuname.getText().toString();
        String password = adminpass.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Enter Your name .. ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Your passsword .. ", Toast.LENGTH_SHORT).show();
        } else {

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, While we are checking the credetial.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(name, password);


        }
    }


    private void AllowAccessToAccount(final String name, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(name).exists()) {
                    admin adminsData = dataSnapshot.child(parentDbName).child(name).getValue(admin.class);

                    if (adminsData.getName().equals(name)) {
                        if (adminsData.getPassword().equals(password)) {
                            Toast.makeText(AdminLoginActivity.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(AdminLoginActivity.this, MenuManagementActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(AdminLoginActivity.this, "Account with this " + name + "name do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(AdminLoginActivity.this, "You need to create a new Account.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}