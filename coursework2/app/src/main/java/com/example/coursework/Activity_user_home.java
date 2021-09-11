package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_user_home extends AppCompatActivity {
    Button btnItemView, btnCartView, btnViewHistory, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        btnItemView = findViewById(R.id.btn_user_viewItem);
        btnCartView = findViewById(R.id.btn_user_manageCart);
        btnViewHistory = findViewById(R.id.btn_user_signout);
        btnSignOut = findViewById(R.id.btn_user_signout);


        //redirect to item view page
        btnItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Activity_user_home.this, Activity_user_items_view.class);
                startActivity(intent);
            }
        });

        //redirect to cart view page
        btnCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_user_home.this, Activity_user_cart_view.class);
                startActivity(intent);

            }
        });

        //redirect to order history page
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //sign out button
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}