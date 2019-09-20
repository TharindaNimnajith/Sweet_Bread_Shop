package com.example.ebreadshop.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.example.ebreadshop.menuManagement.NewFoodListActivity;
import com.example.ebreadshop.user.Model.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private EditText InputUsername, InputPassword;
    private TextView adminbtn1;
    private Button signinbtn;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signinbtn = findViewById(R.id.bsign);
        InputUsername = findViewById(R.id.signnm);
        InputPassword = findViewById(R.id.signpwd);
        adminbtn1 = findViewById(R.id.admintextbtn);
        loadingBar = new ProgressDialog(this);

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        adminbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GotoAdmin();
            }
        });
    }

    private void GotoAdmin() {
        Intent intent = new Intent(SignInActivity.this, AdminLoginActivity.class);
        startActivity(intent);
    }

    private void LoginUser() {
        String name = InputUsername.getText().toString();
        String password = InputPassword.getText().toString();

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
                    user usersData = dataSnapshot.child(parentDbName).child(name).getValue(user.class);

                    if (usersData.getName().equals(name)) {
                        if (usersData.getPassword().equals(password)) {
                            Toast.makeText(SignInActivity.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(SignInActivity.this, NewFoodListActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Account with this " + name + "name do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignInActivity.this, "You need to create a new Account.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
