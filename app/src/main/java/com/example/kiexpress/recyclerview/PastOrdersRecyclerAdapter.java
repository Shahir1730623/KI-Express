package com.example.kiexpress.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kiexpress.Users.Customer.PastOrdersDetails;
import com.example.kiexpress.ModelClasses.Order;
import com.example.kiexpress.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PastOrdersRecyclerAdapter extends FirebaseRecyclerAdapter<Order,PastOrdersRecyclerAdapter.myViewHolder> {

    public PastOrdersRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Order> options) {
        super(options);
    }

    //myViewHolder(inner-class) is responsible for accessing/holding our view objects
    class myViewHolder extends RecyclerView.ViewHolder{

        TextView orderDateText,orderStatus,orderID;
        DatabaseReference reference;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDateText = itemView.findViewById(R.id.orderDateText);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderID = itemView.findViewById(R.id.orderID);

            reference = FirebaseDatabase.getInstance().getReference().child("Users");
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Order model) {
        holder.orderDateText.setText(model.getDate());
        holder.orderStatus.setText(model.getOrderStatus());
        holder.orderID.setText("Order ID: " + model.getOrderId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PastOrdersDetails.class);
                intent.putExtra("receiverName",model.getReceiverName());
                intent.putExtra("receiverPhone",model.getReceiverPhone());
                intent.putExtra("senderAddress",model.getSenderAddress());
                intent.putExtra("receiverAddress",model.getReceiverAddress());
                intent.putExtra("courierService",model.getCourierService());
                intent.putExtra("totalWeight",model.getTotalWeight());
                intent.putExtra("phone",model.getSenderPhone());
                view.getContext().startActivity(intent);
            }

        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_recycler_item,parent,false); //Generating View Object
        return new PastOrdersRecyclerAdapter.myViewHolder(view);
    }


}
