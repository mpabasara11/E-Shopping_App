package com.example.coursework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Items;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class Admin_item_adapter extends FirebaseRecyclerAdapter<Items, Admin_item_adapter.Admin_Item_viewHolder> {
    Context context;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Admin_item_adapter(@NonNull FirebaseRecyclerOptions<Items> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Admin_Item_viewHolder holder, int position, @NonNull Items model) {

        holder.name.setText(model.getPname());
        holder.discription.setText(model.getDescription());
        holder.price.setText(model.getPrice());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        Picasso.get().load(model.getImage()).into(holder.imgPro);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CharSequence options[] = new CharSequence[]
                        {
                                "Delete This",
                                "Dismiss"
                        };

                //alert prompt to select two options
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select An Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //delete specified child in products with product id
                            FirebaseDatabase.getInstance().getReference().child("products").child(model.getPid()).removeValue();

                            //deleting image file uploaded on firestore with image name
                            FirebaseStorage.getInstance().getReference().child("product images").child(model.getImageName()).delete();


                            Toast.makeText(context, "Item Removed ", Toast.LENGTH_SHORT).show();
                        }
                        if (i == 1) {

                        }

                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public Admin_Item_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout, parent, false);
        return new Admin_Item_viewHolder(view);
    }

    class Admin_Item_viewHolder extends RecyclerView.ViewHolder {
        TextView name, price, discription, date, time;
        ImageView imgPro;


        public Admin_Item_viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_product_name);
            price = itemView.findViewById(R.id.product_price);
            discription = itemView.findViewById(R.id.product_desc);
            date = itemView.findViewById(R.id.product_date);
            time = itemView.findViewById(R.id.product_time);
            imgPro = itemView.findViewById(R.id.img_product);
        }
    }
}
