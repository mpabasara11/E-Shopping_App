package com.example.coursework;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Items;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class User_item_adapter extends FirebaseRecyclerAdapter<Items, User_item_adapter.User_Item_viewHolder> {
    Context context;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public User_item_adapter(@NonNull FirebaseRecyclerOptions<Items> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull User_Item_viewHolder holder, int position, @NonNull Items model) {

        holder.name.setText(model.getPname());
        holder.discription.setText(model.getDescription());
        holder.price.setText(model.getPrice());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        Picasso.get().load(model.getImage()).into(holder.imgPro);


        //redirect to item quantity activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Activity_user_items_quentity_to_cart.class);

                intent.putExtra("item_name", model.getPname());
                intent.putExtra("price", model.getPrice());
                intent.putExtra("image_url", model.getImage());

                //redirecting to item quantity activity
                context.startActivity(intent);

            }
        });


    }

    @NonNull
    @Override
    public User_Item_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout, parent, false);
        return new User_Item_viewHolder(view);
    }

    class User_Item_viewHolder extends RecyclerView.ViewHolder {

        TextView name, price, discription, date, time;
        ImageView imgPro;


        public User_Item_viewHolder(@NonNull View itemView) {
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
