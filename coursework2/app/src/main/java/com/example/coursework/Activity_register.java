package com.example.coursework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Activity_register extends AppCompatActivity {
    EditText phone, pass, conPass;
    Button btnReg;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phone = findViewById(R.id.txt_signup_phone);
        pass = findViewById(R.id.txt_signup_pass);
        conPass = findViewById(R.id.txt_signup_cnfPass);
        btnReg = findViewById(R.id.btn_signup);
        loadingBar = new ProgressDialog(Activity_register.this);

        //register button
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCreate();
            }
        });


    }

    //validation part
    private void userCreate() {
        String x = phone.getText().toString();
        String y = pass.getText().toString();
        String z = conPass.getText().toString();

        if (TextUtils.isEmpty(x)) {
            Toast.makeText(Activity_register.this, "Please enter your mobile number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(y)) {
            Toast.makeText(Activity_register.this, "Please enter a password...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(z)) {
            Toast.makeText(Activity_register.this, "Please enter password in confirm password again...", Toast.LENGTH_SHORT).show();
        } else if (!(y.equals(z))) {
            Toast.makeText(Activity_register.this, "Please check that confirm password is same as the password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Creating An Account");
            loadingBar.setMessage("Please wait, while we are checking our databases");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            createAcc(x, y);
        }
    }

    //account creating part
    private void createAcc(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!(snapshot.child("Users").child(phone).exists())) {

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(Activity_register.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            //terminate current activity
                            finish();

                            //redirect to login page
                            Intent intent = new Intent(Activity_register.this, Activity_login.class);
                            startActivity(intent);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Activity_register.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                    Toast.makeText(Activity_register.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Activity_register.this, "Please try again using another phone number or Login using excisting number", Toast.LENGTH_SHORT).show();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}