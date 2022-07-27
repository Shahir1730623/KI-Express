package com.example.kiexpress.Users.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kiexpress.ModelClasses.Order;
import com.example.kiexpress.R;
import com.example.kiexpress.Users.Admin.AdminDashboard;
import com.example.kiexpress.Users.Employee.EmployeeDashboard;
import com.example.kiexpress.recyclerview.NewOrdersRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NewOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    NewOrdersRecyclerAdapter adapter;
    String userPhone;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_new_orders);

        recyclerView = findViewById(R.id.recyclerView);
        userPhone = getIntent().getStringExtra("phone");

        //Database
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        Query query = reference.orderByChild("orderStatus").equalTo("Pending Approval");
        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();

        adapter = new NewOrdersRecyclerAdapter(options,userPhone);
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
        Intent intent = new Intent(NewOrders.this, ViewOrder.class);
        intent.putExtra("phone", userPhone);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}