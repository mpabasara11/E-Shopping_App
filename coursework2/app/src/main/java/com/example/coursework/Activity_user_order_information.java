package com.example.coursework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Activity_user_order_information extends AppCompatActivity {
    EditText name, town, address, email;
    Button btnSubmit;
    ProgressDialog loadingBar;
    String OrderCusName, OrderCusTOwn, OrserCusAdd, OrderCusMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_information);

        name = findViewById(R.id.txt_user_order_usrName);
        town = findViewById(R.id.txt_user_order_usrTown);
        address = findViewById(R.id.txt_user_order_usrAdd);
        email = findViewById(R.id.txt_user_order_usrMail);

        btnSubmit = findViewById(R.id.btn_user_submit_order);

        loadingBar = new ProgressDialog(Activity_user_order_information.this);


        //submit infomation button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInfo();
            }
        });
    }

    //input validation
    public void validateInfo() {
        OrderCusName = name.getText().toString();
        OrderCusTOwn = town.getText().toString();
        OrserCusAdd = address.getText().toString();
        OrderCusMail = email.getText().toString();

        if (TextUtils.isEmpty(OrderCusName)) {
            Toast.makeText(Activity_user_order_information.this, "Please enter your Name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(OrderCusTOwn)) {
            Toast.makeText(Activity_user_order_information.this, "Please enter your Town...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(OrserCusAdd)) {
            Toast.makeText(Activity_user_order_information.this, "Please enter your current address...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(OrderCusMail)) {
            Toast.makeText(Activity_user_order_information.this, "Please enter your email...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Creating An Account");
            loadingBar.setMessage("Please wait, while we are checking our databases");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            placeOrder();
        }
    }

    //place the order
    public void placeOrder() {


        String saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("phone", Current_User_Store.usrStore.getPhone());
        orderMap.put("name", OrderCusName);
        orderMap.put("town", OrderCusTOwn);
        orderMap.put("address", OrserCusAdd);
        orderMap.put("email", OrderCusMail);
        orderMap.put("date", saveCurrentDate);


        RootRef.child("Orders").child(Current_User_Store.usrStore.getPhone()).updateChildren(orderMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Activity_user_order_information.this, "Order Placed SuccessFully..", Toast.LENGTH_SHORT).show();


                //remove current user cart values
                RootRef.child("Cart_List").child("User_View").child(Current_User_Store.usrStore.getPhone()).removeValue();

                Toast.makeText(Activity_user_order_information.this, "Your Cart List has been cleared..", Toast.LENGTH_SHORT).show();


                loadingBar.dismiss();

                //terminate current activity
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //when back button is pressed user cart view should be created again cause it terminated before
        Intent intent = new Intent(Activity_user_order_information.this, Activity_user_cart_view.class);
        startActivity(intent);
    }
}