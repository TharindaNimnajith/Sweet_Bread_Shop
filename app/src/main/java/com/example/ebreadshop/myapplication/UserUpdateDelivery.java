
package com.example.ebreadshop.myapplication;

        import android.app.DatePickerDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
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

public class UserUpdateDelivery  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView DisplayDate, txtzip, txtlane, txtname, txttp, txttp2, txtdate, txttime, txtcurrentD, txtprice, txtoderID,txtuserID;
    public DatePickerDialog.OnDateSetListener mDateSetListener;
    Button addToDB , DeleteB;

    Spinner spicity;
    long maxid = 0;
    DeliveryHomeTable dev;

    DatabaseReference db;

    AlertDialog.Builder builder;

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
        setContentView(R.layout.activity_user_update_delivery);

        Bundle extra = getIntent().getExtras();
        final String key = extra.getString("key");

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

                DatePickerDialog dialog = new DatePickerDialog(UserUpdateDelivery.this,
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

        txtoderID = findViewById(R.id.dhomeOrderID);
        txtuserID = findViewById(R.id.dhomeUserID);
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
        DeleteB = findViewById(R.id.DhomeDelete);

        dev = new DeliveryHomeTable();

        DatabaseReference upref = FirebaseDatabase.getInstance().getReference().child("DeliveryHomeTable").child(key);
        DatabaseReference deref = FirebaseDatabase.getInstance().getReference().child("DeliveryHomeTable").child(key);






        upref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){

                    txtoderID.setText(dataSnapshot.child("orderID").getValue().toString());
                    txtuserID.setText(dataSnapshot.child("userId").getValue().toString());

                    //--------add Spinner possion
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter
                            .createFromResource(UserUpdateDelivery.this, R.array.name,
                                    android.R.layout.simple_spinner_dropdown_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spicity.setAdapter(adapter);
                    spicity.setOnItemSelectedListener(UserUpdateDelivery.this);
                    int spinnerPosition = 0;
                    String c = dataSnapshot.child("city").getValue().toString();
                    spinnerPosition = adapter.getPosition(c);
                    spicity.setSelection(spinnerPosition);

                    // end Spinner possion---------------
                    txtcurrentD.setText(dataSnapshot.child("currentDate").getValue().toString());
                    txtzip.setText(dataSnapshot.child("zipcode").getValue().toString());
                    txtlane.setText(dataSnapshot.child("lane").getValue().toString());
                    txtprice.setText(dataSnapshot.child("price").getValue().toString());
                    txtname.setText(dataSnapshot.child("name").getValue().toString());
                    txttp.setText(dataSnapshot.child("tp").getValue().toString());
                    txttp2.setText(dataSnapshot.child("tp2").getValue().toString());
                    txttime.setText(dataSnapshot.child("time").getValue().toString());
                    txtdate.setText(dataSnapshot.child("date").getValue().toString());


                    addToDB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserUpdateDelivery.this, R.style.MyDialogTheme);
                            // Setting Alert Dialog Title
                            alertDialogBuilder.setTitle("Confirm Update..!!!");
                            // Icon Of Alert Dialog
                            alertDialogBuilder.setIcon(R.drawable.c);
                            // Setting Alert Dialog Message

                            alertDialogBuilder.setMessage("THIS Can not Undo");

                            alertDialogBuilder.setCancelable(false);

                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {


                                    db = FirebaseDatabase.getInstance().getReference().child("DeliveryHomeTable");

                                    dev = new DeliveryHomeTable();


                                    dev.setUserId(txtuserID.getText().toString().trim());
                                    dev.setOrderID(txtuserID.getText().toString().trim());
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

                                    db.child(key).setValue(dev);
                                    Toast.makeText(getApplicationContext(), "your data is added", Toast.LENGTH_SHORT).show();


                                    finish();
                                }
                            });

                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserUpdateDelivery.this, "You clicked over No", Toast.LENGTH_SHORT).show();
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



                    });

                    DeleteB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserUpdateDelivery.this,R.style.MyDialogTheme);
                            // Setting Alert Dialog Title
                            alertDialogBuilder.setTitle("Confirm Delete..!!!");
                            // Icon Of Alert Dialog
                            alertDialogBuilder.setIcon(R.drawable.c);
                            // Setting Alert Dialog Message
                            alertDialogBuilder.setMessage("Are you sure,You want to Delete");
                            alertDialogBuilder.setCancelable(false);

                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    db = FirebaseDatabase.getInstance().getReference().child("DeliveryHomeTable").child(key);
                                    db.removeValue();
                                    clear();
                                    Intent i = new Intent(getApplicationContext(), deliverylist.class);
                                   startActivity(i);
                                   //dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "your data is Deleted ", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserUpdateDelivery.this,"You clicked over No",Toast.LENGTH_SHORT).show();
                                }
                            });
                            alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }






//                                AlertDialog alertDialog = new AlertDialog.Builder(UpdateDelivery.this,R.style.MyDialogTheme).create();
//                                alertDialog.setTitle("Alert");
//                                alertDialog.setMessage("Alert message to be shown");
//                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                db = FirebaseDatabase.getInstance().getReference().child("DeliveryHomeTable").child(key);
//                                                //db.removeValue();
//                                                clear();
//                                                Intent i = new Intent(getApplicationContext(), ShowDelivery.class);
//                                                startActivity(i);
//                                                dialog.dismiss();
//                                                Toast.makeText(getApplicationContext(), "your data is Deleted ", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                alertDialog.show();









                    });




                }

                else{
                    Toast.makeText(getApplicationContext(),"Nothis to display",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // String item = parent.getItemAtPosition(position).toString();
        TextView Dprice = findViewById(R.id.Dhomeprice);

        if (position == 0) {//malabe
            double m = 200;
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



}

