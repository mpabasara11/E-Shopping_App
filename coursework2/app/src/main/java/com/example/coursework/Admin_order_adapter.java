package com.example.coursework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Orders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_order_adapter extends FirebaseRecyclerAdapter<Orders, Admin_order_adapter.Admin_order_viewHolder> {
    Context context;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Admin_order_adapter(@NonNull FirebaseRecyclerOptions<Orders> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Admin_order_viewHolder holder, int position, @NonNull Orders model) {

        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());
        holder.email.setText(model.getEmail());
        holder.town.setText(model.getTown());
        holder.phone.setText(model.getPhone());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CharSequence options[] = new CharSequence[]
                        {
                                "Delete Order",
                                "View Order"
                        };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select An Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {

                            FirebaseDatabase.getInstance().getReference().child("Cart_List").child("Admin_View").child(model.getPhone()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("Orders").child(model.getPhone()).removeValue();

                            Toast.makeText(context, "Order Removed ", Toast.LENGTH_SHORT).show();

                        }

                        if (i == 1) {


                            Intent intent = new Intent(context, Activity_admin_view_order_items.class);
                            intent.putExtra("phone", model.getPhone());
                            context.startActivity(intent);


                        }


                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public Admin_order_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_layout, parent, false);
        return new Admin_order_viewHolder(view);
    }

    class Admin_order_viewHolder extends RecyclerView.ViewHolder {
        TextView address, email, name, phone, town, date;


        public Admin_order_viewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.lbl_admin_order_date);
            name = itemView.findViewById(R.id.lbl_admin_order_name);
            phone = itemView.findViewById(R.id.lbl_admin_order_phone);
            address = itemView.findViewById(R.id.lbl_admin_order_address);
            town = itemView.findViewById(R.id.lbl_admin_order_town);
            email = itemView.findViewById(R.id.lbl_admin_order_mail);


        }
    }
}
