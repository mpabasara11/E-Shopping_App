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

import com.example.coursework.Models.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class User_cart_adapter extends FirebaseRecyclerAdapter<Cart, User_cart_adapter.User_Cart_viewHolder> {
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public User_cart_adapter(@NonNull FirebaseRecyclerOptions<Cart> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull User_Cart_viewHolder holder, int position, @NonNull Cart model) {

        holder.name.setText(model.getPname());
        holder.price.setText(model.getPrice());
        holder.quantity.setText(model.getQuantity());

        Picasso.get().load(model.getImage()).into(holder.imgProCart);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[] = new CharSequence[]
                        {
                                "Remove This",
                                "Dismiss"
                        };
                //alert to select two options
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do you want to remove this");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {

                            //remove single value in admin view and user view documents using current user phone number
                            FirebaseDatabase.getInstance().getReference().child("Cart_List").child("Admin_View").child(Current_User_Store.usrStore.getPhone()).child(model.getUid()).removeValue();

                            FirebaseDatabase.getInstance().getReference().child("Cart_List").child("User_View").child(Current_User_Store.usrStore.getPhone()).child(model.getUid()).removeValue();

                            Toast.makeText(context, "Cart Item Removed ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public User_Cart_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart_item_layout, parent, false);
        return new User_Cart_viewHolder(view);
    }

    class User_Cart_viewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView imgProCart;


        public User_Cart_viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cart_product_name);
            price = itemView.findViewById(R.id.cart_product_price);
            quantity = itemView.findViewById(R.id.cart_product_quantity);
            imgProCart = itemView.findViewById(R.id.img_cart_product);
        }
    }
}
