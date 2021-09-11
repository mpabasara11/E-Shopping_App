package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Cart;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_admin_view_order_items extends AppCompatActivity {
    RecyclerView recyclerView;
    Admin_order_items_adapter admin_order_items_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_order_items);

        recyclerView = findViewById(R.id.rcl_admin_order_itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart_List").child("Admin_View").child(getIntent().getStringExtra("phone")), Cart.class)
                        .build();

        admin_order_items_adapter = new Admin_order_items_adapter(options, this);
        recyclerView.setAdapter(admin_order_items_adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        admin_order_items_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        admin_order_items_adapter.stopListening();

        //kill the activity when screen is off or dissapeared
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //when going back order view should be created again cause it was terminated when this activity is created
        Intent intent = new Intent(Activity_admin_view_order_items.this, Activity_admin_view_orders.class);
        startActivity(intent);

    }
}