package com.example.ebreadshop.myapplication;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebreadshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.util.Calendar;

public class deliveryHome extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {



    TextView DisplayDate, txtzip, txtlane, txtname, txttp, txttp2, txtdate, txttime, txtcurrentD, txtprice;
    public DatePickerDialog.OnDateSetListener mDateSetListener;
    Button addToDB;
    Spinner spicity;
    long maxid = 0;
    DeliveryHomeTable dev;

    DatabaseReference db;




    private void clear() {


        txtzip.setText(" ");
        txtlane.setText(" ");
        txtname.setText(" ");
        txttp.setText(" ");
        txttp2.setText(" ");
        txttime.setText(" ");
        txtdate.setText(" ");


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_home);








        Spinner mySpinner = findViewById(R.id.city);
        mySpinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(this, R.array.name, android.R.layout.simple_spinner_item);

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(myAdapter);


        DisplayDate = (TextView) findViewById(R.id.DhomeDate);


        DisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(deliveryHome.this,
                        android.R.style.Theme_Black_NoTitleBar,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + " . " + month + " . " + year;
                DisplayDate.setText(date);

            }
        };


        Calendar calendar = Calendar.getInstance();

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        TextView txtcuDate = findViewById(R.id.DhomeCurrentdate);
        txtcuDate.setText(currentDate);

        //database process

        txtcurrentD = findViewById(R.id.DhomeCurrentdate);
        spicity = findViewById(R.id.city);
        txtzip = findViewById(R.id.DhomeAddress);
        txtlane = findViewById(R.id.DhomeAddress2);
        txtname = findViewById(R.id.DhomeName);
        txttp = findViewById(R.id.DhomeTp);
        txttp2 = findViewById(R.id.DhomeTP2);
        txtdate = findViewById(R.id.DhomeDate);
        txttime = findViewById(R.id.DhomeTime);
        txtprice = findViewById(R.id.Dhomeprice);
        addToDB = findViewById(R.id.DhomeSubmit);

        dev = new DeliveryHomeTable();
        db = FirebaseDatabase.getInstance().getReference().child("DeliveryHomeTable");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    final String Vzip = txtzip.getText().toString();
                    final String Vtp1 =txttp.getText().toString();
                    final String Vtp2 = txttp2.getText().toString();
                    final int VtpL =Vtp1.length();


                     //else if(!Name.matches("[a-zA-Z ]+"))
//                    {
//                        NameEditText.requestFocus();
//                        NameEditText.setError("ENTER ONLY ALPHABETICAL CHARACTER");
//                    }
                    if (TextUtils.isEmpty(txtzip.getText().toString()) ) {

                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context,"ADD your Zip ", Toast.LENGTH_LONG);
                        View v = toast.getView();
                       // v.setBackgroundResource(R.drawable.rrr);
                        TextView text = (TextView) v.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#2771e8"));

                        toast.show();



                    }


                    else if (TextUtils.isEmpty(txtlane.getText().toString())){
                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context,"ADD your State", Toast.LENGTH_LONG);
                        View v = toast.getView();
                        // v.setBackgroundResource(R.drawable.rrr);
                        TextView text = (TextView) v.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#2771e8"));

                        toast.show();
                    }


                    else if (TextUtils.isEmpty(txtname.getText().toString())){
                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context,"ADD your Name", Toast.LENGTH_LONG);
                        View v = toast.getView();
                        // v.setBackgroundResource(R.drawable.rrr);
                        TextView text = (TextView) v.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#2771e8"));

                        toast.show();
                    }


                    else if (TextUtils.isEmpty(txttp.getText().toString())){
                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context,"ADD Telephone No", Toast.LENGTH_LONG);
                        View v = toast.getView();
                        // v.setBackgroundResource(R.drawable.rrr);
                        TextView text = (TextView) v.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#2771e8"));

                        toast.show();
                    }


                    else if (TextUtils.isEmpty(txttp2.getText().toString())){
                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context,"ADD Another Telephone No", Toast.LENGTH_LONG);
                        View v = toast.getView();
                        // v.setBackgroundResource(R.drawable.rrr);
                        TextView text = (TextView) v.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#2771e8"));

                        toast.show();
                    }
                    else if (TextUtils.isEmpty(txtdate.getText().toString())){
                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context,"ADD Delivery Date", Toast.LENGTH_LONG);
                        View v = toast.getView();
                        // v.setBackgroundResource(R.drawable.rrr);
                        TextView text = (TextView) v.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#2771e8"));

                        toast.show();
                    }


                    else if (TextUtils.isEmpty(txttime.getText().toString())){
                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context,"ADD Delivery Time", Toast.LENGTH_LONG);
                        View v = toast.getView();
                        // v.setBackgroundResource(R.drawable.rrr);
                        TextView text = (TextView) v.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#2771e8"));

                        toast.show();
                    }

//                    else if(!Vzip.matches("[a-zA-Z ]+")){
//                        txtzip.requestFocus();
//                        txtzip.setError("ENTER ONLY ALPHABETICAL CHARACTER");
//
//
//                    }


                    else if((!Vtp1.matches("[0-9]")) && (VtpL !=10) ){
                        txttp.requestFocus();
                        txttp.setError("PLEASE CHEACK THIS");


                    }

                    else if((!Vtp2.matches("[0-9]")) && (Vtp2.length()!=10) ){
                        txttp2.requestFocus();
                        txttp2.setError("PLEASE CHEACK THIS");


                    }




                    else {


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(deliveryHome.this, R.style.MyDialogTheme);
                        // Setting Alert Dialog Title
                        alertDialogBuilder.setTitle("Confirm Price..!!!");
                        // Icon Of Alert Dialog
                        alertDialogBuilder.setIcon(R.drawable.c);
                        // Setting Alert Dialog Message

                        alertDialogBuilder.setMessage("YOUR DELIVERY COST:- " + txtprice.getText().toString().toUpperCase());

                        alertDialogBuilder.setCancelable(false);

                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                dev.setUserId("102");
                                dev.setOrderID("Order101");
                                dev.setCity(spicity.getSelectedItem().toString().trim());
                                dev.setCurrentDate(txtcurrentD.getText().toString().trim());
                                dev.setZipcode(txtzip.getText().toString().trim());
                                dev.setLane(txtlane.getText().toString().trim());
                                dev.setName(txtname.getText().toString().trim());
                                dev.setDate(txtdate.getText().toString().trim());
                                dev.setTp(txttp.getText().toString());
                                dev.setTp2(txttp2.getText().toString());
                                dev.setPrice(txtprice.getText().toString());
                                dev.setTime(txttime.getText().toString().trim());


                                db.child("D" + (maxid + 1)).setValue(dev);

                                Intent i = new Intent(getApplicationContext(), ShowUserDelivery.class);
                                i.putExtra("key","102");
                                startActivity(i);


                                Toast.makeText(getApplicationContext(), "your data is added", Toast.LENGTH_SHORT).show();
                                clear();





                                finish();
                            }
                        });

                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(deliveryHome.this, "You clicked over No", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_SHORT).show();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }



                } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid ", Toast.LENGTH_SHORT).show();
                }




            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // String item = parent.getItemAtPosition(position).toString();
        TextView Dprice = findViewById(R.id.Dhomeprice);


        if (position == 0) {//malabe
            double m = 200.00;
            String malabe = String.valueOf(m);
            Dprice.setText( malabe);
        } else if (position == 1) {//maharagama
            double maha = 150.00;
            String maharagama = String.valueOf(maha);
            Dprice.setText(maharagama);
        } else if (position == 2) {//kottawa
            double k = 120.00;
            String kottawa = String.valueOf(k);
            Dprice.setText( kottawa);
        } else if (position == 3) {//Piliyandala
            double p = 100.00;
            String piliyandala = String.valueOf(p);
            Dprice.setText( piliyandala);
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //database process


}
