package com.example.kiexpress.Users.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kiexpress.ModelClasses.Order;
import com.example.kiexpress.R;
import com.example.kiexpress.recyclerview.PastOrdersRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PastOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    PastOrdersRecyclerAdapter adapter;
    String userEmail,userPhone;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_past_orders);

        recyclerView = findViewById(R.id.recyclerView);

        showUserData();

        //Database
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        Query query = reference.orderByChild("senderPhone").equalTo(userPhone);
        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();

        adapter = new PastOrdersRecyclerAdapter(options);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);             // Display items in reverse order
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);    //The items in recyclerview will be displayed in linear fashion

    }

    private void showUserData() {
        userPhone = getIntent().getStringExtra("phone");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("phone").equalTo(userPhone);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        userEmail = snapshot1.child("email").getValue().toString();
                    }
                } else
                    Toast.makeText(PastOrders.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    public void onBackPressed(){
        Intent intent = new Intent(PastOrders.this, CustomerDashboard.class);
        intent.putExtra("email", userEmail);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}