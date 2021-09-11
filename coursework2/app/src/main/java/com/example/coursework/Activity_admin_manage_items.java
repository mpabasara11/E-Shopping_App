package com.example.coursework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Items;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_admin_manage_items extends AppCompatActivity {

    RecyclerView recyclerView;
    Admin_item_adapter admin_item_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_items);

        recyclerView = findViewById(R.id.rcl_admin_manageItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), Items.class)
                        .build();

        admin_item_adapter = new Admin_item_adapter(options, this);
        recyclerView.setAdapter(admin_item_adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //adapter start
        admin_item_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //adapter stop
        admin_item_adapter.stopListening();

        //kill the activity when screen is off or dissapeared
        finish();
    }
}