package com.example.kiexpress.Users.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.kiexpress.R;
import com.google.android.material.textview.MaterialTextView;

public class PastOrdersDetails extends AppCompatActivity {

    MaterialTextView receiverNameTxt,receiverPhoneTxt,senderAddressTxt,receiverAddressTxt,courierServiceTxt,totalWeightTxt;
    String receiverName,receiverPhone,senderAddress,receiverAddress,courierService,totalWeight;
    String userPhone;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_past_order_details);

        //Initialize
        receiverNameTxt = findViewById(R.id.receiverNameTxt);
        receiverPhoneTxt = findViewById(R.id.receiverPhoneTxt);
        senderAddressTxt = findViewById(R.id.senderAddressTxt);
        receiverAddressTxt = findViewById(R.id.receiverAddressTxt);
        courierServiceTxt = findViewById(R.id.courierServiceTxt);
        totalWeightTxt = findViewById(R.id.totalWeightTxt);

        //getIntent
        receiverName = getIntent().getStringExtra("receiverName");
        receiverPhone = getIntent().getStringExtra("receiverPhone");
        senderAddress = getIntent().getStringExtra("senderAddress");
        receiverAddress = getIntent().getStringExtra("receiverAddress");
        courierService = getIntent().getStringExtra("courierService");
        totalWeight = getIntent().getStringExtra("totalWeight");
        userPhone = getIntent().getStringExtra("phone");

        //setText
        receiverNameTxt.setText(receiverName);
        receiverPhoneTxt.setText(receiverPhone);
        senderAddressTxt.setText(senderAddress);
        receiverAddressTxt.setText(receiverAddress);
        courierServiceTxt.setText("Courier Service: " + courierService);
        totalWeightTxt.setText("Total Weight: " + totalWeight + " kg");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PastOrdersDetails.this, PastOrders.class);
        intent.putExtra("phone", userPhone);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}