package com.example.coursework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Activity_user_items_quentity_to_cart extends AppCompatActivity {
    TextView itemName, itemPrice;
    EditText itemQuentity;
    Button btnAddToCart;
    ImageView imgProduct;
    ProgressDialog loadingBar;
    String quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_items_quentity_to_cart);

        itemName = findViewById(R.id.lbl_user_add_to_cart_quen_itemName);
        itemPrice = findViewById(R.id.lbl_user_add_to_cart_quen_itemPrice);
        itemQuentity = findViewById(R.id.txt_user_add_to_cart_quen_itemQuentity);
        imgProduct = findViewById(R.id.img_user_item_Quentity);
        btnAddToCart = findViewById(R.id.btn_user_add_to_cart_quen_itemAddToCart);
        loadingBar = new ProgressDialog(this);


        Picasso.get().load(getIntent().getStringExtra("image_url")).into(imgProduct);
        itemName.setText(getIntent().getStringExtra("item_name"));
        itemPrice.setText("Rs/:" + getIntent().getStringExtra("price"));


        //add to cart button
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateInput();
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //previous activity is terminated before creating this activity.therefore if we go back that activity should be created again

        Intent intent = new Intent(Activity_user_items_quentity_to_cart.this, Activity_user_items_view.class);
        startActivity(intent);


    }

    //validate quantity field
    public void validateInput() {

        //get quantity input
        quantity = itemQuentity.getText().toString();

        if (TextUtils.isEmpty(quantity)) {
            Toast.makeText(this, "Please Enter Item Quantity you want...", Toast.LENGTH_SHORT).show();

        } else {
            addItemsToCart();
        }
    }

    //add info to cart
    public void addItemsToCart() {

        String saveCurrentTime, saveCurrentDate, Uid;

        loadingBar.setTitle("Add To Cart");
        loadingBar.setMessage("Items Are Adding to The Cart");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //unique id for cart items
        Uid = saveCurrentDate + saveCurrentTime;


        DatabaseReference dbrf;
        dbrf = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> userItemMap = new HashMap<>();
        userItemMap.put("Uid", Uid);
        userItemMap.put("pname", getIntent().getStringExtra("item_name"));
        userItemMap.put("price", getIntent().getStringExtra("price"));
        userItemMap.put("quantity", quantity);
        userItemMap.put("image", getIntent().getStringExtra("image_url"));


        dbrf.child("Cart_List").child("User_View").child(Current_User_Store.usrStore.getPhone()).child(Uid).updateChildren(userItemMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                HashMap<String, Object> AdminItemMap = new HashMap<>();
                userItemMap.put("Uid", Uid);
                userItemMap.put("pname", getIntent().getStringExtra("item_name"));
                userItemMap.put("price", getIntent().getStringExtra("price"));
                userItemMap.put("quantity", quantity);
                userItemMap.put("image", getIntent().getStringExtra("image_url"));

                dbrf.child("Cart_List").child("Admin_View").child(Current_User_Store.usrStore.getPhone()).child(Uid).updateChildren(userItemMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Activity_user_items_quentity_to_cart.this, "Succeeded..", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();


                        //terminate current activity before go to item list again
                        finish();
                        Intent intent = new Intent(Activity_user_items_quentity_to_cart.this, Activity_user_items_view.class);
                        startActivity(intent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Activity_user_items_quentity_to_cart.this, "Something went wrong..Redirecting back to the main page", Toast.LENGTH_LONG).show();

                //terminate current activity before go to user home
                finish();

                Intent intent = new Intent(Activity_user_items_quentity_to_cart.this, Activity_user_home.class);
                startActivity(intent);
            }
        });


    }
}