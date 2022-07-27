package com.example.kiexpress.Users.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kiexpress.R;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NewOrderDetails extends AppCompatActivity {

    MaterialTextView senderNameTxt,senderPhoneTxt,receiverNameTxt,receiverPhoneTxt,senderAddressTxt,receiverAddressTxt,itemNamesTxt,courierServiceTxt,totalWeightTxt;
    String orderId,senderName,senderPhone,receiverName,receiverPhone,senderAddress,receiverAddress,itemNames,courierService,totalWeight;
    String userPhone;
    DatabaseReference reference;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_new_order_details);

        //Initialize
        senderNameTxt = findViewById(R.id.senderNameTxt);
        senderPhoneTxt = findViewById(R.id.senderPhoneTxt);
        receiverNameTxt = findViewById(R.id.receiverNameTxt);
        receiverPhoneTxt = findViewById(R.id.receiverPhoneTxt);
        senderAddressTxt = findViewById(R.id.senderAddressTxt);
        itemNamesTxt = findViewById(R.id.itemNamesTxt);
        receiverAddressTxt = findViewById(R.id.receiverAddressTxt);
        courierServiceTxt = findViewById(R.id.courierServiceTxt);
        totalWeightTxt = findViewById(R.id.totalWeightTxt);

        //getIntent
        orderId = getIntent().getStringExtra("orderId");
        senderName = getIntent().getStringExtra("senderName");
        senderPhone = getIntent().getStringExtra("senderPhone");
        receiverName = getIntent().getStringExtra("receiverName");
        receiverPhone = getIntent().getStringExtra("receiverPhone");
        senderAddress = getIntent().getStringExtra("senderAddress");
        receiverAddress = getIntent().getStringExtra("receiverAddress");
        itemNames = getIntent().getStringExtra("itemNames");
        courierService = getIntent().getStringExtra("courierService");
        totalWeight = getIntent().getStringExtra("totalWeight");
        userPhone = getIntent().getStringExtra("phone");

        //setText
        senderNameTxt.setText(senderName);
        senderPhoneTxt.setText(senderPhone);
        receiverNameTxt.setText(receiverName);
        receiverPhoneTxt.setText(receiverPhone);
        senderAddressTxt.setText(senderAddress);
        receiverAddressTxt.setText(receiverAddress);
        itemNamesTxt.setText("Item Names: " + itemNames);
        courierServiceTxt.setText("Courier Service: " + courierService);
        totalWeightTxt.setText("Total Weight: " + totalWeight + " kg");

        reference = FirebaseDatabase.getInstance().getReference("Orders");
        query = reference.orderByChild("orderId").equalTo(orderId);
    }

    public void orderAccept(View view) {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String key = snapshot1.getKey();
                        reference.child(key).child("orderStatus").setValue("Order Accepted");
                        reference.child(key).child("orderStatusBool").setValue("true");
                        Toast.makeText(NewOrderDetails.this, "Order Accepted", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
                else{
                    Toast.makeText(NewOrderDetails.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void orderDecline(View view) {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String key = snapshot1.getKey();
                        reference.child(key).child("orderStatus").setValue("Order Declined");
                        reference.child(key).child("orderStatusBool").setValue("true");
                        Toast.makeText(NewOrderDetails.this, "Order Declined", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
                else{
                    Toast.makeText(NewOrderDetails.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewOrderDetails.this, NewOrders.class);
        intent.putExtra("phone", userPhone);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}