package com.example.kiexpress.recyclerview;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kiexpress.ModelClasses.Order;
import com.example.kiexpress.R;
import com.example.kiexpress.Users.Common.NewOrderDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NewOrdersRecyclerAdapter extends FirebaseRecyclerAdapter<Order, NewOrdersRecyclerAdapter.myViewHolder> {

    String userPhone;
    public NewOrdersRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Order> options,String userPhone) {
        super(options);
        this.userPhone = userPhone;
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
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Order model) {
        holder.orderDateText.setText(model.getDate());
        holder.orderStatus.setText(model.getOrderStatus());
        holder.orderID.setText("Order ID: " + model.getOrderId());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewOrderDetails.class);
                intent.putExtra("orderId",model.getOrderId());
                intent.putExtra("senderName",model.getSenderName());
                intent.putExtra("senderPhone",model.getSenderPhone());
                intent.putExtra("receiverName",model.getReceiverName());
                intent.putExtra("receiverPhone",model.getReceiverPhone());
                intent.putExtra("senderAddress",model.getSenderAddress());
                intent.putExtra("receiverAddress",model.getReceiverAddress());
                intent.putExtra("itemNames",model.getItemNames());
                intent.putExtra("courierService",model.getCourierService());
                intent.putExtra("totalWeight",model.getTotalWeight());
                intent.putExtra("phone",userPhone);
                view.getContext().startActivity(intent);
            }

        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_new_order_recycler_item,parent,false); //Generating View Object
        return new NewOrdersRecyclerAdapter.myViewHolder(view);
    }
}
