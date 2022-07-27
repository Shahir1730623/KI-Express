package com.example.kiexpress.Users.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.kiexpress.ModelClasses.Order;
import com.example.kiexpress.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomerForm extends AppCompatActivity {

    String orderId,senderName,receiverName,senderPhone,receiverPhone,senderAddress,receiverAddress,courierService,itemNames,itemWeight,date,orderStatus,orderStatusBool;
    String emailFromDB,nameFromDB,phoneFromDB;
    TextInputLayout receiverNameTxt,receiverPhoneTxt,senderAddressTxt,receiverAddressTxt,itemNamesTxt,itemWeightTxt;
    AutoCompleteTextView courierType;

    DatabaseReference reference;
    long maxID=0;
    String[] items = {"DHL","FedEx"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);

        //Hooks
        receiverNameTxt = findViewById(R.id.receiverNameTxt);
        receiverPhoneTxt = findViewById(R.id.receiverPhoneTxt);
        senderAddressTxt = findViewById(R.id.senderAddressTxt);
        courierType = findViewById(R.id.courierType);
        receiverAddressTxt = findViewById(R.id.receiverAddressTxt);
        itemNamesTxt = findViewById(R.id.itemNamesTxt);
        itemWeightTxt = findViewById(R.id.itemWeightTxt);

        //Dropdown
        ArrayAdapter<String> adapterItems= new ArrayAdapter<>(CustomerForm.this,R.layout.courier_type_dropdown_item,items);
        courierType.setAdapter(adapterItems);

        courierType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                courierService = parent.getItemAtPosition(position).toString();
            }
        });

        //Intent
        emailFromDB = getIntent().getStringExtra("email");
        nameFromDB = getIntent().getStringExtra("name");
        phoneFromDB = getIntent().getStringExtra("phone");

        //Database
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxID = snapshot.getChildrenCount();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void sendPickupRequest(View view) {
        orderId = String.valueOf(maxID + 1);
        senderName = nameFromDB;
        receiverName = receiverNameTxt.getEditText().getText().toString();
        senderPhone = phoneFromDB;
        receiverPhone = "+88" + receiverPhoneTxt.getEditText().getText().toString();
        senderAddress = senderAddressTxt.getEditText().getText().toString();
        receiverAddress = receiverAddressTxt.getEditText().getText().toString();
        itemNames = itemNamesTxt.getEditText().getText().toString();
        itemWeight = itemWeightTxt.getEditText().getText().toString();
        date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        orderStatus = "Pending Approval";
        orderStatusBool = "false";

        Order order = new Order(orderId, senderName, receiverName, senderPhone, receiverPhone,senderAddress,receiverAddress,courierService,itemNames,itemWeight,date,orderStatus,orderStatusBool);
        reference.child(orderId).setValue(order);
        Toast.makeText(CustomerForm.this, "Request Sent", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(CustomerForm.this, CustomerDashboard.class);
        intent.putExtra("email", emailFromDB);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}