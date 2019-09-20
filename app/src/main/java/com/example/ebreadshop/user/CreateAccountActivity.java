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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {

    private Button signup;
    private EditText inputUname, inputEmail, inputPassword, inputrpassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        signup = findViewById(R.id.accbtnreg);
        inputUname = findViewById(R.id.accuname);
        inputEmail = findViewById(R.id.accemail);
        inputPassword = findViewById(R.id.accpwd);
        inputrpassword = findViewById(R.id.accrpwd);
        loadingBar = new ProgressDialog(this);

        signup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String name = inputUname.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String rpassword = inputrpassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Enter Your name .. ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Your Email .. ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Your passsword .. ", Toast.LENGTH_SHORT).show();
        } else if (!(password.equals(rpassword))) {
            Toast.makeText(this, "Password do not match.. ", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, While we are checking the credetial.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(name, email, password, rpassword);
        }
    }

    private void ValidateEmail(final String name, final String email, final String paswword, String rpassword) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(name).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("email", email);
                    userdataMap.put("password", paswword);
                    userdataMap.put("name", name);

                    RootRef.child("Users").child(name).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CreateAccountActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(CreateAccountActivity.this, SignInActivity.class);
                                        startActivity(intent);
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(CreateAccountActivity.this, "Network Error : Please try again. ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(CreateAccountActivity.this, "This" + name + "Already exists . ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(CreateAccountActivity.this, "Please try again using and another name. ", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(CreateAccountActivity.this, custHomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
