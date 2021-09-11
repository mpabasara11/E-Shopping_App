package com.example.coursework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Activity_admin_add_item extends AppCompatActivity {
    EditText itemName, itemDes, itemPrice;
    ImageView imgInput;
    Button btnAddItem;
    Uri imageUri;
    static final int GalleryPick = 1;    //request code
    ProgressDialog loadingBar;
    StorageReference productImageref;
    DatabaseReference productRef;
    String Description, Price, Pname, saveCurrentDate, saveCurrentTime, productRandomKey, downloadImageUrl, imageNameOnStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_item);

        itemName = findViewById(R.id.txt_admin_itemName);
        itemDes = findViewById(R.id.txt_admin_itemDes);
        itemPrice = findViewById(R.id.txt_admin_itemPrice);
        imgInput = findViewById(R.id.img_admin_input);
        btnAddItem = findViewById(R.id.btn_admin_itemSubmit);
        loadingBar = new ProgressDialog(this);
        productImageref = FirebaseStorage.getInstance().getReference().child("product images");
        productRef = FirebaseDatabase.getInstance().getReference().child("products");


        //image click
        imgInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInsert();
            }
        });


        //submit button
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });


    }

    //image selecting part
    public void ImageInsert() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GalleryPick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imgInput.setImageURI(imageUri);
        }
    }


    //validate inputs
    private void ValidateProductData() {
        Description = itemDes.getText().toString();
        Price = itemPrice.getText().toString();
        Pname = itemName.getText().toString();
        if (imageUri == null) {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        } else {
            StoreImage();
        }

    }

    //save image to firebase store
    private void StoreImage() {

        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Adding the Product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filepath = productImageref.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");

        //image name on data Storage for deleting purposes
        imageNameOnStorage = imageUri.getLastPathSegment() + productRandomKey + ".jpg";

        final UploadTask task = filepath.putFile(imageUri);

        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                //continue task to get image url
                Task<Uri> uriTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        downloadImageUrl = task.getResult().toString();
                        Toast.makeText(Activity_admin_add_item.this, "Processing...", Toast.LENGTH_SHORT).show();

                        SaveProductInfoToDatabase();
                    }
                });

            }
        });

    }

    //save url and other info to db
    public void SaveProductInfoToDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("price", Price);
        productMap.put("pname", Pname);
        productMap.put("imageName", imageNameOnStorage);

        productRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    //terminate current activity before creating it again
                    finish();

                    Intent intent = new Intent(Activity_admin_add_item.this, Activity_admin_add_item.class);
                    startActivity(intent);

                    loadingBar.dismiss();
                    Toast.makeText(Activity_admin_add_item.this, "Product added successfully..", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(Activity_admin_add_item.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}