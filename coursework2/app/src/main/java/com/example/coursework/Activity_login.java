package com.example.coursework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coursework.Models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_login extends AppCompatActivity {
    Button btnLg;
    EditText phone, pass;
    String parentDb = "Users";
    TextView lableAdmin, lableUser;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLg = findViewById(R.id.btn_signin);
        phone = findViewById(R.id.txt_signin_phone);
        pass = findViewById(R.id.txt_signin_pass);
        lableAdmin = findViewById(R.id.lbl_Admin);
        lableUser = findViewById(R.id.lbl_user);
        loadingBar = new ProgressDialog(this);


        //admin label
        lableAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnLg.setText("Login Admin");
                parentDb = "Admins";
                lableAdmin.setVisibility(view.INVISIBLE);
                lableUser.setVisibility(view.VISIBLE);

            }
        });

        //user label
        lableUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnLg.setText("Login User");
                parentDb = "Users";
                lableAdmin.setVisibility(view.VISIBLE);
                lableUser.setVisibility(view.INVISIBLE);

            }
        });

        //login button
        btnLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }


    //validation part
    private void LoginUser() {
        String x = phone.getText().toString();
        String y = pass.getText().toString();

        if (TextUtils.isEmpty(x)) {
            Toast.makeText(this, "Please enter your mobile number...", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(y)) {
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();

        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(x, y);
        }
    }

    //login part
    private void AllowAccessToAccount(final String phone, final String password) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDb).child(phone).exists()) {

                    Users usersData = dataSnapshot.child(parentDb).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {
                            if (parentDb.equals("Admins")) {
                                Toast.makeText(Activity_login.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                //redirect to admin home page
                                Intent intent = new Intent(Activity_login.this, Activity_admin_home.class);
                                startActivity(intent);

                            } else if (parentDb.equals("Users")) {
                                Toast.makeText(Activity_login.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                //redirect to user home
                                Intent intent = new Intent(Activity_login.this, Activity_user_home.class);
                                startActivity(intent);
                                Current_User_Store.usrStore = usersData;

                            }

                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(Activity_login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Activity_login.this, "Account with this " + phone + " number do not exists on our databases!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}