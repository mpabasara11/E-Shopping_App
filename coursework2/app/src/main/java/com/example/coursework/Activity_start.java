package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_start extends AppCompatActivity {

    private Button btnSignin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnSignin = findViewById(R.id.btn_Tosignin);
        btnSignup = findViewById(R.id.btn_Tosignup);

        //open signin activity
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Activity_start.this, Activity_login.class);
                startActivity(intent);
            }
        });

        //open signup activity
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_start.this, Activity_register.class);
                startActivity(intent);

            }
        });


    }

}