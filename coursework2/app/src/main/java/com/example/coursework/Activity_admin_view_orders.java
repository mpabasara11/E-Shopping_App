package com.example.coursework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Orders;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_admin_view_orders extends AppCompatActivity {
    RecyclerView recyclerView;
    Admin_order_adapter admin_order_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_orders);

        recyclerView = findViewById(R.id.rcl_admin_viewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Orders"), Orders.class)
                        .build();

        admin_order_adapter = new Admin_order_adapter(options, this);
        recyclerView.setAdapter(admin_order_adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        admin_order_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        admin_order_adapter.stopListening();

        //kill the activity when screen is off
        finish();
    }
}