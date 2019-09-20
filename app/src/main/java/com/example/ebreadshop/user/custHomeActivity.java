package com.example.ebreadshop.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;

public class custHomeActivity extends AppCompatActivity {

    private Button signin;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_home);

        signup = findViewById(R.id.nacc);
        signin = findViewById(R.id.signin);

    }

    public void Aregister(View view) {

        Intent intent = new Intent(custHomeActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void Asignin(View view) {
        Intent intent = new Intent(custHomeActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}
