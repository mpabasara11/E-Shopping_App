package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_admin_home extends AppCompatActivity {
    Button btn_itemAdd, btn_itemMange, btn_viewOrder, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        btn_itemAdd = findViewById(R.id.btn_admin_addItem);
        btn_itemMange = findViewById(R.id.btn_admin_manageItem);
        btn_viewOrder = findViewById(R.id.btn_admin_viewOrders);
        btnSignOut = findViewById(R.id.btn_admin_signout);


        //add item button
        btn_itemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_admin_home.this, Activity_admin_add_item.class);
                startActivity(intent);
            }
        });

        //manage item button
        btn_itemMange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_admin_home.this, Activity_admin_manage_items.class);
                startActivity(intent);
            }
        });


        //view order button
        btn_viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Activity_admin_home.this, Activity_admin_view_orders.class);
                startActivity(intent);
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