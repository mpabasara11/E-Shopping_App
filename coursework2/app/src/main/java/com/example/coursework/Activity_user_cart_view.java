package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Cart;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_user_cart_view extends AppCompatActivity {
    RecyclerView recyclerView;
    User_cart_adapter user_cart_adapter;
    Button btnPlacrOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart_view);
        recyclerView = findViewById(R.id.rcl_user_cartView);
        btnPlacrOrder = findViewById(R.id.btn_user_place_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //recyclerview handler
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart_List").child("User_View").child(Current_User_Store.usrStore.getPhone()), Cart.class)
                        .build();

        user_cart_adapter = new User_cart_adapter(options, this);
        recyclerView.setAdapter(user_cart_adapter);


        //place order button
        btnPlacrOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isCartEmpty();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        user_cart_adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        user_cart_adapter.stopListening();


        //kill the activity when screen is off
        finish();
    }


    //check that cart list is empty
    public void isCartEmpty() {

        FirebaseDatabase.getInstance().getReference().child("Cart_List").child("User_View").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(Current_User_Store.usrStore.getPhone())) {

                    //finish current activity
                    finish();

                    //open order infomation activity
                    Intent intent = new Intent(Activity_user_cart_view.this, Activity_user_order_information.class);
                    startActivity(intent);


                } else {

                    Toast.makeText(Activity_user_cart_view.this, "Your Cart Has No Items..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}



