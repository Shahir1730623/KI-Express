package com.example.kiexpress.Users.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.kiexpress.ModelClasses.Order;
import com.example.kiexpress.R;
import com.example.kiexpress.recyclerview.NewOrdersRecyclerAdapter;
import com.example.kiexpress.recyclerview.PastOrdersRecyclerAdapter;
import com.example.kiexpress.recyclerview.PreviousOrdersRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PreviousOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    PreviousOrdersRecyclerAdapter adapter;
    String userPhone;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_previous_orders);

        recyclerView = findViewById(R.id.recyclerView);
        userPhone = getIntent().getStringExtra("phone");

        //Database
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        Query query = reference.orderByChild("orderStatusBool").equalTo("true");
        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();

        adapter = new PreviousOrdersRecyclerAdapter(options,userPhone);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);             // Display items in reverse order
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);    //The items in recyclerview will be displayed in linear fashion

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PreviousOrders.this, ViewOrder.class);
        intent.putExtra("phone", userPhone);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}