package com.example.kiexpress.Users.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kiexpress.R;
import com.example.kiexpress.Users.Admin.AdminDashboard;
import com.example.kiexpress.Users.Employee.EmployeeDashboard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewOrder extends AppCompatActivity {

    String userPhone,userPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_view_order);

        getUserPosition();
    }

    private void getUserPosition() {
        userPhone = getIntent().getStringExtra("phone");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("phone").equalTo(userPhone);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        userPosition = snapshot1.child("position").getValue().toString();
                    }
                }
                else {
                    Toast.makeText(ViewOrder.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewOrder.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    public void goToNewOrderScreen(View view) {
        Intent intent = new Intent(ViewOrder.this, NewOrders.class);
        intent.putExtra("phone", userPhone);
        startActivity(intent);
    }

    public void goToPastOrderScreen(View view) {
        Intent intent = new Intent(ViewOrder.this, PreviousOrders.class);
        intent.putExtra("phone", userPhone);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (userPosition.equals("Admin")) {
            Intent intent = new Intent(ViewOrder.this, AdminDashboard.class);
            intent.putExtra("phone", userPhone);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else {
            Intent intent = new Intent(ViewOrder.this, EmployeeDashboard.class);
            intent.putExtra("phone", userPhone);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

    }
}