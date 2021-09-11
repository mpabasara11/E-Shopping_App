package com.example.coursework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Items;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_user_items_view extends AppCompatActivity {
    RecyclerView rv_itemView;
    User_item_adapter user_item_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_items_view);

        rv_itemView = findViewById(R.id.rv_user_viewItem);
        rv_itemView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), Items.class)
                        .build();

        user_item_adapter = new User_item_adapter(options, this);
        rv_itemView.setAdapter(user_item_adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //adapter starts
        user_item_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //adapter stops
        user_item_adapter.stopListening();

        //kill the activity when screen is off or activity another activity opened
        finish();
    }
}