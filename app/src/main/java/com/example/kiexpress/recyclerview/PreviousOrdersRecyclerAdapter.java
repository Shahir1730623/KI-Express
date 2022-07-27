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
import com.example.kiexpress.Users.Common.PreviousOrderDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PreviousOrdersRecyclerAdapter extends FirebaseRecyclerAdapter<Order, PreviousOrdersRecyclerAdapter.myViewHolder> {
    String item,userPhone;

    public PreviousOrdersRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Order> options,String userPhone) {
        super(options);
        this.userPhone = userPhone;
    }

    //myViewHolder(inner-class) is responsible for accessing/holding our view objects
    class myViewHolder extends RecyclerView.ViewHolder{

        TextView orderDateText,orderStatus,orderID;
        DatabaseReference reference,reference2;
        Button updateBtn,deleteBtn;
        AutoCompleteTextView autoCompleteText;

        ArrayAdapter<String> adapterItems;
        String[] items = {"Order Picked","Order Shipped","Order Delivered"};
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDateText = itemView.findViewById(R.id.orderDateText);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderID = itemView.findViewById(R.id.orderID);

            updateBtn = itemView.findViewById(R.id.updateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            autoCompleteText = itemView.findViewById(R.id.autoCompleteText);

            adapterItems = new ArrayAdapter<>(autoCompleteText.getContext(),R.layout.courier_type_dropdown_item,items);
            autoCompleteText.setAdapter(adapterItems);

            reference = FirebaseDatabase.getInstance().getReference().child("Orders");
            reference2 = FirebaseDatabase.getInstance().getReference().child("Removed-Orders");
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Order model) {
        holder.orderDateText.setText(model.getDate());
        holder.orderStatus.setText(model.getOrderStatus());
        holder.orderID.setText("Order ID: " + model.getOrderId());
        holder.autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PreviousOrderDetails.class);
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

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query =  holder.reference.orderByChild("orderId").equalTo(model.getOrderId());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                String key = snapshot1.getKey();
                                holder.reference.child(key).child("orderStatus").setValue(item);
                                Toast.makeText(holder.autoCompleteText.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                holder.autoCompleteText.setText(null);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(holder.autoCompleteText.getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.autoCompleteText.getContext());
                builder.setTitle("Delete Order");
                builder.setMessage("Are you sure you want to remove this order?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Order order = new Order(model.getOrderId(), model.getSenderName(),model.getReceiverName(),model.getSenderPhone(),model.getReceiverPhone(),model.getSenderAddress(),model.getReceiverAddress(),
                                model.getCourierService(),model.getItemNames(),model.getTotalWeight(),model.getDate(),model.getOrderStatus(),model.getOrderStatusBool());
                        holder.reference2.child(getRef(position).getKey()).setValue(order);
                        holder.reference.child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.autoCompleteText.getContext(), "Order Removed", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_previous_order_recycler_item,parent,false); //Generating View Object
        return new PreviousOrdersRecyclerAdapter.myViewHolder(view);
    }

}
