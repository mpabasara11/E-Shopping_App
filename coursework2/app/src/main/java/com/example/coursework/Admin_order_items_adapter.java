package com.example.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Admin_order_items_adapter extends FirebaseRecyclerAdapter<Cart, Admin_order_items_adapter.Admin_order_items_viewHolder> {
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Admin_order_items_adapter(@NonNull FirebaseRecyclerOptions<Cart> options, Context context) {
        super(options);

        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Admin_order_items_viewHolder holder, int position, @NonNull Cart model) {

        holder.name.setText(model.getPname());
        holder.price.setText(model.getPrice());
        holder.quantity.setText(model.getQuantity());

        Picasso.get().load(model.getImage()).into(holder.imgProCart);


    }

    @NonNull
    @Override
    public Admin_order_items_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_item_layout, parent, false);
        return new Admin_order_items_viewHolder(view);
    }

    class Admin_order_items_viewHolder extends RecyclerView.ViewHolder {

        TextView name, price, quantity;
        ImageView imgProCart;


        public Admin_order_items_viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.order_item_product_name);
            price = itemView.findViewById(R.id.order_item_product_price);
            quantity = itemView.findViewById(R.id.order_item_product_quantity);
            imgProCart = itemView.findViewById(R.id.img_order_item_product);
        }
    }
}
