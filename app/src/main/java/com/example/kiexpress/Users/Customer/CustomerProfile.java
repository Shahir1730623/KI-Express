package com.example.kiexpress.Users.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kiexpress.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfile extends AppCompatActivity {

    TextView fullNameLabel;
    TextInputLayout nameEditTxt, emailEditTxt, phoneEditTxt, passwordEditTxt;
    String userFullName, userEmail, userPhone, userPassword, userPosition;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        //Initialize
        fullNameLabel = findViewById(R.id.fullNameLabel);
        nameEditTxt = findViewById(R.id.nameEditTxt);
        emailEditTxt = findViewById(R.id.emailEditTxt);
        phoneEditTxt = findViewById(R.id.phoneEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        showUserData();
    }

    private void showUserData() {
        userFullName = getIntent().getStringExtra("name");
        userEmail = getIntent().getStringExtra("email");
        userPhone = getIntent().getStringExtra("phone");
        userPassword = getIntent().getStringExtra("password");
        userPosition = getIntent().getStringExtra("position");

        fullNameLabel.setText(userFullName);
        nameEditTxt.getEditText().setText(userFullName);
        emailEditTxt.getEditText().setText(userEmail);
        phoneEditTxt.getEditText().setText(userPhone);
        passwordEditTxt.getEditText().setText(userPassword);
    }


    private Boolean isNameChanged() {
        String userEnteredName = nameEditTxt.getEditText().getText().toString();

        if (!userFullName.equals(userEnteredName)) {
            Query CheckUser = reference.orderByChild("name").equalTo(userFullName);
            CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            String key = snapshot1.getKey();
                            reference.child(key).child("name").setValue(userEnteredName);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            userFullName = userEnteredName;
            return true;
        }

        else {
            return false;
        }
    }

    private Boolean isEmailChanged() {
        String userEnteredEmail = emailEditTxt.getEditText().getText().toString();

        if (!userEmail.equals(userEnteredEmail)) {
            Query CheckUser = reference.orderByChild("email").equalTo(userEmail);
            CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            String key = snapshot1.getKey();
                            reference.child(key).child("email").setValue(userEnteredEmail);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            userEmail = userEnteredEmail;
            return true;
        }

        else {
            return false;
        }
    }

    private Boolean isPhoneChanged() {
        String userEnteredPhone = phoneEditTxt.getEditText().getText().toString();

        if(!userPhone.equals(userEnteredPhone)){
            Query CheckUser = reference.orderByChild("phone").equalTo(userPhone);
            CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            String key = snapshot1.getKey();
                            reference.child(key).child("phone").setValue(userEnteredPhone);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            userPhone = userEnteredPhone;
            return true;
        }

        else{
            return false;
        }
    }

    private Boolean isPasswordChanged() {
        String userEnteredPassword = passwordEditTxt.getEditText().getText().toString();

        if(!userPassword.equals(userEnteredPassword)){
            Query CheckUser = reference.orderByChild("password").equalTo(userPassword);
            CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            String key = snapshot1.getKey();
                            reference.child(key).child("password").setValue(userEnteredPassword);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            userPassword = userEnteredPassword;
            return true;
        }

        else{
            return false;
        }
    }

    public void updateDataOnClick(View view) {
        Boolean isNameChanged, isEmailChanged, isPhoneChanged, isPasswordChanged;
        isNameChanged = isNameChanged();
        isEmailChanged = isEmailChanged();
        isPhoneChanged = isPhoneChanged();
        isPasswordChanged = isPasswordChanged();

        if (isNameChanged || isEmailChanged || isPhoneChanged || isPasswordChanged) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No changes made", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CustomerProfile.this, CustomerDashboard.class);
        intent.putExtra("email", userEmail);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}